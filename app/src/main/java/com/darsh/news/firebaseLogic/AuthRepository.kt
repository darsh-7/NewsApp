package com.darsh.news.firebaseLogic

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    fun signUp(email: String, password: String, callback: (Result<FirebaseUser?>) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                callback(Result.success(user))
            } else callback(Result.failure(task.exception ?: Exception("Sign-up failed")))
        }
    }

    fun sendVerificationEmail(user: FirebaseUser?, callback: (Result<Unit>) -> Unit) {
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) callback(Result.success(Unit))
                else callback(Result.failure(task.exception ?: Exception("Failed to send verification email")))
            }
        } else callback(Result.failure(Exception("User not found")))
    }

    fun login(email: String, password: String, callback: (Result<FirebaseUser?>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) callback(Result.success(user))
                    else callback(Result.failure(Exception("User not found")))
                } else callback(Result.failure(task.exception ?: Exception("Login failed")))
            }
    }

    fun resetPassword(email: String, callback: (Result<Unit>) -> Unit) {
        if (email.isBlank()) {
            callback(Result.failure(Exception("Email cannot be empty")))
            return
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) callback(Result.success(Unit))
            else callback(Result.failure(task.exception ?: Exception("Failed to send password reset email")))
        }
    }

    fun refreshUser(): FirebaseUser? {
        auth.currentUser?.reload()
        return auth.currentUser
    }

    fun logout() = auth.signOut()

    fun getCurrentUser() = auth.currentUser
}