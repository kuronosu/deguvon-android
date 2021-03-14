package dev.kuronosu.deguvon.ui.home.view

import dev.kuronosu.deguvon.datasource.model.LatestEpisode

interface LatestEpisodesListener {
    fun onEpisodeClicked(episode: LatestEpisode, position: Int)
}