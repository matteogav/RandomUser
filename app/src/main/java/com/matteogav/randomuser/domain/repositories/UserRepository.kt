package com.matteogav.randomuser.domain.repositories

import com.matteogav.randomuser.data.models.UserResponse
import com.matteogav.randomuser.domain.models.User

interface UserRepository {

    suspend fun getUsersFromAPI(page: Int, seed: String? = ""): UserResponse

}