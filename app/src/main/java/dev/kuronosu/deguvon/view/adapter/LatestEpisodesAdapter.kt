package dev.kuronosu.deguvon.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import dev.kuronosu.deguvon.R
import dev.kuronosu.deguvon.model.LatestEpisode

class LatestEpisodesAdapter(private val latestEpisodesListener: LatestEpisodesListener) :
    RecyclerView.Adapter<LatestEpisodesAdapter.ViewHolder>() {

    var latestEpisodes = ArrayList<LatestEpisode>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_home, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val latestEpisode = latestEpisodes[position]
        if (latestEpisode.anime.name.isEmpty()) holder.tvLatestEpisodeAnimeName.visibility =
            View.INVISIBLE
        else holder.tvLatestEpisodeAnimeName.text = latestEpisode.anime.name
        if (latestEpisode.capi.isEmpty()) holder.tvLatestEpisodeCapi.visibility = View.INVISIBLE
        else holder.tvLatestEpisodeCapi.text = latestEpisode.capi.replace("Episodio ", "")
        val color = ContextCompat.getColor(
            holder.ivLatestEpisodeImage.context,
            R.color.white
        )
        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(
                Shimmer.ColorHighlightBuilder()
                    .setHighlightColor(color)
                    .setBaseAlpha(0.9f)
                    .setBaseColor(color)
                    .build()
            )
        }

        holder.ivLatestEpisodeImage.load(
            "https://kuronosu.dev" + latestEpisodes[position].image.replace(
                "thumbs",
                "covers"
            )
        ) {
            crossfade(true)
            placeholder(shimmerDrawable)
        }
        holder.itemView.setOnClickListener {
            latestEpisodesListener.onEpisodeClicked(latestEpisode, position)
        }
    }

    override fun getItemCount() = latestEpisodes.size

    fun updateData(data: List<LatestEpisode>) {
        latestEpisodes.clear()
        latestEpisodes.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivLatestEpisodeImage: ImageView = itemView.findViewById(R.id.latest_episode_image)
        val tvLatestEpisodeAnimeName: TextView =
            itemView.findViewById(R.id.latest_episode_anime_name)
        val tvLatestEpisodeCapi: TextView = itemView.findViewById(R.id.latest_episode_capi)
    }
}