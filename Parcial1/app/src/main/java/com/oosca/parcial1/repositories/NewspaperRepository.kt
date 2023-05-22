package com.oosca.parcial1.repositories

import com.oosca.parcial1.data.model.NewspaperModel

class NewspaperRepository(private val Newspapers: MutableList<NewspaperModel>) {

    fun getNewspapers() = Newspapers
    fun addNewspapers(Newspaper: NewspaperModel) = Newspapers.add(Newspaper)
}