package com.example.simbirsoftmobile.presentation.screens.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn
import java.time.format.DateTimeFormatterBuilder
import java.time.LocalDate as JavaLocalDate

fun getRemainingDateInfo(
    start: LocalDate,
    end: LocalDate,
): String {
    val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
    if (today > end) {
        return if (start == end) {
            val formatter = DateTimeFormatterBuilder()
                .appendPattern("LLLL d, YYYY")
                .toFormatter()

            val javaDate: JavaLocalDate =
                JavaLocalDate.of(start.year, start.monthNumber, start.dayOfMonth)

            javaDate.format(formatter)
        } else {
            "Мероприятие уже закончилось"
        }
    }

    val startFormatted = start.format(
        LocalDate.Format {
            byUnicodePattern("dd.MM")
        }
    )
    val endFormatted = end.format(
        LocalDate.Format {
            byUnicodePattern("dd.MM")
        }
    )

    if (today < start) {
        return "Начнётся через ${(start - today).days} дней ($startFormatted-$endFormatted)"
    }

    return "Осталось ${(end - today).days} дней ($startFormatted – $endFormatted)"
}
