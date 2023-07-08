package com.example.noteapp.feature_note.di

import android.app.Application
import androidx.room.Room
import com.example.noteapp.feature_note.data.data_source.NoteDatabase
import com.example.noteapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import com.example.noteapp.feature_note.domain.use_case.AddNote
import com.example.noteapp.feature_note.domain.use_case.DeleteNote
import com.example.noteapp.feature_note.domain.use_case.GetNotes
import com.example.noteapp.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * @Module 어노테이션은 이 클래스가 Hilt 모듈임을 나타낸다.
 * 모듈에서 정의된 의존성들은 Hilt가 의존성 그래프를 구축하는 데 사용
 *
 *
 * @InstallIn 어노테이션은 이 모듈이 어떤 Hilt 컴포넌트에 설치될지 지정, 이 경우 SingletonComponent에 설치
 * Hilt 모듈은 의존성 주입을 위한 객체를 제공하는 클래스이다
 * SingletonComponent는 애플리케이션 수명주기와 연결된 컴포넌트로, 애플리케이션 전체에서 단일 인스턴스만 생성되는 종속성을 제공
 *
 *
 * 세 가지 종속성을 제공
 *
 * @Provides 어노테이션은 이 어노테이션은 이 함수가 의존성을 제공하는 함수임을 나타냄
 * @Singleton 어노테이션은 단일 인스턴스만 생성한다는 뜻
 *
 *
 * Application 객체는 Hilt가 자동으로 제공하는 기본 바인딩 중 하나
 * @HiltAndroidApp 어노테이션이 붙은 클래스의 인스턴스가 Application 객체로 사용
 * 따라서, 별도로 Application 객체를 제공하는 @Provides 함수를 작성하지 않아도, Hilt가 자동으로 Application 객체를 주입
 *
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository)
        )
    }

}