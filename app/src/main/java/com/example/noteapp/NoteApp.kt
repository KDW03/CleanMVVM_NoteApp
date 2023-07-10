package com.example.noteapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * @HiltAndroidApp 어노테이션은 Hilt가 코드를 생성하게 하여 앱이 의존성 주입을 가능하게 하는 트리거 역할
 * 이 어노테이션은 애플리케이션 레벨의 의존성 컨테이너 역할을 하는 Base Application을 만들고 Hilt에게 코드 생성을 하도록 트리거를 만듬
 *
 *
 * 따라서, @HiltAndroidApp 어노테이션이 붙은 클래스는 앱의 수명주기와 밀접하게 연결되며,
 * Hilt를 사용하고자 하는 모든 앱은 @HiltAndroidApp 어노테이션을 포함한 Application 클래스를 가지고 있어야 한다
 *
 * Hilt는 안드로이드에서 의존성 주입을 도와주는 라이브러리로 수명주기를 자동으로 관리해준다
 * Hilt는 Dagger 라이브러리르 기반, Jetpack에서 권장하는 DI 라이브러리
 *
 *
 * 의존성 주입은 객체 간의 의존 관계를 객체 자신이 아닌 외부에서 설정하는 것을 의미
 *이를 통해 코드 재사용성, 리팩터링 편의성, 테스트 편의성 등의 이점
 *
 * Hilt는 프로젝트에서 수동 DI를 사용하는 상용구 코드를 줄여 줌
 *
 */
@HiltAndroidApp
class NoteApp : Application() {

}