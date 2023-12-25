package com.matteogav.randomuser.ui.users.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import com.matteogav.randomuser.R
import com.matteogav.randomuser.domain.models.User
import kotlinx.coroutines.CoroutineScope

class UsersAdapter(private val listener: OnUsersClickListener) :
    PagingDataAdapter<User, UsersViewHolder>(UsersDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UsersViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = getItem(position)
        user?.let {
            holder.bind(user)
        }
    }
}