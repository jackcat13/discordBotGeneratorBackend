package com.chrhenry.discordBotGenerator.service

import com.chrhenry.discordBotGenerator.entity.Bot
import com.chrhenry.discordBotGenerator.jda.BotStarter
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDA.Status.CONNECTED
import net.dv8tion.jda.internal.utils.tuple.MutablePair
import org.springframework.stereotype.Service

@Service
class JdaStarterService(private val botStarter: BotStarter) {

    private val botProcesses = mutableSetOf<MutablePair<String, JDA>>()

    fun startBot(bot: Bot) {
        if (!botProcesses.any{ it.left == bot.id }) addProcess(bot)
        else if (botProcesses.any{ it.left == bot.id && it.right.status != CONNECTED }) restartProcess(bot)
    }

    private fun addProcess(bot: Bot){ botProcesses.add(bot.id mutableTo BotStarter.run(bot.configuration)) }

    private fun restartProcess(bot: Bot){ botProcesses.first { it.left == bot.id }.right = BotStarter.run(bot.configuration) }

    fun getBotServiceStatus(id: String): Boolean = botProcesses.any { it.left == id && it.right.status == CONNECTED }
}

private infix fun String.mutableTo(jda: JDA): MutablePair<String, JDA> = MutablePair(this, jda)
