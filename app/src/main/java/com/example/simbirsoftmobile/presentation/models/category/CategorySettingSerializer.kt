package com.example.simbirsoftmobile.presentation.models.category

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class CategorySettingSerializer : JsonSerializer<List<CategorySetting>> {
    override fun serialize(
        src: List<CategorySetting>?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?,
    ): JsonElement {
        if (src == null) {
            throw NullPointerException()
        } else {
            val result = JsonObject()
            val array = JsonArray()
            for (setting in src) {
                val categoryJson = JsonObject()
                categoryJson.addProperty("id", setting.category.id)
                categoryJson.addProperty("selected", setting.isSelected)
                array.add(categoryJson)
            }
            result.add("settings", array)

            return result
        }
    }

    companion object {
        val objectType: Type = object : TypeToken<List<CategorySetting>>() {}.type
    }
}
