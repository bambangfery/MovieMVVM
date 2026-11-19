package com.bambang.moviemvvm.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bambang.moviemvvm.R
import com.bambang.moviemvvm.data.model.GenresItem

class GenreListAdapter (private var resultsGenres: ArrayList<GenresItem>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val genreName: TextView = itemView.findViewById(R.id.genreName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.items_genre
                , parent, false)
        return GenreViewHolder(view)
    }

    override fun getItemCount(): Int {
       return resultsGenres.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GenreViewHolder) {
            holder.genreName.text = resultsGenres[position].name
        }
    }
}
