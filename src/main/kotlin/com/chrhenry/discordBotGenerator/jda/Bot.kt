package com.chrhenry.discordBotGenerator.jda

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class Bot : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) return
        event.channel.sendMessage("NOT IMPLEMENTED YET").queue()
    }
}