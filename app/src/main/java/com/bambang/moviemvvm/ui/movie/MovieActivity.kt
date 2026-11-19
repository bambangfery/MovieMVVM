package com.bambang.moviemvvm.ui.movie

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bambang.moviemvvm.data.model.Genres
import com.bambang.moviemvvm.R
import com.bambang.moviemvvm.data.model.ResultsMovies
import com.bambang.moviemvvm.databinding.ActivityMovieBinding
import com.bambang.moviemvvm.ui.movie.adapter.GenresItemsAdapter
import com.bambang.moviemvvm.ui.movie.adapter.ResultsMoviesAdapter
import com.bambang.moviemvvm.utils.ApiException
import com.bambang.moviemvvm.utils.Coroutines
import com.bambang.moviemvvm.utils.NoInternetException
import com.bambang.moviemvvm.utils.OnLoadMoreListener
import com.bambang.moviemvvm.utils.RecyclerViewLoadMoreScroll
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MovieActivity : AppCompatActivity(), KodeinAware, GenresItemsAdapter.OnItemClickListener {

    override val kodein by kodein()
    private val factory: MovieViewModelFactory by instance()

    private lateinit var binding: ActivityMovieBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mLayoutManager2: RecyclerView.LayoutManager
    private lateinit var adapterGenresItems: GenresItemsAdapter
    private lateinit var adapterResultsMovies: ResultsMoviesAdapter
    private lateinit var loadMoreResultsMovies: ArrayList<ResultsMovies?>


    var page: Int = 1
    var totalPages: Int = 1
    var isFirst: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)

        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false

        val initialHeaderPaddingTop = binding.linearLayout.paddingTop
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val statusBars = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            val navBars = insets.getInsets(WindowInsetsCompat.Type.navigationBars())

            binding.linearLayout.updatePadding(top = initialHeaderPaddingTop + statusBars.top)
            binding.root.updatePadding(bottom = navBars.bottom)

            insets
        }

        movieViewModel = ViewModelProvider(this, factory).get(MovieViewModel::class.java)

        getGenresMovie()
    }

    private fun getGenresMovie() {
        binding.progressBar.visibility = View.VISIBLE
        Coroutines.main {
            try {
                val getGenres =
                    movieViewModel.getGenresMovie(resources.getString(R.string.API_KEY_TMDB))
                if (getGenres.genres.size > 0) {
                    binding.noresult.visibility = View.GONE
                    binding.rvListMovie.visibility = View.VISIBLE
                    initListGenres(getGenres.genres)
                } else {
                    binding.noresult.visibility = View.VISIBLE
                    binding.rvListMovie.visibility = View.GONE
                }
            } catch (e: ApiException) {
                e.printStackTrace()
                Toast.makeText(
                    this
                    , e.message
                    , Toast.LENGTH_LONG
                )
                    .show()
            } catch (e: NoInternetException) {
                e.printStackTrace()
                Toast.makeText(
                    this
                    , e.message
                    , Toast.LENGTH_LONG
                )
                    .show()
            }
            binding.progressBar.visibility = View.GONE
        }

    }

    private fun initListGenres(genresItem: ArrayList<Genres?>) {
        adapterGenresItems =
            GenresItemsAdapter(genresItem)
        adapterGenresItems.notifyDataSetChanged()
        mLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapterGenresItems.listener = this
        binding.rvGenre.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            removeAllViews()
            adapter = adapterGenresItems
        }

        getResultsMovie(genresItem.get(0)?.id.toString())

    }

    override fun onItemClick(rvGenreItems: Genres?, viewHolder: RecyclerView.ViewHolder) {
        page = 1
        isFirst = 1
        getResultsMovie(rvGenreItems?.id.toString())
    }

    private fun getResultsMovie(genre: String) {
        binding.progressBar.visibility = View.VISIBLE
        Coroutines.main {
            try {
                val getResultsMovie =
                    movieViewModel.getResultsMovie(
                        resources.getString(R.string.API_KEY_TMDB), genre, page.toString()
                    )
                if (getResultsMovie.totalResults > 0) {
                    binding.noresult.visibility = View.GONE
                    binding.rvListMovie.visibility = View.VISIBLE
                    totalPages = getResultsMovie.totalPages
                    initResultsMovie(getResultsMovie.results, genre)
                } else {
                    binding.noresult.visibility = View.VISIBLE
                    binding.rvListMovie.visibility = View.GONE
                }
            } catch (e: ApiException) {
                e.printStackTrace()
                Toast.makeText(
                    this
                    , e.message
                    , Toast.LENGTH_LONG
                )
                    .show()
            } catch (e: NoInternetException) {
                e.printStackTrace()
                Toast.makeText(
                    this
                    , e.message
                    , Toast.LENGTH_LONG
                )
                    .show()
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun initResultsMovie(resultsMovie: ArrayList<ResultsMovies?>, genre: String) {
        adapterResultsMovies =
            ResultsMoviesAdapter(
                resultsMovie
            )
        adapterResultsMovies.notifyDataSetChanged()
        mLayoutManager2 = LinearLayoutManager(this)
        binding.rvListMovie.apply {
            layoutManager = mLayoutManager2
            setHasFixedSize(true)
            removeAllViews()
            adapter = adapterResultsMovies
        }
        binding.itemsswipetorefresh.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(this, R.color.colorPrimary))
        binding.itemsswipetorefresh.setColorSchemeColors(Color.WHITE)
        binding.itemsswipetorefresh.setOnRefreshListener {
            page = 1
            isFirst = 1
            resultsMovie.clear()
            adapterResultsMovies.addData(resultsMovie)
            adapterResultsMovies.notifyDataSetChanged()
            Handler().postDelayed(
                {
                    getResultsMovie(genre)
                    binding.itemsswipetorefresh.isRefreshing = false
                },1000
            )
        }

        scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager2 as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                if (isFirst==1){
                    isFirst=0
                }else{
                    loadMoreData(genre)
                }
            }
        })
        binding.rvListMovie.addOnScrollListener(scrollListener)

    }

    private fun loadMoreData(genre: String) {
        adapterResultsMovies.addLoadingView()
        loadMoreResultsMovies = ArrayList()
        if (page <= totalPages)
            page = page.inc()
        Coroutines.main {
            try {
                val getResultsMovie =
                    movieViewModel.getResultsMovie(
                        resources.getString(R.string.API_KEY_TMDB), genre, page.toString()
                    )
                if (getResultsMovie.totalResults > 0) {
                    loadMoreResultsMovies.addAll(getResultsMovie.results)
                    adapterResultsMovies.removeLoadingView()
                    adapterResultsMovies.addData(loadMoreResultsMovies)
                    scrollListener.setLoaded()
                    binding.rvListMovie.post {
                        adapterResultsMovies.notifyDataSetChanged()
                    }
                } else {
                    adapterResultsMovies.removeLoadingView()
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            } catch (e: NoInternetException) {
                e.printStackTrace()
            }
        }

    }
}