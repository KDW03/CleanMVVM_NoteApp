package com.example.noteapp.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_note.domain.model.InvalidNoteException
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * TextField State는 문자가 입력될때마다 재구성을 다시 하므로 상태를 따로 빼는게 효율적
 *
 * SavedStateHandle은 ViewModel의 상태를 저장하고 복원하는 데 사용되는 클래스
 * 키-값 맵으로, 저장된 상태에 객체를 작성하고 저장된 상태에서 객체를 검색
 * 이러한 값은 시스템에서 프로세스가 중단된 후에도 유지되며 동일한 객체를 통해 계속 사용
 * 이 값은 화면 전환 시 전달된 인수로부터 가져올 수 있다
 *
 * ViewModel은 화면의 수명 주기와 연결
 * 이는 화면이 생성될 때마다 새로운 ViewModel 인스턴스가 생성된다는 것을 의미
 * 새로운 ViewModel 인스턴스가 생성되므로, 화면의 상태가 초기화되고, 구성 변경이나 프로세스가 종료된 후에도 화면의 상태가 유지
 *
 * .toArgb()는 선택된 색상을 ARGB 형식의 정수 값으로 변환
 * ARGB는 색상을 표현하는 형식 중 하나로, 알파(Alpha), 레드(Red), 그린(Green), 블루(Blue)의 4개 채널로 구성
 * 각 채널은 8비트로 표현되며, 0부터 255까지 값을 가짐
 *
 * 알파(Alpha) 채널은 투명도를 나타냅니다. 값이 0이면 완전히 투명하고, 값이 255이면 완전히 불투명
 * 8개의 16진수 숫자로 표현된다 ( EX) 80FFFF00 )
 *
 *
 * SharedFlow는 여러 구독자가 구독할 수 있는 핫 스트림
 * 이 스트림에 이벤트가 발생하면, 모든 구독자가 이 이벤트를 수신
 * 따라서 SharedFlow를 사용하면 여러 구독자가 동일한 이벤트를 수신
 * 이벤트는 _eventFlow.emit() 메서드를 사용하여 발생
 *
 * SharedFlow vs StateFlow
 * 둘 다 핫 스트림으로, 구독자가 구독하기 전에도 데이터를 생성
 *
 * tateFlow는 반드시 초기값을 명시
 * StateFlow는 값을 중복으로 방출할경우, collect()해오지 않는다
 * 즉 stateFlow의 collect()가 내부적으로 값이 같을경우, Skip
 *
 * Main Thread에서 관찰이 진행하기 때문에 LiveData는 비동기 데이터 처리에 적합하게 설계되지 않았다
 * LiveData는 AAC 라이브러리로 해당하는 모듈을 추가해야하는 문제로 의존성을 가지지않는 독립적인 계층이 Domain에서 사용하기 적합하지 않다.
 *
 * 클린 아키텍처에서 이벤트와 상태는 서로 밀접한 관계
 *  이벤트는 사용자의 입력이나 시스템의 변화 등으로 발생하는 사건
 * 상태는 애플리케이션의 현재 상황을 나타냄
 *
 * 이벤트가 발생하면, 이에 따라 상태가 변경
 *
 * 프레젠테이션 계층에서는 사용자 인터페이스와 관련된 이벤트를 처리 (Domain의 Usecase를 이용하거나 , ViewModel에 있는 UI 상태를 변경 한다던가)
 * ViewModel의 State가 변경되었다면 해당 State를 이용하고 있는 ui가 자동으로 업데이트
 *
 *
 *
 */

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle = mutableStateOf(
        NoteTextFiledState(
            hint = "Enter title..."
        )
    )
    val noteTitle: State<NoteTextFiledState> = _noteTitle


    private val _noteContent = mutableStateOf(
        NoteTextFiledState(
            hint = "Enter some content"
        )
    )
    val noteContent: State<NoteTextFiledState> = _noteContent


    private val _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow


    private var currentNoteId: Int? = null

    init {
        // key 값으로 noteId키-값을 확인하여 새로운 노트를 추가하는 것인지, 기존 노트를 보는 것인지 체크
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also {note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }
                }
            }
        }

    }


    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }

            AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "UnKnow Error"
                            )
                        )
                    }
                }
            }
        }
    }


    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }

}

