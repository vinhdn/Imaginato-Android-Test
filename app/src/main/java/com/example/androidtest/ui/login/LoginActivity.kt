package com.example.androidtest.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.example.androidtest.databinding.ActivityLoginBinding
import com.example.androidtest.ui.MainActivity
import com.example.androidtest.utils.Resource
import com.example.androidtest.utils.gone
import com.example.androidtest.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private var _binding: ActivityLoginBinding? = null
    private val binding: ActivityLoginBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnLogin.setOnClickListener {
                viewModel.userName = editUsername.text?.toString() ?: ""
                viewModel.password = editPassword.text?.toString() ?: ""
                viewModel.login().observe(this@LoginActivity, Observer {
                    when(it.status) {
                        Resource.Status.ERROR -> {
                            Toast.makeText(this@LoginActivity, it.message, Toast.LENGTH_SHORT).show()
                            frameLoading.gone()
                        }
                        Resource.Status.LOADING -> {
                            frameLoading.visible()
                        }
                        Resource.Status.SUCCESS -> {
                            frameLoading.gone()
                        }
                    }
                })
            }
            editPassword.addTextChangedListener { viewModel.passwordError.value = "" }
            editUsername.addTextChangedListener { viewModel.userNameError.value = "" }
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            currentUser.observe(this@LoginActivity, Observer{
                if(it != null) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            })
            userNameError.observe(this@LoginActivity, Observer {
                binding.tvUsernameError.apply {
                    if(it.isNotEmpty()) {
                        visible()
                        text = it
                    } else {
                        gone()
                    }
                }
            })

            passwordError.observe(this@LoginActivity, Observer {
                binding.tvPasswordError.apply {
                    if(it.isNotEmpty()) {
                        visible()
                        text = it
                    } else {
                        gone()
                    }
                }
            })
        }

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}