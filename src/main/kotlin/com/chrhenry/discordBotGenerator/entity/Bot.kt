package com.chrhenry.discordBotGenerator.entity

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Bot(val id: String, val description: String, val user: User)
