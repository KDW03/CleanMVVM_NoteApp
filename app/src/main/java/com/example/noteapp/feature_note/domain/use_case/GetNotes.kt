package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


/**
 * 데이터베이스의 note 데이터를 가져와 지정된 정렬 방법에 따라 정렬 후 반환
 * usecase는 비즈니스 로직을 구현하는 코드로 domain 계층에 위치,
 * 이렇게 usecase를 도메인에 위치시키면 데이터 소스가 변경되더라도 usecase는 repository interface에만 종속적이므로 벼녁ㅇ될 필요가 없다
 *
 *
 * operator 키워드는 코틀린에서 연산자 오버로딩을 위해 사용
 * 연산자 오버로딩이란 클래스의 인스턴스에 대한 표준 연산자의 동작을 재 정의 하는 것
 *
 * invoke() 함수에 operator 키워드 -> 이 함수는 해당 클래스의 인스턴스에 대해 () 연산자의 동작을 재정의
 *
 * val useCase = GetNotesUseCase(repository)
 * val notes = useCase() --> GetNotesUseCase 클래스의 인스턴스에 대해 () 연산자 = invoke() 함수가 호출
 *
 *
 */

class GetNotes(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> = repository.getNotes().map { notes ->

        val comparator = when (noteOrder) {
            is NoteOrder.Title -> compareBy<Note> { it.title.lowercase() }
            is NoteOrder.Date -> compareBy { it.timestamp }
            is NoteOrder.Color -> compareBy { it.color }
        }.let {
            if (noteOrder.orderType is OrderType.Ascending) it else it.reversed()
        }

        notes.sortedWith(comparator)

    }
}
