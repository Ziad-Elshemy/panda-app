package com.easy_pro_code.panda.HomeFlow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.easy_pro_code.panda.HomeFlow.presentations.categories1.Categories1Fragment
import com.easy_pro_code.panda.HomeFlow.presentations.home.HomeFragment
import com.easy_pro_code.panda.HomeFlow.presentations.myAccount.MyAccountFragment
import com.easy_pro_code.panda.HomeFlow.presentations.wishlist.WishListFragment
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.ActivityHomeBinding
import com.easy_pro_code.panda.databinding.ActivityMainBinding
import com.easy_pro_code.panda.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarItemView
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() {

//    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = Navigation.findNavController(this,R.id.fragmentContainerView)
        binding.bottomNav.setupWithNavController(navController)

//        bottomNavigationView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
//            menuItem->
//            if (menuItem.itemId == R.id.navigation_homeFragment){
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragmentContainerView,HomeFragment())
//                    .commit()
//            }else if (menuItem.itemId == R.id.navigation_favoriteFragment){
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragmentContainerView,WishListFragment())
//                    .commit()
//            }else if (menuItem.itemId == R.id.navigation_categoriesFragment){
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragmentContainerView,Categories1Fragment())
//                    .commit()
//            }else if (menuItem.itemId == R.id.navigation_cartFragment){
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragmentContainerView,cart())
//                    .commit()
//            }else if (menuItem.itemId == R.id.navigation_myAccountFragment){
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragmentContainerView,MyAccountFragment())
//                    .commit()
//            }
//            return@OnItemSelectedListener true
//        })
    }
}