package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.data.repository.FakeNoteRepository
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


/**
 *  @Before 어노테이션이 붙은 메서드는 각 테스트 메서드가 실행되기 전에 항상 먼저 실행된다.
 *  FakeNoteRepository는 단위 테스트에서 사용되는 가짜 또는 모의(mock)레포지토리
 *
 *  테스트에서 실제 레포지토리를 사용하면 여러 문제가 발생 된다.
 *  실제 레포지토리가 데이터베이스, 네트워크, 파일 시스템 등 외부 시스템과 상호작용하면 테스트의 복잡성 증가
 *  -> 테스트 실행 시간이 길어지며, 테스트의 격리가 어려워짐
 *
 *  또한, 외부 시스템의 상태에 따라 테스트 결과가 달라질 수 있다.
 *
 *  이러한 문제를 해결하기 위해 테스트 더블(test double)이라는 개념
 *  테스트 더블은  테스트 대상의 의존성을 대체하여 테스트를 단순화하고 격리
 *  가짜 레포지토리는 테스트 더블의 한 종류로, 실제 레포지토리의 동작을 단순한 방식으로 모방
 *
 * 그래서 레포지토리 interface를 이용해 fake객체를 만들어서
 * 외부 시스템과의 상호작용 없이 빠르고 일관된 방식으로 테스트
 *
 *
 *
 *
 */


class GetNotesTest {

    private lateinit var getNotes: GetNotes
    private lateinit var fakeRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        getNotes = GetNotes(fakeRepository)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timestamp = index.toLong(),
                    color = index
                )
            )
        }

        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach {
                fakeRepository.insertNote(it)
            }
        }
    }

    @Test
    fun `Order Ascending Title Sort`() = runBlocking {
        val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()


        // 내림 차순 정렬되었는지
        for (i in 0 until notes.size - 1) {
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }

    @Test
    fun `Order Descending Title Sort`() = runBlocking {
        val notes = getNotes(NoteOrder.Title(OrderType.Descending)).first()

        for (i in 0 until notes.size - 1) {
            assertThat(notes[i].title).isGreaterThan(notes[i + 1].title)
        }
    }


    @Test
    fun `Order Ascending date Sort`() = runBlocking {
        val notes = getNotes(NoteOrder.Date(OrderType.Ascending)).first()

        for (i in 0 until notes.size - 1) {
            assertThat(notes[i].timestamp).isLessThan(notes[i+1].timestamp)
        }
    }

    @Test
    fun `Order Descending date Sort`() = runBlocking {
        val notes = getNotes(NoteOrder.Date(OrderType.Descending)).first()

        for (i in 0 until notes.size - 1) {
            assertThat(notes[i].timestamp).isGreaterThan(notes[i+1].timestamp)
        }
    }

    @Test
    fun `Order Ascending color Sort`() = runBlocking {
        val notes = getNotes(NoteOrder.Color(OrderType.Ascending)).first()

        for (i in 0 until notes.size - 1) {
            assertThat(notes[i].color).isLessThan(notes[i+1].color)
        }
    }

    @Test
    fun `Order Descending color Sort`() = runBlocking {
        val notes = getNotes(NoteOrder.Color(OrderType.Descending)).first()

        for (i in 0 until notes.size - 1) {
            assertThat(notes[i].color).isGreaterThan(notes[i+1].color)
        }
    }


}


