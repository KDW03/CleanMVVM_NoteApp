package com.example.noteapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.use_case.NoteUseCases
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @HiltViewModel 어노테이션은 Hilt가 ViewModel 클래스를 인식하고 의존성 주입을 할 수 있도록 해줌
 *
 *
 *  @Inject 어노테이션은 Hilt가 의존성을 주입할 생성자, 메소드 또는 필드를 지정하는 데 사용
 *  이 어노테이션을 붙인 생성자, 메소드 또는 필드의 매개변수는 Hilt가 자동으로 주입
 *
 *
 *  onEach와 launchIn은 코틀린의 코루틴 라이브러리에서 제공하는 Flow API의 메소드
 *
 *  onEach 메소드는 Flow의 각 요소에 대해 지정된 작업을 수행하는 메소드
 *  launchIn 메소드는 Flow를 지정된 CoroutineScope에서 실행하는 메소드
 *  이 메소드는 CoroutineScope를 매개변수로 받아서, 해당 CoroutineScope에서 Flow를 실행
 *
 *
 */

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel(){


    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ){
                    return
                }
                getNotes(event.noteOrder)
            }

            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }

            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }

            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }


    private fun getNotes(noteOrder: NoteOrder) {
        // 이전에 실행 중인 노트 가져오기 작업이 있다면 취소
        getNotesJob?.cancel()


        //onEach로 Flow객체의 상태가 변하면 실행할 작업을 정의한 후 Flow를 반환하는 역할
        getNotesJob = noteUseCases.getNotes(noteOrder)
            .onEach {
                notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope) // 이 작업이 viewModelScope에서 일어나게 정의
    }

}