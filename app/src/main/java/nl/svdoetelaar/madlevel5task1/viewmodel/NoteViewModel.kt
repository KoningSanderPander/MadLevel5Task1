package nl.svdoetelaar.madlevel5task1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import nl.svdoetelaar.madlevel5task1.repository.NoteRepository

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository = NoteRepository(application.applicationContext)

    val note = noteRepository.getNotepad()
}