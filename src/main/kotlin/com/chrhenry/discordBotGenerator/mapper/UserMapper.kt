package com.chrhenry.discordBotGenerator.mapper

import com.chrhenry.discordBotGenerator.dto.UserApiDto
import com.chrhenry.discordBotGenerator.entity.User

fun User.map() = UserApiDto(id)

fun UserApiDto.map() = User(id)