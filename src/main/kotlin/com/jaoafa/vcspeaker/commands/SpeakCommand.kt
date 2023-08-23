package com.jaoafa.vcspeaker.commands

import com.jaoafa.vcspeaker.VCSpeaker
import com.jaoafa.vcspeaker.tools.Discord.publicSlashCommand
import com.jaoafa.vcspeaker.tools.Discord.respond
import com.jaoafa.vcspeaker.tools.Options
import com.kotlindiscord.kord.extensions.commands.converters.impl.string
import com.kotlindiscord.kord.extensions.extensions.Extension

class SpeakCommand : Extension() {

    override val name = this::class.simpleName!!

    inner class SpeakOptions : Options() {
        val text by string {
            name = "text"
            description = "The text to speak."
        }
    }

    override suspend fun setup() {
        publicSlashCommand("speak", "Speaks the text. (Debug use only)", ::SpeakOptions) {
            action {
                VCSpeaker.narrators[guild!!.id]?.queueSelf(arguments.text)
                respond(arguments.text)
            }
        }
    }
}