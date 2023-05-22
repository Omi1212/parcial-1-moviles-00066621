package com.oosca.parcial1.ui.newspaper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.oosca.parcial1.NewspaperReviewerApplication
import com.oosca.parcial1.data.model.NewspaperModel
import com.oosca.parcial1.repositories.NewspaperRepository

class NewspaperViewModel(private val repository: NewspaperRepository) : ViewModel(){
    var name = MutableLiveData("")
    var publicationDate = MutableLiveData("")
    var status = MutableLiveData("")


    fun getNewspapers() = repository.getNewspapers()

    fun addNewspapers(newspaper: NewspaperModel) = repository.addNewspapers(newspaper)

    private fun validateData(): Boolean{
        when{
            name.value.isNullOrEmpty() -> return false
            publicationDate.value.isNullOrEmpty() -> return false

        }
        return true
    }

    fun createNewspaper(){
        if (!validateData()){
            status.value = WRONG_INFORMATION
            return
        }

        val newNewspapers = NewspaperModel(
            name.value!!,
            publicationDate.value!!,

        )

        addNewspapers(newNewspapers)
        clearData()
        status.value = NEWSPAPER_CREATED
    }

    fun clearStatus(){
        status.value = INACTIVE
    }

    fun clearData(){
        name.value = ""
        publicationDate.value = ""

    }

    companion object{
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as NewspaperReviewerApplication
                NewspaperViewModel(app.newspaperRepository)
            }
        }

        const val NEWSPAPER_CREATED = "Newspaper created"
        const val WRONG_INFORMATION = "Wrong information"
        const val INACTIVE = ""
    }


}