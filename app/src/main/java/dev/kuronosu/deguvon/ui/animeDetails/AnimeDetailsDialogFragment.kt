package dev.kuronosu.deguvon.ui.animeDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.navGraphViewModels
import dev.kuronosu.deguvon.R
import dev.kuronosu.deguvon.databinding.DialogFragmentAnimeDetailsBinding
import dev.kuronosu.deguvon.datasource.model.Anime

const val KEY_ANIME = "ANIME"

class AnimeDetailsDialogFragment : DialogFragment() {
    private val viewModel: AnimeDetailsViewModel by navGraphViewModels(R.id.mobile_navigation)

    private var _binding: DialogFragmentAnimeDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentAnimeDetailsBinding.inflate(inflater, container, false)
        val anime = requireArguments().getParcelable<Anime>(KEY_ANIME)!!
        binding.toolbarLayout.title = anime.name
        return binding.root
    }
}