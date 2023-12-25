package com.matteogav.randomuser.domain.models

import com.matteogav.randomuser.data.models.UserModel
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.TimeZone

enum class Gender {
    Female,
    Male
}

data class User(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val gender: Gender? = null,
    val image: String? = null,
    val registrationDate: String? = null,
    val cell: String? = null,
    val latitude: String? = null,
    val longitude: String? = null
): Serializable

fun UserModel.toDomain() =
    User(
        id.value,
        "${name.first} ${name.last}",
        email,
        if(gender=="male") Gender.Male
        else Gender.Female,
        picture.large,
        dateParser(registered.date),
        cellParser(cell),
        location.coordinates.latitude,
        location.coordinates.longitude,
    )

fun cellParser(cell: String): String = "+34 ${cell.replace("-", "")}"

fun dateParser(date: String): String {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    originalFormat.timeZone = TimeZone.getTimeZone("UTC")
    val dateParse = originalFormat.parse(date)
    val parsedFormat = SimpleDateFormat("dd/MM/yyyy")

    return parsedFormat.format(dateParse)
}