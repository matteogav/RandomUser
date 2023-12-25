package com.matteogav.randomuser.ui.users

import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.Menu
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.matteogav.randomuser.R
import com.matteogav.randomuser.databinding.ActivityUsersBinding
import com.matteogav.randomuser.di.repositoryModule
import com.matteogav.randomuser.di.useCaseModule
import com.matteogav.randomuser.di.viewModelModule
import com.matteogav.randomuser.domain.models.User
import com.matteogav.randomuser.ui.userinfo.UserInfoActivity
import com.matteogav.randomuser.ui.users.recycler.OnUsersClickListener
import com.matteogav.randomuser.ui.users.recycler.UsersAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.context.startKoin
import java.io.Serializable

class UsersActivity : AppCompatActivity(), OnUsersClickListener {

    private lateinit var binding: ActivityUsersBinding
    private lateinit var viewModel: UsersViewModel
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidLogger()
            androidContext(androidContext = this@UsersActivity)
            modules(
                listOf(viewModelModule, useCaseModule, repositoryModule)
            )
        }

        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initActionBar()
        initUI()
    }

    private fun initViewModel() {
        viewModel = getViewModel()
    }

    private fun initActionBar() {
        setSupportActionBar(binding.activityUsersToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        binding.activityUsersToolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu))


        val title = getString(R.string.users_activity_title).uppercase()
        val spannableTitle = SpannableString(title)

        val sizeSpan = RelativeSizeSpan(0.9f)
        spannableTitle.setSpan(sizeSpan, 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val styleSpan = StyleSpan(Typeface.BOLD)
        spannableTitle.setSpan(styleSpan, 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        supportActionBar?.title = spannableTitle
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor((R.color.white))))
    }

    private fun initUI() {

        binding.usersSearchView.queryHint = getString(R.string.users_searchbar_hint)

        val adapter = UsersAdapter(this)
        binding.activityUsersRecyclerView.adapter = adapter
        binding.activityUsersRecyclerView.layoutManager = LinearLayoutManager(this)
        subscribeUI(adapter)

        searchView = binding.usersSearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchUsers(newText.orEmpty())
                return true
            }
        })
    }

    private fun subscribeUI(adapter: UsersAdapter) {
        lifecycleScope.launch {
            viewModel.filteredUsers.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onClick(userModel: User) {

        val intent = Intent(this, UserInfoActivity::class.java)
        intent.putExtra("user", userModel as Serializable)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
}