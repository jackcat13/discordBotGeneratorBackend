package com.chrhenry.discordBotGenerator.entity

data class Configuration(
        val commands: Commands = Commands(),
        val channels: Channels = Channels(),
        val messages: Messages = Messages(),
        val roles: Roles = Roles()
)
