package com.matteogav.randomuser.ui.users.recycler

import android.graphics.Typeface
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.matteogav.randomuser.databinding.ItemUserBinding
import com.matteogav.randomuser.domain.models.User

class UsersViewHolder(view: View, private val listener: OnUsersClickListener): RecyclerView.ViewHolder(view) {

    val binding = ItemUserBinding.bind(view)

    fun bind(userModel: User) {
        binding.userNameTextView.isSingleLine = true
        binding.userNameTextView.ellipsize = TextUtils.TruncateAt.END
        binding.userNameTextView.setTypeface(null, Typeface.BOLD)
        binding.userNameTextView.textSize = 16f
        userModel.name.let {
            binding.userNameTextView.text = it
        }

        binding.userEmailTextView.isSingleLine = true
        binding.userEmailTextView.ellipsize = TextUtils.TruncateAt.END
        binding.userEmailTextView.textSize = 14f
        userModel.email.let {
            binding.userEmailTextView.text = it
        }

        binding.userImageView.load(userModel.image)

        itemView.setOnClickListener { listener.onClick(userModel) }
    }

}