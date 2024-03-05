package com.part2.a3dify.app_entry.viewmodels

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.part2.a3dify.app_containers.ThrDifyApplication
import com.part2.a3dify.app_entry.image_recycler_view.ImageAdapter
import com.part2.a3dify.app_entry.image_recycler_view.ImageDataClass
import com.part2.a3dify.app_entry.uistates.MainFragmentUiStates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragmentViewModel(private val application: Application, private val savedStateHandle: SavedStateHandle): ViewModel() {
    private val _uiState = MutableStateFlow(MainFragmentUiStates())
    val uiState : StateFlow<MainFragmentUiStates> = _uiState.asStateFlow()

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return MainFragmentViewModel((application as ThrDifyApplication), savedStateHandle) as T
            }
        }
    }
    // checking for jobs in place
    private var fetchJob: Job? = null

    fun updateRecyclerImages(uriList: List<Uri>) {
        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            withContext(Dispatchers.Default) {
               _uiState.update {
                   updateImages(uriList)
               }
            }
        }
    }

    private fun updateImages(uriList: List<Uri>) : MainFragmentUiStates {
        val images = uriList.map{ImageDataClass(
            it,
            configureBitmap(it)
        )}
        return MainFragmentUiStates(images)
    }

    private fun configureBitmap(uri: Uri): Bitmap {
        var bitmap = MediaStore.Images.Media.getBitmap(application.applicationContext.contentResolver, uri)
        val matrix = Matrix()
        matrix.postRotate(90.0f)
        bitmap = Bitmap.createScaledBitmap(bitmap, 1000, 1000, true)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    override fun onCleared() {
        fetchJob?.cancel()
    }
}