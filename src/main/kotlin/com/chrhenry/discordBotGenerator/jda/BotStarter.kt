package com.chrhenry.discordBotGenerator.jda

import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.ChunkingFilter
import net.dv8tion.jda.api.utils.MemberCachePolicy
import org.springframework.stereotype.Component
import kotlin.concurrent.thread

@Component
class BotStarter : ListenerAdapter() {

    companion object {
        @JvmStatic
        fun run(botToken: String): Thread {
            return thread{
                val jda = JDABuilder.createDefault(botToken)
                        .setChunkingFilter(ChunkingFilter.ALL) // enable member chunking for all guilds
                        .setMemberCachePolicy(MemberCachePolicy.ALL) // ignored if chunking enabled
                        .enableIntents(GatewayIntent.GUILD_MEMBERS)
                        .addEventListeners(BotStarter())
                        .build()
            }
        }
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        event.channel.sendMessage("NOT IMPLEMENTED YET").queue()
    }
}