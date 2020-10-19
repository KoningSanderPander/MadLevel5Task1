package nl.svdoetelaar.madlevel5task1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.svdoetelaar.madlevel5task1.R
import nl.svdoetelaar.madlevel5task1.model.Note
import nl.svdoetelaar.madlevel5task1.repository.NoteRepository
import java.util.*

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private val noteRepository = NoteRepository(application.applicationContext)

    val note = noteRepository.getNotepad()
    val error = MutableLiveData<String>()
    val succes = MutableLiveData<Boolean>()

    fun updateNote (title: String, text: String) {
        val newNote = Note(
            id = note.value?.id,
            title = title,
            lastUpdate = Date(),
            text = text
        )

        if (isNoteValid(newNote)) {
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    noteRepository.updateNotepad(newNote)
                }
                succes.value = true
            }
        }
    }

    private fun isNoteValid (note: Note): Boolean {
        return when {
            note.title.isBlank() -> {
                error.value = R.string.error_title_empty.toString()
                false
            }
            else -> true
        }
    }
}