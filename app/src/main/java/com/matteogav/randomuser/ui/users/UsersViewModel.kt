package com.matteogav.randomuser.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.matteogav.randomuser.data.sources.UsersSource
import com.matteogav.randomuser.domain.models.User
import com.matteogav.randomuser.domain.usecases.GetUsersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class UsersViewModel constructor(
    private val getUsersUseCase: GetUsersUseCase,
) : ViewModel() {

    private val originalUsers = Pager(config = PagingConfig(1),
        pagingSourceFactory = { UsersSource(getUsersUseCase) }
    ).flow.cachedIn(viewModelScope)

    private val currentQuery = MutableStateFlow("")

    val filteredUsers: Flow<PagingData<User>> =
        currentQuery.flatMapLatest { query ->
            originalUsers.map { pagingData ->
                pagingData.filter {
                    it.name?.contains(query, ignoreCase = true) ?: false || it.email?.contains(query, ignoreCase = true) ?: false
                }
            }
        }
    fun searchUsers(query: String) {
        currentQuery.value = query
    }
}