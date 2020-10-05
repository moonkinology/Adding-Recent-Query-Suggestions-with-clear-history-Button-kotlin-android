package com.materialsearchview

import android.content.SearchRecentSuggestionsProvider

class MySuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.materialsearchview.MySuggestionProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES
    }
}