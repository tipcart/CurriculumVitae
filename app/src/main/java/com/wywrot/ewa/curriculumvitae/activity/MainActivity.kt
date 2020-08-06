package com.wywrot.ewa.curriculumvitae.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.navigation.NavigationView
import com.wywrot.ewa.curriculumvitae.R
import com.wywrot.ewa.curriculumvitae.adapter.getContext
import com.wywrot.ewa.curriculumvitae.fragment.AboutMeFragment
import com.wywrot.ewa.curriculumvitae.fragment.MyArticlesFragment
import com.wywrot.ewa.curriculumvitae.fragment.MyExperienceFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        changeFragment(AboutMeFragment.newInstance())
        bindNavDrawer()
    }

    private fun bindNavDrawer() {
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)

        with(navigationView.getHeaderView(0)) {
            Glide
                .with(this)
                .load(R.drawable.profile_photo)
                .placeholder(R.drawable.ic_baseline_account_circle_white_24)
                .apply(RequestOptions.circleCropTransform())
                .circleCrop()
                .into(drawerProfilePhoto)

            drawerProfileName.text = resources.getString(R.string.my_name)
        }
    }

    private fun changeFragment(fragment: Fragment) {
        val tag = fragment.javaClass.simpleName
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about_me -> changeFragment(AboutMeFragment.newInstance())
            R.id.experience -> changeFragment(MyExperienceFragment.newInstance())
            R.id.articles -> changeFragment(MyArticlesFragment.newInstance())
            R.id.linkedin -> {
                try {
                    val linkedInLink = "https://www.linkedin.com/in/ewa-wywrot-078638124/"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(linkedInLink)
                    ContextCompat.startActivity(this, i, null)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            when {
                supportFragmentManager.backStackEntryCount == 1 -> {
                    setResult(Activity.RESULT_OK, Intent())
                    finish()
                }

                supportFragmentManager.backStackEntryCount > 1 -> {
                    supportFragmentManager.getBackStackEntryAt(
                        supportFragmentManager.backStackEntryCount - 2
                    ).name
                    supportFragmentManager.popBackStack()
                }

                else -> {
                    setResult(Activity.RESULT_OK, Intent())
                    super.onBackPressed()
                }
            }
        }
    }

    fun setActionBarTitle(@StringRes title: Int) {
        toolbar.setTitle(title)
        setTitle(title)
    }
}