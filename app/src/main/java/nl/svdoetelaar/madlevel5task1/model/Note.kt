package nl.svdoetelaar.madlevel5task1.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Note")
data class Note(

    var title: String,
    var lastUpdate: Date,
    var text: String,

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null
)