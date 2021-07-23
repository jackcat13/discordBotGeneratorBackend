package com.chrhenry.discordBotGenerator.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Bot(@Id val id: String, val description: String, val user: User, val configuration: Configuration = Configuration())
