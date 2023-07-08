package com.example.noteapp.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp.feature_note.domain.model.Note


/**
 *
 * Room 데이터베이스를 정의하는 추상 클래스
 * @Database 어노테이션은 이 클래스가 Room 데이터베이스임을 나타냄
 * entities 속성은 데이터베이스에 포함될 엔티티 클래스들을 지정
 * vesion 데이터베이스의 버전 지정
 *
 *
 * NoteDatabase는 Room 데이터베이스의 인스턴스를 생성하기 위해 필요한 메소드들을 정의
 * 그것을 noteDao라는 추상 프로퍼티를 통해 정의, 이 프로퍼티는 NoteDao 인터페이스의 인스턴스를 반환
 *
 */

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}