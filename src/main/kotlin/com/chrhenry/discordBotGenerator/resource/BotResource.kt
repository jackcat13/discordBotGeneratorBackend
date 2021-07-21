package com.chrhenry.discordBotGenerator.resource

import com.chrhenry.discordBotGenerator.dto.Bot
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController()
class BotResource {

    @GetMapping("/bots")
    fun getBots(): Flux<Bot>{
        return Flux.just(
                Bot("PandemicBot", "Discord bot for the pandemic game"),
                Bot("PandemicDarkRp", "Discord bot for the pandemic dark RP GMod server")
        )
    }
}