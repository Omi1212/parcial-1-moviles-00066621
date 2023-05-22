package com.oosca.parcial1

import android.app.Application
import com.oosca.parcial1.data.newspapers
import com.oosca.parcial1.repositories.NewspaperRepository

class NewspaperReviewerApplication : Application() {
    val newspaperRepository: NewspaperRepository by lazy {
        NewspaperRepository(newspapers)
    }
}