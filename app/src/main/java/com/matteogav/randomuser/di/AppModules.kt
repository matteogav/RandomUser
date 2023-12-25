package com.matteogav.randomuser.di

import com.matteogav.randomuser.data.repositories.UserRepositoryImpl
import com.matteogav.randomuser.data.sources.UsersSource
import com.matteogav.randomuser.domain.repositories.UserRepository
import com.matteogav.randomuser.domain.usecases.GetUsersUseCase
import com.matteogav.randomuser.ui.users.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        UsersViewModel(get())
    }
}

val useCaseModule = module {
    single {
        GetUsersUseCase(get())
    }
}

val repositoryModule = module {
    single<UserRepository> {
        UserRepositoryImpl()
    }
}