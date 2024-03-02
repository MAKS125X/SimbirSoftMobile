package com.example.simbirsoftmobile

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simbirsoftmobile.databinding.ActivityMainBinding
import com.example.simbirsoftmobile.models.FriendUI

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter: FriendAdapter by lazy { FriendAdapter(testList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SimbirSoftMobile)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM

        initAdapter()
        initBottomNavigation()
    }

    private fun initAdapter() {
        binding.layoutBased.friendRecycler.addItemDecoration(FriendAdapter.CustomItemDecoration())
        binding.layoutBased.friendRecycler.adapter = adapter
        binding.layoutBased.friendRecycler.layoutManager = LinearLayoutManager(this)
    }

    private fun initBottomNavigation() {
        binding.bottomNavigationView.selectedItemId = R.id.profile
        binding.bottomNavigationView.setOnItemSelectedListener {
            val toastMassage = it.title
            Toast.makeText(this, toastMassage, Toast.LENGTH_SHORT).show()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    companion object {
        private val testList =
            arrayOf(
                FriendUI("Дмитрий Валерьевич", R.drawable.avatar_3),
                FriendUI("Дмитрий Валерьевич", R.drawable.avatar_3),
                FriendUI("Дмитрий Валерьевич", R.drawable.avatar_3),
                FriendUI("Дмитрий Валерьевич", R.drawable.avatar_3),
                FriendUI("Евгений Александров", R.drawable.avatar_2),
                FriendUI("Евгений Александров", R.drawable.avatar_2),
                FriendUI("Евгений Александров", R.drawable.avatar_2),
                FriendUI("Евгений Александров", R.drawable.avatar_2),
                FriendUI("Виктор Кузнецов", R.drawable.avatar_1),
                FriendUI("Виктор Кузнецов", R.drawable.avatar_1),
                FriendUI("Виктор Кузнецов", R.drawable.avatar_1),
                FriendUI("Виктор Кузнецов", R.drawable.avatar_1),
            )
    }
}
