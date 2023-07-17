package com.example.noteapp.feature_note.presentation.notes

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteapp.feature_note.core.util.TestTags
import com.example.noteapp.feature_note.di.AppModule
import com.example.noteapp.feature_note.presentation.MainActivity
import com.example.noteapp.feature_note.presentation.add_edit_note.components.AddEditNoteScreen
import com.example.noteapp.feature_note.presentation.notes.components.NoteScreen
import com.example.noteapp.feature_note.presentation.util.Screen
import com.example.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            NoteAppTheme {
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


    @Test
    fun saveNetNote_editAfterwards() {


        composeRule.onNodeWithContentDescription("Add").performClick()
        composeRule.onNodeWithTag(testTag = TestTags.TITLE_TEXT_FIELD)
            .performTextInput("test-titles")
        composeRule.onNodeWithTag(testTag = TestTags.CONTENT_TEXT_FIELD)
            .performTextInput("test-content")
        composeRule.onNodeWithContentDescription("Save").performClick()


        composeRule.onNodeWithText("test-titles").assertIsDisplayed()
        composeRule.onNodeWithText("test-titles").performClick()
        composeRule.onNodeWithTag(testTag = TestTags.TITLE_TEXT_FIELD)
            .assertTextEquals("test-titles")
        composeRule.onNodeWithTag(testTag = TestTags.CONTENT_TEXT_FIELD)
            .assertTextEquals("test-content")
        composeRule.onNodeWithTag(testTag = TestTags.TITLE_TEXT_FIELD).performTextInput("2")
        composeRule.onNodeWithContentDescription("Save").performClick()
        composeRule.onNodeWithText("test-titles2").assertIsDisplayed()
    }

    @Test
    fun saveNewNotes_orderByTitleDescending() {
        for (i in 1..3) {
            composeRule.onNodeWithContentDescription("Add").performClick()
            composeRule.onNodeWithTag(testTag = TestTags.TITLE_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule.onNodeWithTag(testTag = TestTags.CONTENT_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Sort")
            .performClick()
        composeRule.onNodeWithContentDescription("Title")
            .performClick()
        composeRule.onNodeWithContentDescription("Descending")
            .performClick()

        (0..2).forEach {
            composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[it]
                .assertTextContains((3 - it).toString())
        }


    }
}