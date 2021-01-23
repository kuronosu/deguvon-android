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
import dev.kuronosu.deguvon.model.Anime

class SearchAnimeAdapter(private val listener: SearchAnimeListener) :
    RecyclerView.Adapter<SearchAnimeAdapter.ViewHolder>() {

    var animes = ArrayList<Anime>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_search, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val anime = animes[position]
        /*if (anime.name.isEmpty()) holder.tvSearchAnime.visibility =
            View.INVISIBLE
        else holder.tvSearchAnime.text = anime.name*/
        val color = ContextCompat.getColor(
            holder.ivSearchAnime.context,
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
        holder.tvSearchAnime.text = anime.name
        holder.ivSearchAnime.load("https://kuronosu.dev" + animes[position].cover) {
            crossfade(true)
            placeholder(shimmerDrawable)
        }
        holder.itemView.setOnClickListener {
            listener.onAnimeClicked(anime, position)
        }
    }

    override fun getItemCount() = animes.size

    fun updateData(data: List<Anime>) {
        animes.clear()
        animes.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivSearchAnime: ImageView = itemView.findViewById(R.id.search_anime_image)
        val tvSearchAnime: TextView = itemView.findViewById(R.id.search_anime_name)
    }
}