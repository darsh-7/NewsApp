package com.darsh.news.presentation.authentication.emailverification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darsh.news.R
import com.darsh.news.databinding.FragmentEmailVerificationBinding
import com.darsh.news.databinding.FragmentSignUpBinding


class EmailVerificationFragment : Fragment() {

    private lateinit var binding: FragmentEmailVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    private fun initUi() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmailVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

}