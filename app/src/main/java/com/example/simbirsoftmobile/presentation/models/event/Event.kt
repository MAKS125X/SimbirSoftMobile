package com.example.simbirsoftmobile.presentation.models.event

import kotlinx.datetime.LocalDate

data class Event(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: Int,
    val organizerName: String,
    val categoryList: List<Int>,
    val address: String,
    val phoneNumbers: List<String>,
    val email: String,
    val siteUrl: String,
    val dateStart: LocalDate,
    val dateEnd: LocalDate,
)
