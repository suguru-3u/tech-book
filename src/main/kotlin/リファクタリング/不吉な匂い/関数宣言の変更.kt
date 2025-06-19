package org.example.リファクタリング.不吉な匂い

// 関数名を変更する
fun circum(radius: Double): Double {
    return 2 * Math.PI * radius
}

fun circumference(radius: Double): Double {
    return 2 * Math.PI * radius
}

// 関数のプロパティを変更する
// 仮のデータクラス定義 (実際のアプリケーションに合わせて調整してください)
data class Address(val state: String)
data class Customer(val address: Address)

fun inNewEngland(aCustomer: Customer): Boolean {
    val newEnglandStates = listOf("MA", "CT", "ME", "VT", "NH", "RI")
    return newEnglandStates.contains(aCustomer.address.state)
}

// Caller example (Kotlin Collections)
/*
val someCustomers: List<Customer> = // ... your list of customers
val newEnglanders = someCustomers.filter { inNewEngland(it) }
*/

fun inNewEngland(stateCode: String): Boolean {
    val newEnglandStates = listOf("MA", "CT", "ME", "VT", "NH", "RI")
    return newEnglandStates.contains(stateCode)
}

// 段階的な移行方法
class OldBook {
    private val _reservations = mutableListOf<Customer>() // Customerは適切な型に置き換えてください

    fun addReservation(customer: Customer) {
        zz_addReservation(customer)
    }

    private fun zz_addReservation(customer: Customer) {
        _reservations.add(customer)
    }
}

class NewBook1 {
    private val _reservations = mutableListOf<Customer>()

    fun addReservation(customer: Customer, isPriority: Boolean) {
        zz_addReservation(customer, isPriority)
    }

    private fun zz_addReservation(customer: Customer, isPriority: Boolean) {
        // isPriority を利用したロジックがあればここに追加
        _reservations.add(customer)
    }
}

class NewBook2 {
    private val _reservations = mutableListOf<Customer>()

    // addReservation を呼び出す側で適切な引数を渡すことを想定
    fun addReservation(customer: Customer, isPriority: Boolean) {
        zz_addReservation(customer, isPriority)
    }

    private fun zz_addReservation(customer: Customer, isPriority: Boolean) {
        // Kotlinのassertはデフォルトでテスト時のみ有効なので、実行時チェックにはrequire/checkが適切
        require(isPriority == true || isPriority == false) { "isPriority must be true or false" }
        // またはシンプルに
        // require(isPriority in listOf(true, false)) { "isPriority must be true or false" }

        _reservations.add(customer)
    }
}