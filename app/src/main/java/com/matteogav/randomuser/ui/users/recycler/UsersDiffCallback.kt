package com.matteogav.randomuser.ui.users.recycler

import androidx.recyclerview.widget.DiffUtil
import com.matteogav.randomuser.domain.models.User

class UsersDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}