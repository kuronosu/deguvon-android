package dev.kuronosu.deguvon.ui.search.view

import dev.kuronosu.deguvon.datasource.model.Anime

interface SearchAnimeListener {
    fun onAnimeClicked(anime: Anime, position: Int)
}