package dev.kuronosu.deguvon.view.adapter

import dev.kuronosu.deguvon.model.LatestEpisode

interface LatestEpisodesListener {
    fun onEpisodeClicked(episode: LatestEpisode, position: Int)
}