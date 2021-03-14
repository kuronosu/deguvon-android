package dev.kuronosu.deguvon.ui.search.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.kuronosu.deguvon.R
import dev.kuronosu.deguvon.datasource.model.Anime
import dev.kuronosu.deguvon.utils.loadWithShimmerPlaceholder


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
        holder.tvSearchAnimeName.text = anime.name
        holder.tvSearchAnimeType.text = anime.type.name
        holder.ivSearchAnime.loadWithShimmerPlaceholder("https://kuronosu.dev" + animes[position].cover)
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
        val tvSearchAnimeName: TextView = itemView.findViewById(R.id.search_anime_name)
        val tvSearchAnimeType: TextView = itemView.findViewById(R.id.search_anime_type)
    }
}