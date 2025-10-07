package com.darsh.news.firebaseTempUi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.darsh.news.R
import com.darsh.news.firebaseTempUi.VerifyActivity
import com.darsh.news.databinding.ActivityRegisterBinding
import com.darsh.news.firebaseLogic.AuthViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.signupButton.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            authViewModel.signUp(email, password)
        }

        authViewModel.authResult.observe(this) { result ->
            result.onSuccess { user ->
                if (user != null) authViewModel.sendVerificationEmail(user)
            }
            result.onFailure { exception ->
                Toast.makeText(this, "Signup failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }

        authViewModel.verificationResult.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "Check your Inbox/Spam", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, VerifyActivity::class.java))
                finish()
            }
            result.onFailure { exception ->
                Toast.makeText(this, "Failed to send verification email: ${exception.message}", Toast.LENGTH_SHORT).show()
                Log.e("RegisterActivity", "Failed to send verification email", exception)
            }
        }
    }
}