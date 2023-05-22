package com.oosca.parcial1.ui.newspaper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.oosca.parcial1.NewspaperReviewerApplication
import com.oosca.parcial1.data.model.NewspaperModel
import com.oosca.parcial1.repositories.NewspaperRepository

class NewspaperViewModel(private val repository: NewspaperRepository) : ViewModel() {
    fun getNewspapers() = repository.getNewspapers()
    fun addNewspapers(newspaper: NewspaperModel) = repository.addNewspapers(newspaper)

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as NewspaperReviewerApplication
                NewspaperViewModel(app.newspaperRepository)
            }
        }
    }
}