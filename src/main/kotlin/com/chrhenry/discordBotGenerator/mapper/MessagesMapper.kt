package com.chrhenry.discordBotGenerator.mapper

import com.chrhenry.discordBotGenerator.dto.MessagesApiDto
import com.chrhenry.discordBotGenerator.entity.Messages

fun Messages.map() = MessagesApiDto(rulesMessage)

fun MessagesApiDto.map() = Messages(rulesMessage)