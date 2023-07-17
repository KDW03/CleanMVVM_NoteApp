package com.example.noteapp.di

import android.app.Application
import androidx.room.Room
import com.example.noteapp.feature_note.data.data_source.NoteDatabase
import com.example.noteapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import com.example.noteapp.feature_note.domain.use_case.AddNote
import com.example.noteapp.feature_note.domain.use_case.DeleteNote
import com.example.noteapp.feature_note.domain.use_case.GetNote
import com.example.noteapp.feature_note.domain.use_case.GetNotes
import com.example.noteapp.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * 의존성 주입을 설정하는 클래스
 * 실제 앱과는 다르게 메모리 내 데이터베이스를 생성
 *
 * 각 테스트 케이스는 다른 테스트 케이스로부터 독립적이어야 한다, 그래서 테스트가 끝나면 데이터가 사라지게 메모리에 데이터베이스를 생성
 * 즉, 메모리 내 데이터베이스를 사용하면 각 테스트가 독립적이고 재현 가능한 상태를 유지하면서, 실제 데이터베이스와 동일한 인터페이스를 통해 코드를 테스트할 수 있습니다
 *
 *
 *
 *
 *
 *
 */

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {


    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            NoteDatabase::class.java
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
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }

}