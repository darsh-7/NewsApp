package com.darsh.news.presentation.authentication.verify

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.darsh.news.R
import com.darsh.news.databinding.FragmentVerifyBinding
import com.darsh.news.firebaseLogic.AuthViewModel

class VerifyFragment : Fragment() {

    private lateinit var binding: FragmentVerifyBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val handler = Handler(Looper.getMainLooper())

    /**
     * A Runnable that periodically checks if the user's email has been verified.
     * It reloads the user's profile from Firebase and checks the `isEmailVerified` status.
     */
    private val checkVerification = object : Runnable {
        override fun run() {
            // Reload the user to get the latest verification status
            val user = authViewModel.refreshUser()
            if (user != null && user.isEmailVerified) {
                // If verified, enable the button, show a success message, and stop polling
                binding.startButton.isEnabled = true
                Toast.makeText(requireContext(), "Email Verified Successfully", Toast.LENGTH_SHORT).show()
                handler.removeCallbacks(this) // Stop the handler
            } else {
                // If not verified, schedule the check again after 2 seconds
                handler.postDelayed(this, 2000)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // The "Start" button is disabled until the email is verified
        binding.startButton.isEnabled = false
        // Start the periodic check for email verification
        handler.postDelayed(checkVerification, 2000)

        // When the button is enabled and clicked, navigate to the home screen
        binding.startButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_verifyFragment_to_signInFragment,
                null,
                androidx.navigation.NavOptions.Builder()
                    .setPopUpTo(R.id.verifyFragment, true)
                    .build()
            )
        }

//        // Navigate to the reset password screen if the user clicks "Forgot Password?"
//        binding.forgotPass.setOnClickListener {
//            findNavController().navigate(R.id.action_verifyFragment_to_resetPasswordFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Stop the handler when the fragment's view is destroyed to prevent memory leaks
        handler.removeCallbacks(checkVerification)
    }
}
