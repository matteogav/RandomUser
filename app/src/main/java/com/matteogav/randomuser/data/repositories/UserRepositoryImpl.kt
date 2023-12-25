package com.matteogav.randomuser.data.repositories

import com.matteogav.randomuser.data.models.Info
import com.matteogav.randomuser.data.models.UserResponse
import com.matteogav.randomuser.data.services.APIBaseService
import com.matteogav.randomuser.domain.models.User
import com.matteogav.randomuser.domain.models.toDomain
import com.matteogav.randomuser.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl: UserRepository {

    private val userService = APIBaseService.userService

    override suspend fun getUsersFromAPI(page: Int, seed: String?): UserResponse {
        return withContext(Dispatchers.IO) {
            val result = userService.getUsers(page, 20, seed,"es", "login,dob,phone,nat")
            return@withContext result ?: UserResponse(emptyList(), Info("", 0, 1, "1.1"))
        }
    }
}