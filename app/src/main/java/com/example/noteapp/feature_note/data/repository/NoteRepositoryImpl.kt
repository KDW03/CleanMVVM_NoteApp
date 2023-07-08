package com.example.noteapp.feature_note.data.repository

import com.example.noteapp.feature_note.data.data_source.NoteDao
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

/**
 * NoteRepository 인터페이스를 구현,즉 NoteRepository 인터페이스의 메소드들을 구현
 * NoteDao 인스턴스를 생성자의 인자로 받는다.
 * dao 프로퍼티의 메소드를 호출하여, 데이터베이스에 저장된 note 테이블의 데이터에 접근
 *
 */

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> = dao.getNotes()

    override suspend fun getNoteById(id: Int): Note? = dao.getNoteById(id)

    override suspend fun insertNote(note: Note) = dao.insertNote(note = note)

    override suspend fun deleteNote(note: Note) = dao.deleteNote(note = note)
}