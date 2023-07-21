package com.fabfinance.noteapp.repository

import androidx.lifecycle.LiveData
import com.fabfinance.noteapp.model.Note
import com.fabfinance.noteapp.services.NotesDao

class NoteRepository(val notesDao: NotesDao) {

    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    suspend fun insert(note: Note) {
        notesDao.insert(note)
    }

    suspend fun delete(note: Note){
        notesDao.delete(note)
    }

    suspend fun update(note: Note){
        notesDao.update(note)
    }


}