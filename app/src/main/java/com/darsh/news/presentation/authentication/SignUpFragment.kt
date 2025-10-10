package com.darsh.news.presentation.authentication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.darsh.news.R
import com.darsh.news.databinding.FragmentSignUpBinding
import com.darsh.news.firebaseLogic.AuthViewModel

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Link the Sign Up button to the validation function and Firebase logic
        binding.buttonSignUp.setOnClickListener {
            if (isInputValid()) {
                val email = binding.inputEmail.text.toString().trim()
                val password = binding.inputPassword.text.toString()
                authViewModel.signUp(email, password)
            }
        }

        // Link the Sign In link to navigation
        binding.textSignInFull.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment2_to_signInFragment)
        }

        // Observer for the sign-up result
        authViewModel.authResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { user ->
                // If sign-up is successful, send a verification email
                if (user != null) {
                    authViewModel.sendVerificationEmail(user)
                }
            }
            result.onFailure { exception ->
                // Show a toast if sign-up fails
                Toast.makeText(requireContext(), "Signup failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }

        // Observer for the verification email sending result
        authViewModel.verificationResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                // On success, notify user and navigate to the sign-in screen
                Toast.makeText(requireContext(), "Check your Inbox/Spam for verification email", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_signUpFragment2_to_verifyFragment)
            }
            result.onFailure { exception ->
                // On failure, log the error and notify the user
                Toast.makeText(requireContext(), "Failed to send verification email: ${exception.message}", Toast.LENGTH_SHORT).show()
                Log.e("SignUpFragment", "Failed to send verification email", exception)
            }
        }
    }

    /**
     * Checks all input fields for validity and displays specific error messages.
     * @return true if all fields pass validation, false otherwise.
     */
    private fun isInputValid(): Boolean {
        // Clear previous errors before running validation
        clearErrors()

        val email = binding.inputEmail.text.toString().trim()
        val username = binding.inputName.text.toString().trim()
        val password = binding.inputPassword.text.toString()
        val confirmPassword = binding.inputConfirmPassword.text.toString()

        var isValid = true

        // 1. Username Validation
        if (username.isBlank()) {
            binding.layoutName.error = "Name is required"
            isValid = false
        }

        // 2. Email Validation
        if (email.isBlank()) {
            binding.layoutEmail.error = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.layoutEmail.error = "Invalid email format"
            isValid = false
        }

        // 3. Password Validation (Length)
        if (password.isBlank()) {
            binding.layoutPassword.error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            binding.layoutPassword.error = "Must be at least 6 characters"
            isValid = false
        }

        // 4. Confirm Password Validation (Required & Match)
        if (confirmPassword.isBlank()) {
            binding.layoutConfirmPassword.error = "Confirmation is required"
            isValid = false
        } else if (password != confirmPassword) {
            // Error messages for both fields to highlight the mismatch
            binding.layoutPassword.error = "Passwords do not match"
            binding.layoutConfirmPassword.error = "Passwords do not match"
            isValid = false
        }

        return isValid
    }

    // Helper function to clear all active errors on TextInputLayouts
    private fun clearErrors() {
        binding.layoutName.error = null
        binding.layoutEmail.error = null
        binding.layoutPassword.error = null
        binding.layoutConfirmPassword.error = null
    }
}
