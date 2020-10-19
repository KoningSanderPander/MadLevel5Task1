package nl.svdoetelaar.madlevel5task1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.svdoetelaar.madlevel5task1.dao.NoteDao
import nl.svdoetelaar.madlevel5task1.model.Note
import java.util.*

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class NotepadRoomDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private const val DATABASE_NAME = "NOTEPAD_DATABASE"

        @Volatile
        private var INSTANCE: NotepadRoomDatabase? = null

        fun getDatabase(context: Context): NotepadRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(NotepadRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            NotepadRoomDatabase::class.java,
                            DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .addCallback(
                                object : RoomDatabase.Callback() {
                                    override fun onCreate(db: SupportSQLiteDatabase) {
                                        super.onCreate(db)
                                        INSTANCE?.let { notepadRoomDatabase ->
                                            CoroutineScope(Dispatchers.IO).launch {
                                                notepadRoomDatabase.noteDao()
                                                    .insertNote(Note("", Date(), ""))
                                            }
                                        }
                                    }
                                })
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}