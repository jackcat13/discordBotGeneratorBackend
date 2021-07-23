package com.chrhenry.discordBotGenerator.mapper

import com.chrhenry.discordBotGenerator.dto.ConfigurationApiDto
import com.chrhenry.discordBotGenerator.entity.Configuration

fun Configuration.map() = ConfigurationApiDto(commands.map(), channels.map(), messages.map(), roles.map())

fun ConfigurationApiDto.map() = Configuration(commands.map(), channels.map(), messages.map(), roles.map())