package com.chrhenry.discordBotGenerator.mapper

import com.chrhenry.discordBotGenerator.dto.BotApiDto
import com.chrhenry.discordBotGenerator.entity.Bot
import java.lang.IllegalArgumentException

fun Bot.map() = BotApiDto(id, description, user.map())

fun BotApiDto.map() = Bot(id, description, user.map())