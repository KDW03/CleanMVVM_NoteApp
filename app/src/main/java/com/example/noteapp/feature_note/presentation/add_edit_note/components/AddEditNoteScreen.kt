package com.example.noteapp.feature_note.presentation.add_edit_note.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.example.noteapp.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * LaunchedEffect는 key1 = true로 설정되어 있다 이는 LaunchedEffect가 한 번만 실행되도록 합
 * LaunchedEffect 내부에서 viewModel.eventFlow.collectLatest를 사용하여 eventFlow의 최신 이벤트를 수집
 *
 * 전달된 코드 블록으로 코루틴을 시작
 * LaunchedEffect가 Composition에서 벗어나면 코루틴이 취소
 * LaunchedEffec가 다른 키로 재구성되면 기존 코루틴이 취소되고 새로운 suspend 함수가 새로운 코루틴에서 시작
 *
 * noteBackgroundAnimatable은 remember 함수를 사용하여 정의된 Animatable 객체
 * Animatable은 Jetpack Compose에서 애니메이션을 적용할 수 있는 객체
 *
 * LaunchedEffect를 사용하지 않고 코루틴을 직접 시작하면, Composable 함수가 재구성될 때마다 코루틴이 다시 시작될 수 있습니다
 * LaunchedEffect를 사용하면 Composable 함수가 재구성되더라도 코루틴이 한 번만 실행되도록
 *
 *
 * Arrangement.SpaceBetween : 첫 번째 자식 Composable과 마지막 자식 Composable이 시작 부분과 끝 부분에 배치되도록 한다
 * 나머지 자식 Composable은 균등한 간격으로 배치 (간격의 부모의 너비에 따라 결정)
 *
 * animateTo 함수는 Animatable의 값을 애니메이션하여 변경하는 함수
 * targetValue와 animationSpec 매개 변수를 사용
 *
 * targetValue: 애니메이션의 목표 값입니다. 이 경우 목표 값은 Color(colorInt)로 설정
 * animationSpec: 애니메이션의 사양을 결정하는 매개 변수
 *
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {

    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val snackbarHostState = remember { SnackbarHostState() }

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }

                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(AddEditNoteEvent.SaveNote) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColor.value == colorInt) Color.Black else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextFiled(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = { string -> viewModel.onEvent(AddEditNoteEvent.EnteredTitle(string)) },
                onFocusChange = { focusState ->
                    viewModel.onEvent(
                        AddEditNoteEvent.ChangeTitleFocus(
                            focusState
                        )
                    )
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextFiled(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = { string -> viewModel.onEvent(AddEditNoteEvent.EnteredContent(string)) },
                onFocusChange = { focusState ->
                    viewModel.onEvent(
                        AddEditNoteEvent.ChangeContentFocus(
                            focusState
                        )
                    )
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxHeight()
            )


        }

    }

}