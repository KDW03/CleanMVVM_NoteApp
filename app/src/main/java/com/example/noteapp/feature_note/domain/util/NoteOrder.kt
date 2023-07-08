package com.example.noteapp.feature_note.domain.util

/**
 * 각 서브클래스가 상태를 가지기 때문에 obejct 사용 x
 * OrderType 인자는 각 서브클래스의 인스턴스가 가지는 상태 -> 따라서 class 사용
 */
sealed class NoteOrder(val orderType: OrderType) {
    class Title(orderType: OrderType) : NoteOrder(orderType)
    class Date(orderType: OrderType) : NoteOrder(orderType)
    class Color(orderType: OrderType) : NoteOrder(orderType)
}
