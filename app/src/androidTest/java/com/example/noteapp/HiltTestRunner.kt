package com.example.noteapp

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication
/**
 *
 * TestRunner는 테스트 케이스를 실행하는 역할을 한다.
 * 테스트 클래스를 로드하고, 테스트 메서드를 실행하며, 테스트의 결과를 수집
 *
 * build.gradle(app)의 testInstrumentationRunner에 해당 클래스 패키지 경로를 명시해야한다.
 *
 *
 * Test환경에서 Application 객체를 반환 받아 hilt를 사용할 수 있게 newApplication 메서드 오버라이딩
 *
 *
 *
 */





class HiltTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}