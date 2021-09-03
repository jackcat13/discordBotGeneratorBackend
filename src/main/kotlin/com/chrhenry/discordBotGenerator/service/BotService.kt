package com.chrhenry.discordBotGenerator.service

import com.chrhenry.discordBotGenerator.entity.Bot
import com.chrhenry.discordBotGenerator.entity.User
import com.chrhenry.discordBotGenerator.repository.BotRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

@Service
class BotService(private val botRepository: BotRepository, private val jdaStarterService: JdaStarterService) {

    fun getBotsForUser(userId: String): Flux<Bot> {
        return botRepository.findByUser(User(userId))
                .doOnNext { println("On next: Bot $it retrieved") }
                .doOnSubscribe { println("Retrieve user by id $userId") }
    }

    fun getBotServiceStatusById(id: String) = Mono.just(jdaStarterService.getBotServiceStatus(id))

    fun createOrUpdateBot(bot: Bot): Mono<Bot> {
        return botRepository.save(bot)
                .doOnSuccess { println("Created bot entity $it") }
                .doOnSubscribe { println("Creating bot entity $it") }
    }

    fun deleteBot(botId: String): Mono<Void> {
        return botRepository.deleteById(botId)
                .doOnSuccess { println("bot entity $it deleted") }
                .doOnSubscribe{ println("Deleting bot entity $it") }
    }

    fun startBot(id: String): Mono<Bot> = botRepository.findById(id)
                .doOnNext { jdaStarterService.startBot(it) }
                .doOnSubscribe{ println("Retrieve bot to start it") }

    fun downloadBotById(id: String): ByteArrayOutputStream {
        downloadBotCode(id)
        val zipFile = ByteArrayOutputStream()
        ZipOutputStream(BufferedOutputStream(zipFile)).use {
            files(id).forEach { file ->
                file.takeIf { fileOrDir -> fileOrDir.isFile }?.run {
                    it.putNextEntry(ZipEntry(file.absolutePath))
                    it.write(file.readBytes())
                } ?: it.putNextEntry(ZipEntry(file.absolutePath.addSlashIfNotPresent()))
            }
        }
        return zipFile
    }

    private fun downloadBotCode(id: String) {
        val rm = "rm -rf ./$id"
        Runtime.getRuntime().exec(rm)
        val curl = "curl -X GET https://codeload.github.com/jackcat13/discordBotGeneratorBackend/zip/refs/heads/main --output ./$id/DATA.zip"
        Runtime.getRuntime().exec(curl)
        val unzip = "unzip ./$id/DATA.zip"
        Runtime.getRuntime().exec(unzip)
    }

    private fun files(id: String) = File("./$id/discordBotGeneratorBackend-main")
        .walkTopDown()
        .filterNot { it.absolutePath.notExpectedSource() }
        .toList()
}

private fun String.addSlashIfNotPresent(): String = if (!endsWith("/")) "$this/" else this

private fun String.notExpectedSource() = contains("/.gradle")
        || contains("/.idea")
        || contains("/.git")
        || contains("/build/")
        || contains("/test/")
        || contains("DiscordBotGeneratorApplication.kt")
        || contains("com/chrhenry/discordBotGenerator/dto")
        || contains("com/chrhenry/discordBotGenerator/mapper")
        || contains("com/chrhenry/discordBotGenerator/service")
        || contains("com/chrhenry/discordBotGenerator/repository")