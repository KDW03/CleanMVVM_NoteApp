package com.example.noteapp.feature_note.domain.util


/**
 * 정렬 순서를 나타내는 클래스, 두 개의 서브클래스 Ascending, Descending
 * sealed 클래스는 클래스 계층을 표현하는 데 사용
 *
 * sealed 클래스를 사용하면 컴파일러가 sealed 클래스의 서브클래스들을 모두 검사
 * when 문에서 sealed 클래스의 인스턴스를 검사할 때, 컴파일러는 모든 서브클래스가 처리되었는지 확인
 * 만약 어떤 서브클래스가 처리되지 않았다면, 컴파일러는 경고 메시지를 출력
 *
 *  object 키워드는 코틀린에서 싱글턴 객체를 정의하는 데 사용
 *  싱글턴 객체는 클래스의 인스턴스가 하나만 존재하는 객체를 의미
 *
 *  object 키워드를 사용하여 정의된 객체는 프로그램 전체에서 공유
 *  OrderType sealed 클래스의 서브클래스들은 상태를 가지지 않으므로, 각 서브클래스의 인스턴스가 하나만 존재해도 충분
 *
 */
sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}
