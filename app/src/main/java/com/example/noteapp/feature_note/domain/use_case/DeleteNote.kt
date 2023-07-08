package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository

/**
 * Usecase는 일반적으로 하나의 기능을 수행하도록 설계
 * => 별도의 클래스에 정의 =>  각 사용 사례가 독립적으로 변경
 * 예를 들어,  GeNotesUseCase가 변경되더라도 , DeleteNoteUseCase 클래스는 변경될 필요가 없다
 *
 *
 */

class DeleteNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}