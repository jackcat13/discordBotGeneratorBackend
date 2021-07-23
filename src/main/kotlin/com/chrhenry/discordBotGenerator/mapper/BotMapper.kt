package com.chrhenry.discordBotGenerator.mapper

import com.chrhenry.discordBotGenerator.dto.BotApiDto
import com.chrhenry.discordBotGenerator.entity.Bot
import com.chrhenry.discordBotGenerator.entity.Configuration
import java.lang.IllegalArgumentException

fun Bot.map() = BotApiDto(id, description, user.map(), configuration.map())

fun BotApiDto.map() = Bot(id, description, user.map(), Configuration())

fun BotApiDto.mapWithConfiguration() = Bot(id, description, user.map(), configuration.map())