package dev.kuronosu.deguvon.view.adapter

import dev.kuronosu.deguvon.model.Anime

interface SearchAnimeListener {
    fun onAnimeClicked(anime: Anime, position: Int)
}