package com.example.noteapp.feature_note.domain.repository

import com.example.noteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow


/**
 * 이 인터페이스는 Note 데이터에 접근하는 메소드들을 정의
 * 클린 아키텍처 관점에서 보면, NoteRepository 인터페이스는 도메인 계층에 속함
 * 비즈니스 로직과 관련된 추상화된 인터페이스로, 데이터 소스와 독립적이므로
 * 이 인터페이스의 구현체는 data Layer에 위치하고 다양한 데이터 소스(예: 로컬 데이터베이스, 원격 서버)로부터 데이터를 가져올 수 있습니다.
 * 이 인터페이스를 이용하여 로컬 또는 원격 데이터 소스에서 가져오도록 구현체를 구현하면 된다.
 *
 * 이러한 구조는 각 계층이 독립적으로 변경될 수 있도록 해준다
 * 예를 들어, 데이터 소스가 변경되더라도, 도메인 계층의 코드는 변경될 필요가 없습니다. 대신, 변경된 구현체가 있는 데이터 계층의 코드만 변경하면 됩니다.
 *
 */

interface NoteRepository {

    fun getNotes() : Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

}