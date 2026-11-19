package com.bambang.moviemvvm.ui.movie.adapter

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.bambang.moviemvvm.utils.Constant
import com.bambang.moviemvvm.R
import com.bambang.moviemvvm.data.network.ServerManager
import com.bambang.moviemvvm.data.model.ResultsMovies
import com.bambang.moviemvvm.ui.detail.DetailsMovieActivity

class ResultsMoviesAdapter(private var resultsMovies: ArrayList<ResultsMovies?>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    lateinit var mcontext: Context

    class ResultsMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val date: TextView = itemView.findViewById(R.id.date)
        val ratings: TextView = itemView.findViewById(R.id.ratings)
        val overview: TextView = itemView.findViewById(R.id.overview)
        val poster: ImageView = itemView.findViewById(R.id.poster)
        val lyItems: LinearLayout = itemView.findViewById(R.id.ly_items)
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addData(dataViews: ArrayList<ResultsMovies?>) {
        this.resultsMovies.addAll(dataViews)
        notifyDataSetChanged()
    }

    fun addLoadingView() {
        //add loading item
        Handler().post {
            resultsMovies.add(null)
            notifyItemInserted(resultsMovies.size - 1)
        }
    }

    fun removeLoadingView() {
        //Remove loading item
        if (resultsMovies.size != 0) {
            resultsMovies.removeAt(resultsMovies.size - 1)
            notifyItemRemoved(resultsMovies.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mcontext = parent.context
        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_results_movies
                    , parent, false)
            ResultsMoviesViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(mcontext).inflate(R.layout.progress_loading
                    , parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return resultsMovies.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (resultsMovies[position] == null) {
            Constant.VIEW_TYPE_LOADING
        } else {
            Constant.VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ResultsMoviesViewHolder) {
            val movie = resultsMovies[position]
            holder.title.text = movie?.title
            holder.date.text = "( " + movie?.releaseDate + " )"
            holder.ratings.text = movie?.voteCount.toString() + " Ratings"
            holder.overview.text = movie?.overview
            Picasso.get()
                .load(ServerManager.SERVER_IMAGE_W500 + movie?.posterPath)
                .error(R.mipmap.no_image)
                .into(holder.poster)
            holder.lyItems.setOnClickListener {
                val detailsMovieIntent = Intent(mcontext, DetailsMovieActivity::class.java)
                    .apply {
                        putExtra("id_movie", movie?.id)
                    }
                mcontext.startActivity(detailsMovieIntent)
            }
        }
    }
}
