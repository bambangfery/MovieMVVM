package com.bambang.moviemvvm.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.squareup.picasso.Picasso
import com.bambang.moviemvvm.utils.ApiException
import com.bambang.moviemvvm.utils.Coroutines
import com.bambang.moviemvvm.utils.NoInternetException
import com.bambang.moviemvvm.R
import com.bambang.moviemvvm.data.network.ServerManager
import com.bambang.moviemvvm.data.model.DetailsMovieResponse
import com.bambang.moviemvvm.databinding.ActivityDetailMovieBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class DetailsMovieActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory: DetailsMovieViewModelFactory by instance()

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var detailsMovieViewModel: DetailsMovieViewModel
    private lateinit var adapterGenresLists: GenreListAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    var id_movie: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_movie)

        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false

        val initialHeaderPaddingTop = binding.linearLayout.paddingTop
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val statusBars = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            val navBars = insets.getInsets(WindowInsetsCompat.Type.navigationBars())

            binding.linearLayout.updatePadding(top = initialHeaderPaddingTop + statusBars.top)
            binding.root.updatePadding(bottom = navBars.bottom)

            insets
        }


        detailsMovieViewModel =
            ViewModelProvider(this, factory).get(DetailsMovieViewModel::class.java)
        lifecycle.addObserver(binding.youtubePlayerView)
        id_movie = intent.getIntExtra("id_movie",0)
        getDetailsMovie(id_movie.toString())
    }

    private fun getDetailsMovie(idMovie: String){
        binding.progressBar.visibility = View.VISIBLE
        Coroutines.main {
            try {
                val getDetailsMovie =
                    detailsMovieViewModel.getDetailsMovie(
                        resources.getString(R.string.API_KEY_TMDB), idMovie)
                setDataDetails(getDetailsMovie)
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

    private fun setDataDetails(detail: DetailsMovieResponse){
        Picasso.get()
            .load(ServerManager.SERVER_IMAGE_ORI+detail.backdropPath)
            .error(resources.getDrawable(R.mipmap.no_image))
            .into(binding.banner)

        binding.title.text = detail.title
        binding.releaseDate.text = detail.releaseDate
        binding.ratings.text = detail.voteCount.toString()+" Ratings"

        adapterGenresLists = GenreListAdapter(detail.genres)
        adapterGenresLists.notifyDataSetChanged()
        mLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvGenre.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            removeAllViews()
            adapter = adapterGenresLists
        }

        binding.overview.text = detail.overview
        getReviewMovie(detail.id.toString())
        getTrailerMovie(detail.id.toString())

    }

    private fun getReviewMovie(idMovie: String){
        binding.progressBar.visibility = View.VISIBLE
        Coroutines.main {
            try {
                val getReviewMovie =
                    detailsMovieViewModel.getReviewMovie(
                        resources.getString(R.string.API_KEY_TMDB), idMovie)
                if (getReviewMovie.totalResults>0)
                    binding.review.text = getReviewMovie.results[0].content
                else
                    binding.review.text = "No Review"
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

    private fun getTrailerMovie(idMovie: String){
        binding.progressBar.visibility = View.VISIBLE
        Coroutines.main {
            try {
                val getTrailerMovie =
                    detailsMovieViewModel.getTrailerMovie(
                        resources.getString(R.string.API_KEY_TMDB), idMovie)

                val trailer = getTrailerMovie.results.find {
                    it.site.equals("YouTube", ignoreCase = true) && it.type.equals("Trailer", ignoreCase = true)
                } ?: getTrailerMovie.results.find {
                    it.site.equals("YouTube", ignoreCase = true)
                }

                if (trailer != null && !trailer.key.isNullOrEmpty()){
                    binding.youtubePlayerView.visibility = View.VISIBLE
                    val options = IFramePlayerOptions.Builder(baseContext).controls(1).build()
                    binding.youtubePlayerView.initialize(
                        youTubePlayerListener = object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                youTubePlayer.cueVideo(trailer.key, 0f)
                            }
                        },
                        handleNetworkEvents = true,
                        playerOptions = options,
                        videoId = trailer.key
                    )
                } else {
                    binding.youtubePlayerView.visibility = View.GONE
                }
            } catch (e: ApiException) {
                e.printStackTrace()
                Toast.makeText(
                    this,
                    e.message,
                    Toast.LENGTH_LONG
                ).show()
                binding.youtubePlayerView.visibility = View.GONE
            } catch (e: NoInternetException) {
                e.printStackTrace()
                Toast.makeText(
                    this,
                    e.message,
                    Toast.LENGTH_LONG
                ).show()
                binding.youtubePlayerView.visibility = View.GONE
            }
            binding.progressBar.visibility = View.GONE
        }
    }


}