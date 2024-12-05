package com.capstone.finsight.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.finsight.ui.FeedFragment

class FeedAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FeedFragment()
        fragment.arguments = Bundle().apply {
            putInt(FeedFragment.Type, position)
        }
        return fragment
    }


}