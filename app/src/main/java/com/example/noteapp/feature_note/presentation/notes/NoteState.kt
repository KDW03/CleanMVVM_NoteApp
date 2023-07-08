package com.example.noteapp.feature_note.presentation.notes

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType


/**
 *
 * 앱의 상태를 나타내는 데이터 클래스
 * 상태를 데이터 클래스로 묶어놓으면, 상태 관리가 더욱 쉬워짐
 * 이 클래스의 인스턴스는 앱의 전체 상태를 나타내며, 상태 변경 시 새로운 인스턴스를 생성하여 상태를 갱신
 *
 * 이 상태를 ViewModel이 가지고 있게해서 state holder로써 사용 하게 만든다.
 *
 */

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)