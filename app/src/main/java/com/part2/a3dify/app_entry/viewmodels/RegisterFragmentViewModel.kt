package com.part2.a3dify.app_entry.viewmodels

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.part2.a3dify.app_containers.ThrDifyApplication
import com.part2.a3dify.app_entry.repository.RegisterRepository
import com.part2.a3dify.app_entry.uistates.RegisterFragmentUiStates
import com.part2.a3dify.app_entry.uistates.RegisterRequest
import com.part2.a3dify.common.CommonViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragmentViewModel(private val repository: RegisterRepository,
                                private val savedStateHandle: SavedStateHandle
    ):  CommonViewModel<RegisterFragmentUiStates>(RegisterFragmentUiStates())
{
    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                if ( (application as ThrDifyApplication).mainContainer.registerFragmentContainer != null) {
                     return RegisterFragmentViewModel(application.mainContainer.registerFragmentContainer!!.registerRepository, savedStateHandle) as T
                } else {
                    throw IllegalStateException("application.mainContainer.registerFragmentContainer is null")
                }
            }
        }
    }

    fun registerUser(registerRequest: RegisterRequest)  {
        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = repository.registerUser(registerRequest)
                // How to call registerUser. What to do with the return type of below method.
                _uiState.update {
                    if (response.isSuccessful) {
                        return@update RegisterFragmentUiStates(null, response.body())
                    } else {
                        return@update RegisterFragmentUiStates(null, null)
                    }
                }
            }
        }
    }
}