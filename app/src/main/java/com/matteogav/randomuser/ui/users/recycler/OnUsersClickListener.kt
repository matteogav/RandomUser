package com.matteogav.randomuser.ui.users.recycler

import com.matteogav.randomuser.domain.models.User

interface OnUsersClickListener {
    fun onClick(userModel: User)
}