package com.matteogav.randomuser.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val results: List<UserModel>,
    val info: Info
)

@Serializable
data class UserModel(
    val gender: String,
    val name: UserName,
    val location: UserLocation,
    val email: String,
    val registered: UserRegistered,
    val cell: String,
    val id: UserId,
    val picture: UserProfilePicture
)

@Serializable
data class UserName(
    val title: String,
    val first: String,
    val last: String
)

@Serializable
data class UserLocation(
    val street: UserStreet,
    val city: String,
    val state: String,
    val country: String,
    val postcode: Int,
    val coordinates: UserCoordinates,
    val timezone: UserTimezone
)

@Serializable
data class UserStreet(
    val number: Int,
    val name: String
)

@Serializable
data class UserCoordinates(
    val latitude: String,
    val longitude: String
)

@Serializable
data class UserTimezone(
    val offset: String,
    val description: String
)

@Serializable
data class UserRegistered(
    val date: String,
    val age: Int
)

@Serializable
data class UserId(
    val name: String?,
    val value: String?
)

@Serializable
data class UserProfilePicture(
    val large: String,
    val medium: String,
    val thumbnail: String
)

@Serializable
data class Info(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String
)