package com.jaoafa.vcspeaker.commands

import com.jaoafa.vcspeaker.VCSpeaker
import com.jaoafa.vcspeaker.tools.Discord.publicSlashCommand
import com.jaoafa.vcspeaker.tools.Discord.respond
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.utils.respond

class ClearCommand : Extension() {

    override val name = this::class.simpleName!!

    override suspend fun setup() {
        publicSlashCommand("clear", "予定されているメッセージの読み上げを中止します。") {
            action {
                val narrator = VCSpeaker.narrators[guild!!.id] ?: run {
                    respond("**:question: VC に参加していません。**")
                    return@action
                }

                narrator.clear()
                narrator.queueSelf("読み上げを中止しました。")

                respond("**:white_check_mark: 予定されていたメッセージの読み上げを中止しました。**")
            }
        }

        chatCommand {
            name = "clear"
            description = "予定されているメッセージの読み上げを中止します。"

            action {
                val narrator = VCSpeaker.narrators[guild!!.id] ?: run {
                    respond("**:question: VC に参加していません。**")
                    return@action
                }

                narrator.clear()
                narrator.queueSelf("読み上げを中止しました。")

                respond("**:white_check_mark: 予定されていたメッセージの読み上げを中止しました。**")
            }
        }
    }
}