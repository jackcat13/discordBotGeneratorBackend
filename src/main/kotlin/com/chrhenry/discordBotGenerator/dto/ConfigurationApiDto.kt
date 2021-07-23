package com.chrhenry.discordBotGenerator.dto

data class ConfigurationApiDto(
        val commands: CommandsApiDto,
        val channels: ChannelsApiDto,
        val messages: MessagesApiDto,
        val roles: RolesApiDto,
        val token: String = ""
)
