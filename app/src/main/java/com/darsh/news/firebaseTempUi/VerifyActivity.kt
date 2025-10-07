package com.darsh.news.firebaseTempUi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.darsh.news.MainActivity
import com.darsh.news.R
import com.darsh.news.databinding.ActivityVerifyBinding
import com.darsh.news.firebaseLogic.AuthViewModel

class VerifyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val handler = Handler(Looper.getMainLooper())

    private val checkVerification = object : Runnable {
        override fun run() {
            val user = authViewModel.refreshUser()
            if (user != null && user.isEmailVerified) {
                binding.startButton.isEnabled = true
                Toast.makeText(this@VerifyActivity, "Email Verified Successfully", Toast.LENGTH_SHORT).show()
                handler.removeCallbacks(this)
            } else handler.postDelayed(this, 2000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.startButton.isEnabled = false
        handler.postDelayed(checkVerification, 2000)

        binding.startButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(checkVerification)
    }
}