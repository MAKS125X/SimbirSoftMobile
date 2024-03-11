package com.example.simbirsoftmobile.presentation.models.category

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.Locale

class CategoryDeserializer : JsonDeserializer<List<Category>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): List<Category> {
        val list = mutableListOf<Category>()
        val jsonObject = json?.asJsonObject
        jsonObject?.let {
            for (category in jsonObject.getAsJsonArray("categories")) {
                val name = category.asString
                list.add(
                    when (name.lowercase(Locale.ROOT)) {
                        "дети" -> Category.CHILDREN
                        "взрослые" -> Category.ADULTS
                        "пожилые" -> Category.OLD
                        "животные" -> Category.ANIMALS
                        "мероприятия" -> Category.EVENTS
                        else -> throw IllegalArgumentException("Unknown category: $name")
                    }
                )
            }
            return list
        }
        throw IllegalArgumentException("Incorrect data format")
    }

    companion object {
        val objectType: Type = object : TypeToken<List<Category>>() {}.type
    }
}
