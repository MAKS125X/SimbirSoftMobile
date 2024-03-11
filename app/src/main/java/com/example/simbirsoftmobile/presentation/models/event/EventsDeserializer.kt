package com.example.simbirsoftmobile.presentation.models.event

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import kotlinx.datetime.LocalDate
import java.lang.reflect.Type

class EventsDeserializer : JsonDeserializer<List<Event>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): List<Event> {
        val jsonObject =
            json?.asJsonObject ?: throw JsonParseException("Ошибка преобразования json: $json")

        val events = mutableListOf<Event>()

        for (entry in jsonObject.entrySet()) {
            val title = entry.key
            val value = entry.value.asJsonObject
            val id = value["id"].asInt
            val description = value["description"].asString
            val imageUrl = value["imageUrl"].asInt
            val organizerName = value["organizerName"].asString

            val categories: MutableList<Int> = mutableListOf()
            val categoriesJsonArray = value["categories"].asJsonArray
            for (category in categoriesJsonArray) {
                categories.add(category.asInt)
            }

            val address = value["address"].asString

            val numbers: MutableList<String> = mutableListOf()
            val numbersJsonArray = value["numbers"].asJsonArray
            for (number in numbersJsonArray) {
                numbers.add(number.asString)
            }

            val email = value["email"].asString
            val siteUrl = value["siteUrl"].asString
            val start = LocalDate.parse(value["start"].asString)
            val end = LocalDate.parse(value["end"].asString)

            val event = Event(
                id,
                title,
                description,
                imageUrl,
                organizerName,
                categories,
                address,
                numbers,
                email,
                siteUrl,
                start,
                end,
            )

            events.add(event)
        }

        return events
    }

    companion object {
        val objectType: Type = object : TypeToken<List<Event>>() {}.type
    }
}
