package com.uk.clearscore

import com.uk.clearscore.viewmodels.MainViewModel
import dagger.Module
import dagger.Provides
import io.mockk.impl.annotations.MockK
import org.mockito.Mockito.mock

/**
 * Package com.uk.clearscore in

 * Project ClearScore

 * Created by Maxwell on 02/06/2022
 */
@Module
object TestMainViewModelModule {

    @MockK
    lateinit var viewModelFactory: MainViewModel.MainViewFactory

    @JvmStatic
    @Provides
    fun provideFavoritesViewModelFactory(): MainViewModel.MainViewFactory {
        return viewModelFactory
    }
}