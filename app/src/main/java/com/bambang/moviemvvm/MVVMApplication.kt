package com.bambang.moviemvvm

import android.app.Application
import com.bambang.moviemvvm.data.network.ApiClient
import com.bambang.moviemvvm.data.network.NetworkConnectionInterceptor
import com.bambang.moviemvvm.data.repositories.DetailsMovieRepository
import com.bambang.moviemvvm.data.repositories.MovieRepository
import com.bambang.moviemvvm.data.repositories.ReviewMovieRepository
import com.bambang.moviemvvm.data.repositories.TrailerMovieRepository
import com.bambang.moviemvvm.ui.detail.DetailsMovieViewModelFactory
import com.bambang.moviemvvm.ui.movie.MovieViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class MVVMApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { ApiClient(instance()) }
        bind() from singleton { MovieRepository(instance()) }
        bind() from provider { MovieViewModelFactory(instance()) }
        bind() from singleton { DetailsMovieRepository(instance()) }
        bind() from singleton { ReviewMovieRepository(instance()) }
        bind() from singleton { TrailerMovieRepository(instance()) }
        bind() from provider { DetailsMovieViewModelFactory(instance(), instance(), instance()) }

    }

}