package com.jaoafa.vcspeaker.voicetext

import com.jaoafa.vcspeaker.store.GuildStore
import com.jaoafa.vcspeaker.store.VoiceStore
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import dev.kord.common.annotation.KordVoice
import dev.kord.common.entity.Snowflake
import dev.kord.core.entity.Message
import dev.kord.core.entity.ReactionEmoji
import dev.kord.voice.VoiceConnection

class GuildNarrator @OptIn(KordVoice::class) constructor(
    val guildId: Snowflake,
    val player: AudioPlayer,
    val connection: VoiceConnection
) {
    private val scheduler = NarratorScheduler(guildId, player)

    private suspend fun queue(text: String, voice: Voice, message: Message? = null) =
        scheduler.queue(SpeakInfo(text, voice, message))

    suspend fun queueSelf(text: String) =
        queue(text, GuildStore.getOrDefault(guildId).voice)

    suspend fun queueUser(text: String, userId: Snowflake, message: Message) =
        queue(text, VoiceStore.byIdOrDefault(userId), message)


    suspend fun skip() = scheduler.skip()

    suspend fun clear() {
        listOfNotNull(*scheduler.queue.toTypedArray(), scheduler.now).forEach {
            it.message?.deleteOwnReaction(ReactionEmoji.Unicode("🔊"))
            it.message?.deleteOwnReaction(ReactionEmoji.Unicode("👀"))
        }
        scheduler.queue.clear()
        scheduler.now = null
        player.stopTrack()
    }

    init {
        player.addListener(scheduler)
    }
}