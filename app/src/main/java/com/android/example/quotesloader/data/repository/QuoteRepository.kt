package com.android.example.quotesloader.data.repository

import kotlinx.coroutines.delay

class QuoteRepository {
    suspend fun fetchQuotes(): List<String> {
        delay(2000) // Stimulate network delay or latency

        return listOf(
            "Believe in yourself",
            "Stay positive, work hard and make it happen",
            "Dream big and dare to fail",
            "Every moment is a fresh beginning",
            "The harder you work, the luckier you get"
        )
    }
}