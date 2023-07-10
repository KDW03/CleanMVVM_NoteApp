package com.example.noteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.ui.theme.NoteAppTheme


/**
 *
 * Canvas 컴포저블을 사용하여 노트의 모양을 그림
 *
 * matchParentSize는 부모 컴포저블의 패딩을 무시하고, 자식 컴포저블의 크기를 부모 컴퍼저블의 크기와 일치하도록 설정
 * Modifier.fillMaxSize는 부모 컴포저블의 패딩을 고려하여, 자식 컴포저블의 크기를 부모 컴포저블의 남은 공간에 맞춰 설정합
 *
 *
 * lineTo를 4번 호출하여 노트의 모양을 그림
 * 1. 오른쪽 위 모서리
 * 2. 오른쪽 위 모서러 -> 오른쪽 아래 모서리
 * 3. 오른쪽 아래 모서리 -> 왼쪽 아래 모서리
 * 4. 왼쪽 아래 모서리 -> 오른쪽 위 모서리
 *
 *
 *  Path 객체를 사용하여 노트 클립 모양을 정의
 *  Path 객체는 Android에서 벡터 그래픽을 그리기 위한 클래스
 *
 *  Path 객체는 lineTo, moveTo, quadTo, cubicTo 등 다양한 함수를 제공
 *
 *  drawRoundRect : 함수를 두 번 호출하여 노트의 배경색과 모서리를 그림
 *
 *
 * maxLines: 텍스트가 표시될 최대 줄 수
 * overflow : 텍스트가 최대 줄 수를 초과할 경우 처리 방법
 *
 * overflow 종류
 * - Clip : 텍스트가 최대 줄 수를 초과하는 경우, 초과하는 부분을 잘라냄
 * - Ellipsis: 텍스트가 최대 줄 수를 초과하는 경우, 초과하는 부분을 말줄임표(…)로 표시
 * - Visible: 텍스트가 최대 줄 수를 초과하는 경우, 초과하는 부분을 그대로 표시
 *
 *
 * Modifier.align 함수를 사용하여 컴포저블의 위치를 설정가능
 *
 *
 */

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerRadius: Dp = 30.dp,
    onDeleteClick: () -> Unit
) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerRadius.toPx(), 0f)
                lineTo(size.width, cutCornerRadius.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }


            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(note.color),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )

                drawRoundRect(
                    color = Color(ColorUtils.blendARGB(note.color, 0x000000, 0.2f)),
                    topLeft = Offset(size.width - cutCornerRadius.toPx(), -100f),
                    size = Size(cutCornerRadius.toPx() + 100f, cutCornerRadius.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }

        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete note")
        }
    }
}

@Preview
@Composable
fun PreviewNoteItem() {
    NoteAppTheme {
        NoteItem(note = Note("하이", "내용", 20L, MaterialTheme.colorScheme.background.hashCode())) {}
    }
}


