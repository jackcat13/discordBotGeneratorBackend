package com.chrhenry.discordBotGenerator.jda

import com.chrhenry.discordBotGenerator.entity.Configuration
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class Bot : ListenerAdapter() {

    var configuration = Configuration()

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) return
        event.message.contentRaw.patternMatching(event)
    }

    private fun String.patternMatching(event: MessageReceivedEvent) {
        val adminContext = event to configuration
        when{
            equals(configuration.commands.help) -> event.returnHelpPanel(configuration)
            startsWith(configuration.commands.sondage) -> event.returnSurvey(configuration)
            startsWith(configuration.commands.mute) -> adminContext.ifAdmin { event.muteUser(configuration) }
            startsWith(configuration.commands.unMute) -> adminContext.ifAdmin { event.unmuteUser(configuration) }
        }
    }
}


