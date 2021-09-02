package com.chrhenry.discordBotGenerator.jda

import com.chrhenry.discordBotGenerator.entity.Configuration
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color

val logger: Logger = LoggerFactory.getLogger(Bot::class.java)

fun MessageReceivedEvent.unmuteUser(configuration: Configuration) {
    val userToUnMute = message.contentRaw.substringAfter(configuration.commands.unMute)
    jda.guilds.map { it.getMembersByEffectiveName(userToUnMute.trim(), false) }
            .takeIf { it.isNotEmpty() }
            ?.forEach { processUnMute(it.first(), configuration) }
            ?: logger.error("Pas d'utilisateur trouvé pour le unmute")
}

private fun MessageReceivedEvent.processUnMute(member: Member, configuration: Configuration) {
    member.roles
            .map { it.name }
            .firstOrNull { it == configuration.roles.muteRole }
            ?.let { modifyRolesToUnMute(member, configuration) }
            ?: logger.warn("Cet utilisateur est déjà unmute")
}

private fun MessageReceivedEvent.modifyRolesToUnMute(member: Member, configuration: Configuration) {
    guild.removeRoleFromMember(member, jda.getRolesByName(configuration.roles.muteRole, false).first()).queue()
    guild.addRoleToMember(member, jda.getRolesByName(configuration.roles.activeRole, false).first()).queue()
}

fun MessageReceivedEvent.muteUser(configuration: Configuration) {
    val userToMute = message.contentRaw.substringAfter(configuration.commands.mute)
    jda.guilds.map { it.getMembersByEffectiveName(userToMute.trim(), false) }
            .takeIf { it.isNotEmpty() }
            ?.forEach { processMute(it.first(), configuration) }
            ?: logger.error("Pas d'utilisateur trouvé pour le unmute")
}

private fun MessageReceivedEvent.processMute(member: Member, configuration: Configuration) {
    member.roles
            .map { it.name }
            .firstOrNull { it == configuration.roles.muteRole }
            ?.let { logger.warn("This user is already muted") }
            ?: modifyRolesToMute(member, configuration)
}

private fun MessageReceivedEvent.modifyRolesToMute(member: Member, configuration: Configuration) {
    guild.addRoleToMember(member, jda.getRolesByName(configuration.roles.muteRole, false).first()).queue()
    guild.removeRoleFromMember(member, jda.getRolesByName(configuration.roles.activeRole, false).first()).queue()
}

fun MessageReceivedEvent.returnSurvey(configuration: Configuration) {
    jda.getTextChannelsByName(configuration.channels.messagesChannel, false)
            .firstOrNull()
            ?.sendMessage(message.contentRaw
                    .substringAfter(configuration.commands.sondage)
                    .asEmbed("Question: ", Color.ORANGE))
            ?.queue {
                it.addReaction("✅").queue()
                it.addReaction("❌").queue()
            }
    logger.info("Survey ${message.contentRaw} has been created")
}

fun MessageReceivedEvent.returnHelpPanel(configuration: Configuration) {
    member?.user?.openPrivateChannel()
            ?.queue{
                it.sendMessage(configuration.asHelpMessage().asEmbed("Available commands", Color.GREEN))
                        .queue()
                logger.info("Help embed has been sent to ${member!!.user}")
            }
}

private fun Configuration.asHelpMessage() = """$computerDiscordEmoji ${commands.sondage} QUESTION. Send a survey to the ${channels.messagesChannel} channel
        $computerDiscordEmoji ${commands.mute} USERNAME. Mute the USERNAME giving the ${roles.muteRole} role
        $computerDiscordEmoji ${commands.unMute} USERNAME. Unmute the USERNAME removing the ${roles.muteRole} role
        $computerDiscordEmoji ${commands.mp} CHANNEL_NAME // MESSAGE. Send a MESSAGE to the target CHANNEL_NAME
    """.trimIndent()

private fun String.asEmbed(title: String, color: Color): MessageEmbed = EmbedBuilder()
        .setTitle(embedTitle)
        .setColor(color)
        .addField(title, this, false)
        .setFooter(developedBy, developerAvatarUrl)
        .build()
