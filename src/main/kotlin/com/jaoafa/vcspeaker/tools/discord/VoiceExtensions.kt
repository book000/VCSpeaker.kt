package com.jaoafa.vcspeaker.tools.discord

import com.jaoafa.vcspeaker.VCSpeaker
import com.jaoafa.vcspeaker.tools.discord.DiscordExtensions.errorColor
import com.jaoafa.vcspeaker.tools.discord.DiscordExtensions.name
import com.jaoafa.vcspeaker.tts.SpeakInfo
import com.jaoafa.vcspeaker.tts.narrators.NarrationScripts
import com.jaoafa.vcspeaker.tts.narrators.Narrator
import com.jaoafa.vcspeaker.tts.narrators.Narrators
import com.jaoafa.vcspeaker.tts.narrators.Narrators.narrator
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import dev.kord.common.annotation.KordVoice
import dev.kord.core.behavior.channel.BaseVoiceChannelBehavior
import dev.kord.core.behavior.channel.connect
import dev.kord.core.behavior.reply
import dev.kord.rest.builder.message.embed
import dev.kord.voice.AudioFrame
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.runBlocking
import java.rmi.UnexpectedException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object VoiceExtensions {
    private val logger = KotlinLogging.logger { }

    /**
     * VoiceChannel に接続します。
     *
     * 接続時に [Narrator] が作成され、[Narrators] に登録されます。
     *
     * @param replier 参加通知の文章を受け取り、返信する関数
     */
    @OptIn(KordVoice::class)
    suspend fun BaseVoiceChannelBehavior.join(
        replier: (suspend (String) -> Unit)? = null
    ): Narrator {
        Narrators -= guild.id // force disconnection

        val player = VCSpeaker.lavaplayer.createPlayer()

        val connection = connect {
            audioProvider {
                AudioFrame.fromData(player.provide(1, TimeUnit.SECONDS)?.data)
            }
        }

        val narrator = Narrator(guild.id, player, connection)
        Narrators += narrator

        narrator.announce(
            NarrationScripts.SELF_JOIN,
            "**:loudspeaker: $mention に接続しました。**",
            replier
        )

        val name = name()
        val guildName = guild.asGuild().name

        logger.info {
            "[$guildName] Joined: Joined to $name"
        }

        return narrator
    }

    /**
     * 新しい VoiceChannel へ移動します。
     *
     * 実行時に VoiceChannel に接続していない場合、何も起こりません。
     *
     * @param replier 移動通知の文章を受け取り、返信する関数
     */
    @OptIn(KordVoice::class)
    suspend fun BaseVoiceChannelBehavior.move(
        replier: (suspend (String) -> Unit)? = null
    ): Narrator? {
        val narrator = guild.narrator() ?: return null

        narrator.connection.move(id)

        narrator.announce(
            NarrationScripts.SELF_MOVE,
            "**:loudspeaker: $mention に移動しました。**",
            replier
        )

        val name = name()
        val guildName = guild.asGuild().name

        logger.info {
            "[$guildName] Moved: Moved to $name"
        }

        return narrator
    }

    /**
     * VoiceChannel から退出します。
     *
     * 実行時に VoiceChannel に接続していない場合、何も起こりません。
     *
     * 退出時に [Narrator] が破棄され、[Narrators] から削除されます。
     *
     * @param replier 退出通知の文章を受け取り、返信する関数
     */
    @OptIn(KordVoice::class)
    suspend fun BaseVoiceChannelBehavior.leave(
        replier: (suspend (String) -> Unit)? = null
    ) {
        val narrator = guild.narrator() ?: return

        narrator.connection.leave()
        narrator.player.destroy()

        Narrators -= guild.id

        narrator.announce(
            "",
            "**:wave: $mention から退出しました。**",
            replier
        )

        val name = name()
        val guildName = guild.asGuild().name

        logger.info {
            "[$guildName] Left: Left from $name"
        }
    }

    // TODO: Separate load and play to avoid blocking the main thread
    suspend fun AudioPlayer.speak(info: SpeakInfo) {
        val guildName = info.guild.name

        val track = suspendCoroutine {
            VCSpeaker.lavaplayer.loadItemOrdered(
                this,
                info.file.path, // already checked
                object : AudioLoadResultHandler {
                    override fun trackLoaded(track: AudioTrack) {
                        logger.info {
                            "[$guildName] Loaded Track: Audio for ${info.getMessageLogInfo()} has been loaded successfully (${track.identifier})"
                        }

                        track.userData = info
                        it.resume(track)
                    }

                    override fun playlistLoaded(playlist: AudioPlaylist?) {
                        throw UnexpectedException("This code should not be reached.")
                    }

                    override fun noMatches() {
                        return
                    }

                    override fun loadFailed(exception: FriendlyException?): Unit = runBlocking {
                        info.message?.reply {
                            embed {
                                title = ":interrobang: Error!"

                                description = """
                                        音声の読み込みに失敗しました。
                                        VCSpeaker の不具合と思われる場合は、[GitHub Issues](https://github.com/jaoafa/VCSpeaker.kt/issues) か、サーバー既定のチャンネルへの報告をお願いします。
                                    """.trimIndent()

                                field("Exception") {
                                    "```\n${exception?.message ?: "不明"}\n```"
                                }

                                errorColor()
                            }
                        }

                        logger.error(exception) {
                            "[$guildName] Failed to Load Track: Audio track for ${info.getMessageLogInfo(withText = true)} have failed to load."
                        }
                    }
                })
        }

        try {
            this.playTrack(track)

            logger.info {
                "[$guildName] Playing Track: Audio for ${info.getMessageLogInfo()} is playing now (${track.identifier})"
            }
        } catch (exception: Exception) {
            logger.error(exception) {
                "[$guildName] Failed to Play Track: Audio track for ${info.getMessageLogInfo(withText = true)} have failed to play."
            }
        }
    }
}