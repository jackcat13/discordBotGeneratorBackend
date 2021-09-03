package com.chrhenry.discordBotGenerator.resource

import com.chrhenry.discordBotGenerator.dto.BotApiDto
import com.chrhenry.discordBotGenerator.entity.Bot
import com.chrhenry.discordBotGenerator.mapper.map
import com.chrhenry.discordBotGenerator.mapper.mapWithConfiguration
import com.chrhenry.discordBotGenerator.service.BotService
import jdk.jfr.ContentType
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.MimeType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


@RestController()
class BotResource(private val botService: BotService) {

    @GetMapping("/bots")
    @ResponseBody
    fun getBotsForUser(@RequestParam userId: String): Flux<BotApiDto>{
        return botService.getBotsForUser(userId)
                .doOnNext { println("onNext: Bot $it to be returned to consumer") }
                .map { it.map() }
                .doOnSubscribe { println("Return Bots dtos to consumer") };
    }

    @GetMapping("/bots/{id}/botAsAServiceStatus")
    @ResponseBody
    fun getBotServiceStatusById(@PathVariable id: String): Mono<Boolean>{
        return botService.getBotServiceStatusById(id)
                .doOnNext { println("onNext: Bot $id has service status $it to be returned to consumer") }
                .doOnSubscribe { println("Return bot $id service status to consumer") };
    }

    @GetMapping("/bots/{id}/downloadBotCode")
    @ResponseBody
    fun downloadBotById(@PathVariable id: String): ResponseEntity<ByteArray> {
        return ResponseEntity.ok()
            .contentType(MediaType.asMediaType(MimeType.valueOf("application/zip")))
            .headers(HttpHeaders().apply { set("Content-Disposition", "attachment; filename=DATA.zip") })
            .body(botService.downloadBotById(id).toByteArray())
    }

    @PostMapping("/bots")
    fun createBot(@RequestBody botApiDto: BotApiDto): Mono<BotApiDto> {
        return botService.createOrUpdateBot(botApiDto.map())
                .map { it.map() }
                .doOnSuccess { println("Bot $it created") }
                .doOnSubscribe { println("Creating bot $it") }
    }

    @DeleteMapping("/bots/{id}")
    fun deleteBot(@PathVariable id: String): Mono<Void>{
        return botService.deleteBot(id)
                .doOnSuccess { println("Bot $it deleted") }
                .doOnSubscribe { println("Deleting bot $it") }
    }

    @PutMapping("/bots")
    fun updateTodo(@RequestBody botApiDto: BotApiDto): Mono<ResponseEntity<Bot>> {
        return botService.createOrUpdateBot(botApiDto.mapWithConfiguration())
                .map { ResponseEntity<Bot>(it, HttpStatus.OK) }
                .doOnSuccess { println("Bot $botApiDto updated") }
                .doOnSubscribe { println("Updating bot $botApiDto") }
    }

    @PostMapping("/bots/{id}")
    fun startBot(@PathVariable id: String): Mono<BotApiDto> {
        return botService.startBot(id)
                .map { it.map() }
                .doOnSuccess { println("Bot $it started") }
                .doOnSubscribe { println("Starting bot $it") }
    }
}