package com.example.simbirsoftmobile.presentation.models.category

data class CategorySetting(
    val category: Category,
    var isSelected: Boolean = true,
)

fun Category.toSettingsModel(): CategorySetting = CategorySetting(this)
