package com.chrhenry.discordBotGenerator.service

import com.chrhenry.discordBotGenerator.entity.Bot
import com.chrhenry.discordBotGenerator.jda.BotStarter
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDA.Status.CONNECTED
import org.springframework.stereotype.Service

@Service
class JdaStarterService(private val botStarter: BotStarter) {

    private val botProcesses = mutableSetOf<Pair<String, JDA>>()

    fun startBot(bot: Bot) {
        botProcesses.add(bot.id to BotStarter.run(bot.configuration.token))
    }

    fun getBotServiceStatus(id: String): Boolean = botProcesses.any { it.first == id && it.second.status == CONNECTED }
}