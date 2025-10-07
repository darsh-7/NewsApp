package com.darsh.news.presentation.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.darsh.news.R
import com.darsh.news.databinding.FragmentSignInBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignInFragment : Fragment() {

    lateinit var binding: FragmentSignInBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                it.findNavController().navigate(R.id.action_signInFragment_to_favouritFragment)
            }
        }    }
private fun isInputValid(): Boolean {
    // Clear previous errors before running validation
    clearErrors()

    val email = binding.inputEmail.text.toString().trim()
    val password = binding.inputPassword.text.toString()
    var isValid = true

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
    }

    return isValid
}

// Helper function to clear all active errors on TextInputLayouts
private fun clearErrors() {
    binding.layoutEmail.error = null
    binding.layoutPassword.error = null
}}