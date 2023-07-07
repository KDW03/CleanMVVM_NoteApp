package com.example.noteapp.feature_note.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.noteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Room 데이터베이스 라이브러리에서 사용되는 DAO(Data Access Object)로,  데이터베이스에 저장된 note 테이블의 데이터에 접근하는 메소드 정의
 *
 * getNotes = note 테이블의 데이터가가 변경될 때마다 자동으로 업데이트되는 스트림을 제공하기 위해 FLOW를 반환
 * 따라서 getNotes() 메소드를 호출하면 Flow<List<Note>> 타입의 이 객체를 구독하면, note 테이블의 데이터가 변경될 때마다 자동으로 업데이트
 * 따라서, getNotes() 메소드를 한 번만 호출하고 반환된 Flow 객체를 구독하면, insertNote() 메소드가 호출되어 데이터가 삽입되거나, deleteNote() 메소드가 호출되어 데이터가 삭제될 때마다 자동으로 note 테이블의 변화를 관찰
 * 그래서 해당 메서드만 Flow로 매핑
 *
 * Flow 객체를 구독하면, 데이터의 변경 사항을 지속적으로 관찰하고 반영하는 작업이 비동기적으로 수행
 * 이 작업은 코루틴 내에서 수행되므로, getNotes() 메소드는 코루틴 내에서 호출될 필요가 없다
 *
 */

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getNotes() : Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}