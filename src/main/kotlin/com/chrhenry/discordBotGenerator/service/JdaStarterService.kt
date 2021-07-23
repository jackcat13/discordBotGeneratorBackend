package com.chrhenry.discordBotGenerator.service

import com.chrhenry.discordBotGenerator.bot.BotStarter
import com.chrhenry.discordBotGenerator.entity.Bot
import org.springframework.stereotype.Service

@Service
class JdaStarterService(private val botStarter: BotStarter) {

    private val botProcesses = mutableSetOf<Thread>()

    fun startBot(bot: Bot) {
        botProcesses.add(BotStarter.run(bot.configuration.token))
    }
}