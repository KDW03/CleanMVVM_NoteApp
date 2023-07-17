package com.example.noteapp.feature_note.presentation.notes.components

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.feature_note.core.util.TestTags
import com.example.noteapp.feature_note.di.AppModule
import com.example.noteapp.feature_note.presentation.MainActivity
import com.example.noteapp.feature_note.presentation.util.Screen
import com.example.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
/**
 * 실제 모듈을 사용하지 않기 위해 @UninstallModules 로 실제 모듈 제거
 * HiltAndroidTest 어노테이션이 붙어 있어서 Hilt를 사용한 의존성 주입이 가능
 *
 *
 * 규칙(Rules)은 JUnit 테스트 프레임워크에서 제공하는 기능으로, 테스트 메소드 실행 전후에 어떤 작업을 수행하도록 할 수 있다
 * 예를 들어, 테스트 환경을 설정하거나, 테스트 데이터를 초기화하거나, 테스트가 끝난 후 리소스를 정리하는 등의 작업을 수행
 *
 * order 속성은 여러 규칙이 있을 때 그 실행 순서를 정의하는 데 사용
 * 숫자가 작을수록 먼저 실행됩니다. 이 테스트에서는 Hilt 규칙이 먼저 실행되어 의존성 주입이 이루어진 후
 * Compose 규칙이 실행되어 UI 설정이 이루어지도록 하기 위해 order 속성을 사용
 *
 * composeRule은 Compose UI 테스트를 위한 주요 도구
 * Compose UI를 검증하고 상호작용을 시뮬레이션하는 데 사용
 *
 *
 * 1. onNode :  Compose 노드를 찾는 데 사용 SemantricsNodeInteractions를 반환
 *  - onNodeWithText(String) : 주어진 텍스트를 가진 노드를 찾는다.
 *  - onNodeWithTag(String) : 주어진 태그를 가진 노드를 찾는다
 *  - onNodeWithContentDescription(String) : 주어진 content description을 가진 노드를 찾는다
 *
 * 2. setContent : 테스트에서 사용할 Compose UI를 설정하는 데 사용
 *
 * 3. waitForIdle : Compose가 현재 펜딩 중인 모든 작업을 완료할 때까지 테스트를 일시 중지
 *
 * 4. runOnIdle : Compose가 현재 펜딩 중인 모든 작업을 완료한 후에 실행할 작업을 예약한다.
 *
 * 5. clockTestRule : Compose 에니메이션을 제어 가능
 *
 * onNode의 메서드 반환 객체 메서드
 * 1. assertExists : 노드가 존재함을 확인
 * 2. assertDoesNotExits : 노드가 존재하지 않음을 확인
 * 3. assertIsDisplayed : 노드가 화면에 표시되고 있음을 확인
 * 4. assertIsNotDisplayed : 노드가 화면에 표시되고 있지 않음을 확인
 * 5. assertHasContentDescription(String) : 노드가 주어진 content description을 가지고 있음을 확인
 * 6. performClick : 노드를 클릭하는 동작을 시뮬레이션
 * 7. performScrollTo : 노드가 스크롤 가능한 경우, 해당 노드로 스크롤하는 동작을 시뮬레이션
 *
 * 펜딩이란? "보류 중" 이라는 뜻으로 Compose가 현재 처리해야 할 작업이 아직 완료되지 않음을 뜻함
 *
 *
 *
 *
 * MainActivity의 Compose 환경을 설정하고, 그 환경 내에서 NoteScreen을 표시
 * NoteScreen의 동작을 독립적으로 테스트
 *
 *
 *
 *
 *
 */

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            NoteAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(route = Screen.NotesScreen.route) {
                        NoteScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
    }
}