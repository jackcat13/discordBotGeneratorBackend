package com.chrhenry.discordBotGenerator.service

import com.chrhenry.discordBotGenerator.entity.Bot
import com.chrhenry.discordBotGenerator.jda.BotStarter
import org.springframework.stereotype.Service

@Service
class JdaStarterService(private val botStarter: BotStarter) {

    private val botProcesses = mutableSetOf<Pair<String, Thread>>()

    fun startBot(bot: Bot) {
        botProcesses.add(bot.id to BotStarter.run(bot.configuration.token))
    }

    fun getBotServiceStatus(id: String) = botProcesses.any { it.first == id && it.second.isAlive }
}