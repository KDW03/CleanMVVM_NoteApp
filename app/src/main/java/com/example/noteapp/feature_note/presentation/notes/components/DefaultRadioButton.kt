package com.example.noteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteapp.ui.theme.NoteAppTheme


/**
 *
 * semantics는 Jetpack Compose에서 접근성과 테스트를 지원하기 위한 기능을 제공
 * semantics는 Compose 노드에 메타데이터를 추가하여,
 * 접근성 도구(예: 화면 판독기)나 테스트 도구가 해당 노드를 이해하고 상호작용할 수 있도록 한다
 *
 *
 */
@Composable
fun DefaultRadioButton(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected, onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary,
                unselectedColor = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.semantics {
                contentDescription = text
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = text, style = MaterialTheme.typography.bodySmall)
    }

}


@Preview
@Composable
fun PreViewDefaultRadioButton() {
    NoteAppTheme() {
        DefaultRadioButton(text = "test", selected = false, onSelect = { /*TODO*/ })
    }
}