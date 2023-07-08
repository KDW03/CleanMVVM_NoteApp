package com.example.noteapp.feature_note.presentation.notes

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.util.NoteOrder


/**
 * 앱의 이벤트를 묶어 래핑한 클래스
 *
 *  이벤트를 래핑하는 방식은 이벤트 처리 코드의 가독성과 유지보수성을 높이기 위해 많이 사용
 *
 *
 *
 */

sealed class NotesEvent {

    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    object RestoreNote : NotesEvent()
    object ToggleOrderSection : NotesEvent()

}
