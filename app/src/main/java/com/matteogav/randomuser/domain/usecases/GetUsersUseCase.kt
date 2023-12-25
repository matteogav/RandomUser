package com.matteogav.randomuser.domain.usecases

import com.matteogav.randomuser.data.models.UserResponse
import com.matteogav.randomuser.domain.repositories.UserRepository

class GetUsersUseCase constructor(private val userRepository: UserRepository) {

    suspend fun getUsers(page: Int, seed: String?): UserResponse {
        return userRepository.getUsersFromAPI(page, seed)
    }
}