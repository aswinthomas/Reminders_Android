package com.aswindev.remindersapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aswindev.remindersapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import general.GeneralFragment
import passwords.PasswordsFragment

class MainActivity : AppCompatActivity() {
    companion object {
        const val NUMBER_OF_TABS = 2
    }

    private lateinit var binding: ActivityMainBinding
    val tabNames = arrayOf("Passwords", "General")
    val tabIcons = arrayOf(R.drawable.baseline_lock_open_24, R.drawable.baseline_info_outline_24)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = PagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabNames[position]
            tab.icon = ContextCompat.getDrawable(this, tabIcons[position])
        }.attach()

    }

    class PagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount() = NUMBER_OF_TABS

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> PasswordsFragment()
                else -> GeneralFragment()
            }

        }
    }
}