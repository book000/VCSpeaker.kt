package com.jaoafa.vcspeaker.voicetext

import com.jaoafa.vcspeaker.VCSpeaker
import com.jaoafa.vcspeaker.store.CacheStore
import com.jaoafa.vcspeaker.store.GuildStore
import com.jaoafa.vcspeaker.store.VoiceStore
import com.jaoafa.vcspeaker.tools.Discord.respond
import com.jaoafa.vcspeaker.voicetext.api.Speaker
import com.kotlindiscord.kord.extensions.types.PublicInteractionContext
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import dev.kord.common.entity.Snowflake
import dev.kord.core.entity.Guild
import dev.kord.core.entity.channel.TextChannel
import java.rmi.UnexpectedException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object NarratorExtensions {
    suspend fun Narrator.announce(voice: String, text: String, interaction: PublicInteractionContext? = null) {
        VCSpeaker.kord.getGuildOrNull(guildId)?.announce(voice, text, interaction)
    }

    suspend fun Guild.announce(voice: String, text: String, interaction: PublicInteractionContext? = null) {
        val narrator = VCSpeaker.narrators[id]

        narrator?.queueSelf(voice)

        if (interaction != null) {
            interaction.respond(text)
        } else {
            val channel = GuildStore.getOrDefault(id).channelId?.let {
                VCSpeaker.kord.getChannelOf<TextChannel>(it)
            }

            channel?.createMessage(text)
        }
    }

    suspend fun AudioPlayer.speakSelf(text: String, guildId: Snowflake) {
        speak(SpeakInfo(text, GuildStore[guildId]?.voice ?: Voice(speaker = Speaker.Hikari)))
    }

    suspend fun AudioPlayer.speakUser(text: String, userId: Snowflake) {
        speak(SpeakInfo(text, VoiceStore.byIdOrDefault(userId)))
    }

    suspend fun AudioPlayer.speak(info: SpeakInfo) {
        val text = info.text
        val voice = info.voice

        val file = if (!CacheStore.exists(text, voice)) {
            val audio = VCSpeaker.voiceText.generateSpeech(text, voice)
            CacheStore.create(text, voice, audio)
        } else CacheStore.read(text)

        val track = suspendCoroutine {
            VCSpeaker.lavaplayer.loadItemOrdered(
                this,
                file!!.path, // already checked
                object : AudioLoadResultHandler {
                    override fun trackLoaded(track: AudioTrack) {
                        track.userData = info
                        it.resume(track)
                    }

                    override fun playlistLoaded(playlist: AudioPlaylist?) {
                        throw UnexpectedException("This code should not be reached.")
                    }

                    override fun noMatches() {
                        return
                    }

                    override fun loadFailed(exception: FriendlyException?) {
                        return
                    }
                })
        }

        this.playTrack(track)
    }
}