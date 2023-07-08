package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.domain.model.InvalidNoteException
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {

        if (note.title.isBlank()) {
            throw InvalidNoteException("메모의 제목을 입력하세요")
        }

        if (note.content.isBlank()) {
            throw InvalidNoteException("메모의 내용을 입력하세요")
        }

        repository.insertNote(note)
    }
}