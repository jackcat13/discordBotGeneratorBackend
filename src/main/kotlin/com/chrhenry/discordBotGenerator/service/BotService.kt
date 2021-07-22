package com.chrhenry.discordBotGenerator.service

import com.chrhenry.discordBotGenerator.entity.Bot
import com.chrhenry.discordBotGenerator.entity.User
import com.chrhenry.discordBotGenerator.repository.BotRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BotService(private val botRepository: BotRepository) {

    fun getBotsForUser(userId: String): Flux<Bot> {
        return botRepository.findByUser(User(userId))
                .doOnNext { println("On next: Bot $it retrieved") }
                .doOnSubscribe { println("Retrieve user by id $userId") }
    }

    fun createBot(bot: Bot): Mono<Bot> {
        return botRepository.save(bot)
                .doOnSuccess { println("Created bot entity $it") }
                .doOnSubscribe { println("Creating bot entity $it") }
    }
}
