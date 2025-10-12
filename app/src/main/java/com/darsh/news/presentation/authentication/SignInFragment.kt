package com.darsh.news.presentation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.darsh.news.R
import com.darsh.news.databinding.FragmentSignInBinding
import com.darsh.news.firebaseLogic.AuthViewModel

class SignInFragment : Fragment() {

    lateinit var binding: FragmentSignInBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textRegisterFull.setOnClickListener {
            it.findNavController().navigate(R.id.action_signInFragment_to_signUpFragment2)
        }

        binding.buttonSignIn.setOnClickListener {
            if (isInputValid()) {
                val email = binding.inputEmail.text.toString().trim()
                val password = binding.inputPassword.text.toString()
                authViewModel.login(email, password)
            }
        }

        binding.textForgotPassword.setOnClickListener {
            it.findNavController().navigate(R.id.action_signInFragment_to_resetPasswordFragment)
        }

        authViewModel.authResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { user ->
                if (user?.isEmailVerified == true) {
                    Toast.makeText(requireContext(), getString(R.string.welcome, user.email), Toast.LENGTH_SHORT).show()
                    // Navigate to home and clear back stack
                    findNavController().navigate(R.id.action_signInFragment_to_homeFragment, null, androidx.navigation.NavOptions.Builder()
                        .setPopUpTo(R.id.signInFragment, true)
                        .build())
                } else {
                    Toast.makeText(requireContext(), getString(R.string.verify_email), Toast.LENGTH_SHORT).show()
                    // Assuming you have a fragment for email verification
                    // If not, you can remove this navigation or create the fragment and action
                    // findNavController().navigate(R.id.action_signInFragment_to_verifyEmailFragment)
                }
            }
            result.onFailure {
                Toast.makeText(requireContext(),  getString(R.string.login_failed, it.message), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = authViewModel.getCurrentUser()
        if (currentUser != null && currentUser.isEmailVerified) {
            // User is already signed in and verified, navigate to home
            findNavController().navigate(R.id.action_signInFragment_to_homeFragment, null, androidx.navigation.NavOptions.Builder()
                .setPopUpTo(R.id.signInFragment, true)
                .build())
        }
    }

    private fun isInputValid(): Boolean {
        // Clear previous errors before running validation
        clearErrors()

        val email = binding.inputEmail.text.toString().trim()
        val password = binding.inputPassword.text.toString()
        var isValid = true

        // 2. Email Validation
        if (email.isBlank()) {
            binding.layoutEmail.error = getString(R.string.email_required)
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.layoutEmail.error = getString(R.string.invalid_email)
            isValid = false
        }

        // 3. Password Validation (Length)
        if (password.isBlank()) {
            binding.layoutPassword.error = getString(R.string.password_required)
            isValid = false
        }

        return isValid
    }

    // Helper function to clear all active errors on TextInputLayouts
    private fun clearErrors() {
        binding.layoutEmail.error = null
        binding.layoutPassword.error = null
    }
}
