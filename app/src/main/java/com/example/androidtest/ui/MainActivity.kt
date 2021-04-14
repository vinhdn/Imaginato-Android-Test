package com.example.androidtest.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtest.data.local.UserDao
import com.example.androidtest.databinding.ActivityMainBinding
import com.example.androidtest.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogout.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                userDao.clear()
            }
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }
}
