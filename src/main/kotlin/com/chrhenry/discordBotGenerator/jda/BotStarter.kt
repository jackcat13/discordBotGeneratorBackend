package com.chrhenry.discordBotGenerator.jda

import com.chrhenry.discordBotGenerator.entity.*
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.ChunkingFilter
import net.dv8tion.jda.api.utils.MemberCachePolicy
import org.springframework.stereotype.Component

@Component
class BotStarter{

    companion object {
        /*
        @JvmStatic
        fun main(args: Array<String>){
            JDABuilder.createDefault("TOBEREPLACED")
                .setChunkingFilter(ChunkingFilter.ALL) // enable member chunking for all guilds
                .setMemberCachePolicy(MemberCachePolicy.ALL) // ignored if chunking enabled
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(Bot())
                .build()
        }
        */

        @JvmStatic
        fun run(botConfiguration: Configuration = Configuration()): JDA {
            val bot = Bot().apply { configuration = botConfiguration }
            return JDABuilder.createDefault(botConfiguration.token)
                .setChunkingFilter(ChunkingFilter.ALL) // enable member chunking for all guilds
                .setMemberCachePolicy(MemberCachePolicy.ALL) // ignored if chunking enabled
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(bot)
                .build()
        }
    }
}