package com.example.notesapp.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note_data ORDER BY note_date")
    fun getAll(): Flow<List<NoteData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToRoomDatabase(noteData: NoteData)

    @Update
    suspend fun updateNoteData(noteData: NoteData)

    @Query("DELETE FROM note_data WHERE id = :id")
    suspend fun deleteNoteData(id: Int)

    @Query("DELETE FROM note_data")
    suspend fun deleteAll()
}