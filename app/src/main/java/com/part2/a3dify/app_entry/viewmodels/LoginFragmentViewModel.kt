package com.part2.a3dify.app_entry.viewmodels

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.part2.a3dify.app_containers.ThrDifyApplication
import com.part2.a3dify.app_entry.common.CommonViewModel
import com.part2.a3dify.app_entry.uistates.LoginFragmentUiStates

class LoginFragmentViewModel(private val application: Application, private val savedStateHandle: SavedStateHandle):
    CommonViewModel<LoginFragmentUiStates>(LoginFragmentUiStates()) {
    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return LoginFragmentViewModel((application as ThrDifyApplication), savedStateHandle) as T
            }
        }
    }

    fun login() {

    }

    fun register() {

    }
}