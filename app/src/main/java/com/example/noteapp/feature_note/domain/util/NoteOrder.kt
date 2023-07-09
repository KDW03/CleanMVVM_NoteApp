package com.example.noteapp.feature_note.domain.util

/**
 * 각 서브클래스가 상태를 가지기 때문에 obejct 사용 x
 * OrderType 인자는 각 서브클래스의 인스턴스가 가지는 상태 -> 따라서 class 사용
 *
 *
 * 위에 방법은 객체를 새로 생성하지 않아서 메모리 사용량은 적지만
 * 기존의 객체를 변경하기 때문에 부작용이 발생할 수 있다
 *
 */

/*

sealed class NoteOrder(var orderType: OrderType) {
    class Title(orderType: OrderType) : NoteOrder(orderType)
    class Date(orderType: OrderType) : NoteOrder(orderType)
    class Color(orderType: OrderType) : NoteOrder(orderType)

    fun changeOrderType(): NoteOrder = this.apply {
        this.orderType = if (this.orderType == OrderType.Ascending) OrderType.Descending else OrderType.Ascending
    }

}


 */


sealed class NoteOrder(val orderType: OrderType) {
    class Title(orderType: OrderType) : NoteOrder(orderType)
    class Date(orderType: OrderType) : NoteOrder(orderType)
    class Color(orderType: OrderType) : NoteOrder(orderType)
    fun copy(orderType: OrderType): NoteOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}


