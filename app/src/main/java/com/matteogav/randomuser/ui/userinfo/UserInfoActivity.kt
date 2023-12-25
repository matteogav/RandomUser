package com.matteogav.randomuser.ui.userinfo

import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.load
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.matteogav.randomuser.R
import com.matteogav.randomuser.databinding.ActivityUserInfoBinding
import com.matteogav.randomuser.domain.models.Gender
import com.matteogav.randomuser.domain.models.User


class UserInfoActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityUserInfoBinding
    private var user: User? = null

    override fun onResume() {
        super.onResume()
        binding.userinfoMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.userinfoMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.userinfoMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.userinfoMapView.onLowMemory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        user = intent.getSerializableExtra("user") as? User

        binding.userinfoMapView.onCreate(savedInstanceState)

        initActionBar()
        initUI()
    }

    private fun initActionBar() {
        setSupportActionBar(binding.activityUsersToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val overflowIcon = ContextCompat.getDrawable(this, R.drawable.ic_menu)
        overflowIcon?.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
        binding.activityUsersToolbar.overflowIcon = overflowIcon

        user?.let {
            supportActionBar?.setDisplayShowTitleEnabled(true)

            val title = it.name?.uppercase()
            val spannableTitle = SpannableString(title)

            if (title != null) {
                spannableTitle.setSpan(RelativeSizeSpan(0.9f), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableTitle.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            supportActionBar?.title = spannableTitle
            binding.activityUsersToolbar.setTitleTextColor(getColor(R.color.white))
        }

    }

    private fun initUI() {
        user?.let {

            binding.userinfoProfileImageView.load(it.image)

            binding.userinfoCameraButton.setImageDrawable(getDrawable(R.drawable.ic_camera))
            binding.userinfoEditButton.setImageDrawable(getDrawable(R.drawable.ic_edit))

            binding.userinfoNameItem.userinfoTitle.text = getString(R.string.userinfo_name_title)
            binding.userinfoNameItem.userinfoText.text = it.name
            binding.userinfoNameItem.userinfoText.setTypeface(null, Typeface.BOLD)
            binding.userinfoNameItem.userinfoImageView.setImageDrawable(getDrawable(R.drawable.ic_user))

            binding.userinfoEmailItem.userinfoTitle.text = getString(R.string.userinfo_email_title)
            binding.userinfoEmailItem.userinfoText.text = it.email
            binding.userinfoEmailItem.userinfoText.setTypeface(null, Typeface.BOLD)
            binding.userinfoEmailItem.userinfoImageView.setImageDrawable(getDrawable(R.drawable.ic_email))

            binding.userinfoGenderItem.userinfoTitle.text = getString(R.string.userinfo_genre_title)
            binding.userinfoGenderItem.userinfoText.text =
                if(it.gender == Gender.Male) getString(R.string.userinfo_male)
                else { getString(R.string.userinfo_female)  }
            binding.userinfoGenderItem.userinfoText.setTypeface(null, Typeface.BOLD)
            binding.userinfoGenderItem.userinfoImageView.setImageDrawable(getDrawable(R.drawable.ic_gender_woman))

            binding.userinfoRegistrationDateItem.userinfoTitle.text = getString(R.string.userinfo_registerdate_title)
            binding.userinfoRegistrationDateItem.userinfoText.text = it.registrationDate
            binding.userinfoRegistrationDateItem.userinfoText.setTypeface(null, Typeface.BOLD)
            binding.userinfoRegistrationDateItem.userinfoImageView.setImageDrawable(getDrawable(R.drawable.ic_date))

            binding.userinfoPhoneItem.userinfoTitle.text = getString(R.string.userinfo_cell_title)
            binding.userinfoPhoneItem.userinfoText.text = it.cell
            binding.userinfoPhoneItem.userinfoText.setTypeface(null, Typeface.BOLD)
            binding.userinfoPhoneItem.userinfoImageView.setImageDrawable(getDrawable(R.drawable.ic_phone))

            binding.userinfoDirectionTitle.text = getString(R.string.userinfo_direction_title)
            binding.userinfoMapView.getMapAsync(this)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        user?.let { user ->
            if(user.latitude != null && user.longitude != null) {
                val point = LatLng(user.latitude.toDouble(), user.longitude.toDouble())
                googleMap.addMarker(
                    MarkerOptions()
                        .position(point)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 16f))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
