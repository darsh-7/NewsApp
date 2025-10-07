package com.darsh.news.firebaseTempUi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.darsh.news.R
import com.darsh.news.databinding.ActivityResetPasswordBinding
import com.darsh.news.firebaseLogic.AuthViewModel

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.resetButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            authViewModel.resetPassword(email)
        }

        authViewModel.resetResult.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                finish()
            }
            result.onFailure { exception ->
                Toast.makeText(this, "Failed to send password reset email: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}