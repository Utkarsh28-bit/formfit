package com.example.formfit.viewmodel

import androidx.lifecycle.ViewModel
import com.example.formfit.ProfileEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _profileState = MutableStateFlow<ProfileEntity?>(null)
    val profileState: StateFlow<ProfileEntity?> = _profileState.asStateFlow()

    fun fetchProfile() {
        val userId = auth.currentUser?.uid ?: return
        
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val profile = document.toObject(ProfileEntity::class.java)
                    _profileState.value = profile
                }
            }
    }

    fun saveProfile(profile: ProfileEntity, onSuccess: () -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        
        db.collection("users").document(userId)
            .set(profile)
            .addOnSuccessListener {
                _profileState.value = profile
                onSuccess()
            }
    }
}
