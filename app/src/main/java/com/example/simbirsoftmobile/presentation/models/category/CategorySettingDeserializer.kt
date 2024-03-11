package com.example.simbirsoftmobile.presentation.models.category

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class CategorySettingDeserializer : JsonDeserializer<List<CategorySetting>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): List<CategorySetting> {
        val jsonSettingsArray =
            json?.asJsonObject?.get("settings")?.asJsonArray
                ?: throw IllegalArgumentException("Incorrect data format")

        val result = mutableListOf<CategorySetting>()

        for (jsonSetting in jsonSettingsArray) {
            val setting = jsonSetting.asJsonObject
            val category =
                when (val id = setting["id"].asInt) {
                    1 -> Category.CHILDREN
                    2 -> Category.ADULTS
                    3 -> Category.OLD
                    4 -> Category.ANIMALS
                    5 -> Category.EVENTS
                    else -> throw IllegalArgumentException("Unknown category with id: $id")
                }
            val isSelected = setting["selected"].asBoolean

            result.add(CategorySetting(category, isSelected))
        }

        return result
    }

    companion object {
        val objectType: Type = object : TypeToken<List<CategorySetting>>() {}.type
    }
}
