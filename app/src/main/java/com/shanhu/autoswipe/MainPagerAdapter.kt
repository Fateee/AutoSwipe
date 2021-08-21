package com.shanhu.autoswipe

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainPagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm){

    var listFragments : ArrayList<Fragment> = arrayListOf()

    fun setFragments(listFragments : ArrayList<Fragment>?) {
        listFragments?.apply {
            this@MainPagerAdapter.listFragments.clear()
            this@MainPagerAdapter.listFragments.addAll(listFragments)
            notifyDataSetChanged()
        }
    }

    override fun getItem(position: Int) = listFragments[position]

    override fun getCount() = listFragments.size

}
