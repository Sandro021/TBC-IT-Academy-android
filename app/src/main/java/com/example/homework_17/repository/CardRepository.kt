package com.example.homework_17.repository

import android.content.Context
import com.example.homework_17.R
import com.example.homework_17.model.CardInfo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class CardRepository(private val context: Context) {

    private val fileName = "card_info.json"

    private fun getFile(): File = File(context.filesDir, fileName)

    fun loadCards(): List<CardInfo> {
        val file = getFile()

        if (!file.exists()) {

            return try {

                val jsonFromRaw =
                    context.resources.openRawResource(R.raw.card_info)
                        .bufferedReader()
                        .use { it.readText() }

                val initialCards: List<CardInfo> = Json.decodeFromString(jsonFromRaw)
                saveCards(initialCards)
                initialCards
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        } else {

            return try {
                val json = file.readText()
                Json.decodeFromString(json)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    fun saveCards(cards: List<CardInfo>) {
        try {
            val json = Json.encodeToString(cards)
            getFile().writeText(json)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}