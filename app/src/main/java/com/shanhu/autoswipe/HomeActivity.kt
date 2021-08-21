package com.shanhu.autoswipe

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.shanhu.autoswipe.util.SpUtil
import com.shanhu.autoswipe.view.SetDialog
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private val listFragment : ArrayList<Fragment> = arrayListOf()
    var homeFragment : HomeFragment? = null
    var configFragment : ConfigFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        tabLayout?.newTab()?.setText("首页")?.let { tabLayout?.addTab(it) }
        tabLayout?.newTab()?.setText("配置")?.let { tabLayout?.addTab(it) }

        listFragment.apply {
            homeFragment = HomeFragment.newInstance(1)
            configFragment = ConfigFragment.newInstance(1)
            add(homeFragment!!)
            add(configFragment!!)
        }

        val pagerAdapter = MainPagerAdapter(supportFragmentManager)
        pagerAdapter.setFragments(listFragment)
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = 1
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> {
                    }
                    1 -> {
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        tabLayout?.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let {
                    viewPager.currentItem = it
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.openAccess -> openAccessSetting()
//            R.id.time -> {
//                timeDialog?.refreshView()
//                timeDialog?.show()
//            }
//            R.id.keywords -> {
//                keywordDialog?.refreshView()
//                keywordDialog?.show()
//            }
//            R.id.followRate -> {
//                followDialog?.refreshView()
//                followDialog?.show()
//            }
//            R.id.niceRate -> {
//                niceDialog?.refreshView()
//                niceDialog?.show()
//            }
//            R.id.commentRate -> {
//                commentDialog?.refreshView()
//                commentDialog?.show()
//            }
        }
    }

    /**
     * 打开辅助功能设置
     */
    fun openAccessSetting() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
//        val follow = SpUtil.getInstace().getString(MainApplication.KEY_FOLLOW, "8")
//        val nice = SpUtil.getInstace().getString(MainApplication.KEY_NICE, "4")
//        val comment = SpUtil.getInstace().getString(MainApplication.KEY_COMMENT, "7")
//        val followText = findViewById<AppCompatButton>(R.id.followRate)
//        followText.text = "关注比率($follow/10)"
//        val nicetext = findViewById<AppCompatButton>(R.id.niceRate)
//        nicetext.text = "点赞比率($nice/10)"
//        val commentText = findViewById<AppCompatButton>(R.id.commentRate)
//        commentText.text = "评论比率($comment/10)"
    }
}