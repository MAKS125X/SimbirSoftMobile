package com.example.simbirsoftmobile.presentation.models.category

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.simbirsoftmobile.R

enum class Category(
    val id: Int,
    @DrawableRes val imageResId: Int,
    @StringRes val stringResId: Int,
) {
    CHILDREN(1, R.drawable.category_children, R.string.children),
    ADULTS(2, R.drawable.category_adults, R.string.adults),
    OLD(3, R.drawable.category_old, R.string.old),
    ANIMALS(4, R.drawable.category_animals, R.string.animals),
    EVENTS(5, R.drawable.category_events, R.string.events),
}
