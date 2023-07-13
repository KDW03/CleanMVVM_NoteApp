package com.example.noteapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteapp.feature_note.presentation.add_edit_note.components.AddEditNoteScreen
import com.example.noteapp.feature_note.presentation.notes.components.NoteScreen
import com.example.noteapp.feature_note.presentation.util.Screen
import com.example.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint


/**
 * NavHost는 안드로이드의 Navigation 컴포넌트에서 사용되는 인터페이스
 * 이 인터페이스는 사용자가 앱 내에서 이동할 수 있는 목적지들을 관리
 *
 *
 *
 *
 * NavController는 Navigation 컴포넌트의 중심 API로, 앱의 화면들을 구성하는 컴포지션들의 백 스택과 각 화면의 상태를 관리
 * NavController는 rememberNavController() 메소드를 사용하여 생성
 *
 * NavHost를 생성하려면 먼저 NavController와 navigation graph의 시작 목적지의 route를 지정
 * lambda 구문을 사용하여 navigation graph를 구성
 * composable() 메소드를 사용하여 route와 연결된 컴포지션을 추가
 *
 *
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                    ) {
                        composable(route = Screen.NotesScreen.route) {
                            NoteScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditNoteScreen.route
                                    + "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(name = "noteId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(name = "noteColor") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(navController = navController, noteColor = color)
                        }
                    }

                }
            }
        }
    }
}

