package com.chrhenry.discordBotGenerator.mapper

import com.chrhenry.discordBotGenerator.dto.RolesApiDto
import com.chrhenry.discordBotGenerator.entity.Roles

fun Roles.map() = RolesApiDto(activeRole, muteRole, adminRole)

fun RolesApiDto.map() = Roles(activeRole, muteRole, adminRole)