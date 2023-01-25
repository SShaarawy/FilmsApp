package com.example.filmsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.filmsapp.R
import com.example.filmsapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val sharedViewModel: FilmsViewModel by activityViewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filmTitle = args.id

        sharedViewModel.getFilmDetails(filmTitle)

        sharedViewModel.filmDetails.observe(viewLifecycleOwner){
            val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
            toolbar.title = it.Title

            binding.tvImdbRateDetail.text = getString(R.string.imdb_rate,it.imdbRating)
            binding.tvPlotDetail.text = it.Plot
            binding.tvGenresDetail.text = it.Genre
            Glide.with(this)
                .load(it.Poster)
                .into(binding.ivPosterDetail)
        }

    }

}