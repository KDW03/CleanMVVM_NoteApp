package com.example.noteapp.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle


/**
 * TextField는 Material 디자인을 기본으로 하기 때문에 다양한 모양의 TextField를 표현하기에는 제한적
 * foundation 아티팩트에 위치한 BasicTextField를 사용하여 다양하게 커스텀
 *
 * BasicTextField에는 placeholder(hint)가 없다 따라서 placeholder를 넣어주고 싶은 경우에는 직접 Text로 넣어주면 된다.
 *
 *
 *
 *
 */
@Composable
fun TransparentHintTextFiled(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    testTag: String = "",
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit
) {

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(testTag)
                .onFocusChanged {
                    onFocusChange(it)
                }
        )
        if (isHintVisible) Text(text = hint, style = textStyle, color = Color.DarkGray)
    }

}