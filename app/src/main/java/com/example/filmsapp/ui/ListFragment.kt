package com.example.filmsapp.ui

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmsapp.R
import com.example.filmsapp.adapter.FilmsAdapter
import com.example.filmsapp.databinding.FragmentListBinding
import com.example.filmsapp.model.Search
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val sharedViewModel: FilmsViewModel by activityViewModels()
    private val filmsAdapter = FilmsAdapter()
    private  var searchText = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFilms.apply {
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.btnSearch.setOnClickListener {
            searchText = binding.etSearch.text.toString()

            if(searchText.isNotEmpty()){
                sharedViewModel.searchFilms(searchText)
            } else {
                alertDialog(resources.getString(R.string.caution))
            }
        }

        sharedViewModel.filmList.observe(viewLifecycleOwner){
            loading(it)
        }
    }

    private fun loading(search: Search) {
        binding.pbLoading.visibility = View.VISIBLE
        binding.rvFilms.visibility = View.GONE
        binding.tvError.visibility = View.GONE

        object : CountDownTimer(1000,1000) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                binding.pbLoading.visibility = View.GONE

                if(search.Response == "False"){
                   // filmsAdapter.differ.submitList(mutableListOf<Film>(Film("","","","","")))
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = resources.getString(R.string.error,searchText)
                } else {
                    filmsAdapter.differ.submitList(search.Search)

                    binding.rvFilms.visibility = View.VISIBLE
                    println("fragment "+ search.Search)
                }
            }
        }.start()
    }

    private fun alertDialog(message : String) {
        MaterialAlertDialogBuilder(requireActivity())
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton(resources.getString(R.string.tamam)){ _, _ -> }
            .create()
            .show()
    }
}