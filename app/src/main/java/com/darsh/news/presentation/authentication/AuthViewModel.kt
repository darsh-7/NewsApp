package com.darsh.news.firebaseLogic

import AuthRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _authResult = MutableLiveData<Result<FirebaseUser?>>()
    val authResult: LiveData<Result<FirebaseUser?>> get() = _authResult

    private val _verificationResult = MutableLiveData<Result<Unit>>()
    val verificationResult: LiveData<Result<Unit>> get() = _verificationResult

    private val _resetResult = MutableLiveData<Result<Unit>>()
    val resetResult: LiveData<Result<Unit>> get() = _resetResult

    fun signUp(email: String, password: String) {
        authRepository.signUp(email, password) { result ->
            _authResult.postValue(result)
        }
    }

    fun sendVerificationEmail(user: FirebaseUser?) {
        authRepository.sendVerificationEmail(user) { result ->
            _verificationResult.postValue(result)
        }
    }

    fun login(email: String, password: String) {
        authRepository.login(email, password) { result ->
            _authResult.postValue(result)
        }
    }

    fun resetPassword(email: String) {
        authRepository.resetPassword(email) { result ->
            _resetResult.postValue(result)
        }
    }

    fun refreshUser() = authRepository.refreshUser()

    fun logout() = authRepository.logout()

    fun getCurrentUser() = authRepository.getCurrentUser()
}