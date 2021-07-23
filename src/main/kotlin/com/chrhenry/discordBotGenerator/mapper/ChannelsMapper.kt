package com.chrhenry.discordBotGenerator.mapper

import com.chrhenry.discordBotGenerator.dto.ChannelsApiDto
import com.chrhenry.discordBotGenerator.entity.Channels

fun Channels.map() = ChannelsApiDto(messagesChannel, welcomeChannel, logsChannel)

fun ChannelsApiDto.map() = Channels(messagesChannel, welcomeChannel, logsChannel)