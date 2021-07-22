package com.chrhenry.discordBotGenerator.repository

import com.chrhenry.discordBotGenerator.entity.Bot
import com.chrhenry.discordBotGenerator.entity.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface BotRepository: ReactiveMongoRepository<Bot, String> {

    fun findByUser(user: User): Flux<Bot>
}
