package com.matteogav.randomuser.data.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.matteogav.randomuser.domain.models.User
import com.matteogav.randomuser.domain.models.toDomain
import com.matteogav.randomuser.domain.usecases.GetUsersUseCase

class UsersSource (private val getUsersUseCase: GetUsersUseCase)
    : PagingSource<Int, User>() {

    var seed: String? = null

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val key = params.key ?: 1
            val result = getUsersUseCase.getUsers(key, seed)

            val prevKey = if (key == 1) null else key - 1
            val nextKey = if (result.results.isEmpty()) null else key + 1
            if(seed != "") seed = result.info.seed

            LoadResult.Page(
                data = result.results?.map { it.toDomain() } ?: emptyList(),
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (error: Throwable) {
            LoadResult.Error(error)
        }
    }
}