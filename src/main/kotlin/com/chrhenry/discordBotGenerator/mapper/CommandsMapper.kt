package com.chrhenry.discordBotGenerator.mapper

import com.chrhenry.discordBotGenerator.dto.CommandsApiDto
import com.chrhenry.discordBotGenerator.entity.Commands

fun Commands.map() = CommandsApiDto(sondage, help, mp, mute, unMute)

fun CommandsApiDto.map() = Commands(sondage, help, mp, mute, unMute)