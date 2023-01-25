package com.example.filmsapp.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmsapp.R
import com.example.filmsapp.databinding.RecyclerItemBinding
import com.example.filmsapp.model.Film

class FilmsAdapter : RecyclerView.Adapter<FilmsAdapter.FilmsViewHolder>() {

    companion object DiffCallBack : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.imdbID == newItem.imdbID
        }

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, DiffCallBack)

    class FilmsViewHolder(val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        return FilmsViewHolder(RecyclerItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        val film = differ.currentList[position]
        holder.apply {
            binding.tvTitle.text = film.Title
            binding.tvYear.text = film.Year
        }
        Glide.with(holder.itemView)
            .load(film.Poster)
            .into(holder.binding.ivPoster)

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("id", film.imdbID)

            holder.itemView.findNavController()
                .navigate(R.id.action_listFragment_to_detailsFragment, bundle)
        }
    }

    override fun getItemCount(): Int {

        return differ.currentList.size
    }
}



