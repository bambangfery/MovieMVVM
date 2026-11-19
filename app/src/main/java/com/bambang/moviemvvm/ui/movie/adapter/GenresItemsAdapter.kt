package com.bambang.moviemvvm.ui.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bambang.moviemvvm.data.model.Genres
import com.bambang.moviemvvm.R

class GenresItemsAdapter(private var genresCells: ArrayList<Genres?>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    lateinit var mcontext: Context
    var row_index: Int = -1
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameGenre: TextView = itemView.findViewById(R.id.name_genre)
        val lyGenre: LinearLayout = itemView.findViewById(R.id.ly_genre)
    }
    var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(
            rvGenreItems: Genres?,
            viewHolder: RecyclerView.ViewHolder
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mcontext = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_genres, parent,
            false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return genresCells.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.nameGenre.text = genresCells[position]?.name
            holder.lyGenre.setOnClickListener {
                listener?.onItemClick(genresCells[position], holder)
                row_index = position
                notifyDataSetChanged()
            }
            if (row_index == -1 && position == 0) {
                holder.lyGenre.background =
                    ContextCompat.getDrawable(mcontext, R.drawable.chip_selected)
            } else if (row_index == position) {
                holder.lyGenre.background =
                    ContextCompat.getDrawable(mcontext, R.drawable.chip_selected)
            } else {
                holder.lyGenre.background =
                    ContextCompat.getDrawable(mcontext, R.drawable.chip_unselected)
            }
        }
    }
}
