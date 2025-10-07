package com.darsh.news.firebaseTempUi

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.darsh.news.MainActivity
import com.darsh.news.R
import com.darsh.news.firebaseTempUi.RegisterActivity
import com.darsh.news.firebaseTempUi.ResetPasswordActivity
import com.darsh.news.firebaseTempUi.VerifyActivity
import com.darsh.news.databinding.ActivityLoginBinding
import com.darsh.news.firebaseLogic.AuthViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter Email and Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                authViewModel.login(email, password)
            }
        }

        binding.register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.forgotPass.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }

        authViewModel.authResult.observe(this) { result ->
            result.onSuccess { user ->
                if (user?.isEmailVerified == true) {
                    Toast.makeText(this, "Welcome, ${user.email}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, VerifyActivity::class.java))
                    finish()
                }
            }
            result.onFailure { exception ->
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = authViewModel.getCurrentUser()
        if (currentUser != null && currentUser.isEmailVerified) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}