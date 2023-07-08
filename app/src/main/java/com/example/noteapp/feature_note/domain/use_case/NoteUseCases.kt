package com.example.noteapp.feature_note.domain.use_case


/**
 *
 * viewModel에 Usecase를 제공할 때 한번에 제공하기 위해서 래핑 시킨 클래스
 *
 * class NoteViewModel(
 * private val noteUseCases: NoteUseCases
 * ) : ViewModel() {}
 *
 *
 * val notes = noteUseCases.getNotes()
 * noteUseCases.deleteNote.invoke(note)
 *
 *
 */

data class NoteUseCases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote
)