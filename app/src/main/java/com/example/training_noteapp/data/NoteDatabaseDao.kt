package com.example.training_noteapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.training_noteapp.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDatabaseDao {

    @Query("select * from notes_tbl")
    fun getNotes(): Flow<List<Note>>

    @Query("select * from notes_tbl where id = :id")
    suspend fun getNoteById(id: String): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: Note)

    @Query("delete from notes_tbl")
    suspend fun deleteAll()

    @Delete
     suspend  fun deleteNote(note: Note)

}
