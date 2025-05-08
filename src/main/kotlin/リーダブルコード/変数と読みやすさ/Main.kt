package org.example.リーダブルコード.変数と読みやすさ

import java.time.LocalDateTime

fun main() {

}

/*問題---------------------------------------------------------------------------------------------*/

/**
 * 【問題】
 *
 * 以下の関数 `removeFirstMatch` は、リストから最初に一致する値を削除する関数です。
 * ただし、「リーダブルコード」の観点から見ると、いくつか改善できる点があります。
 *
 * 1. 読みやすさが低下する変数を使っている
 * 2. 再代入される変数を使用している
 * 3. 変数のスコープが広すぎる
 *
 * 上記の観点からコードをリファクタリングし、より読みやすく、意図が明確な関数に書き換えてください。
 *
 * Kotlin の構文と標準ライブラリを活用して、コードを改善してください。
 */

fun removeFirstMatch(list: MutableList<String>, target: String) {
    var foundIndex: Int? = null
    for (i in list.indices) {
        if (list[i] == target) {
            foundIndex = i
            break
        }
    }

    if (foundIndex != null) {
        list.removeAt(foundIndex)
    }
}

/**
 * 【問題】
 *
 * 以下の関数 `calculateTotalLength` は、複数の文字列の長さを足し合わせて合計を返します。
 * ただし、「リーダブルコード」の観点から見ると、いくつか改善できる点があります。
 *
 * - 意図が不明瞭な変数名
 * - 再代入される変数の使用
 * - スコープが広すぎる変数
 *
 * この関数を、より読みやすく、意図が明確な Kotlin のコードにリファクタリングしてください。
 */

fun calculateTotalLength(strings: List<String>): Int {
    var x = 0
    for (s in strings) {
        x += s.length
    }
    return x
}

/**
 * 注文処理システムの改善
 * 以下は、注文処理のための簡単な関数です。
 * 注文ごとのアイテムの合計金額を計算して、割引を適用し、最終的に顧客に請求する金額を返すという処理を行っています。
 *
 * 関数名や変数名を見直して、意図が明確に伝わるようにしてください。
 * 必要以上に大きな関数や重複したコードをなくしてください。
 * 各処理の責任を **単一の関数** に絞るようにしてください。
 */

data class OrderItem(val name: String, val price: Double, val quantity: Int)

fun calculateTotal(orderItems: List<OrderItem>, discount: Double): Double {
    var totalAmount = 0.0
    for (item in orderItems) {
        totalAmount += item.price * item.quantity
    }

    val discountedAmount = totalAmount * (1 - discount)

    return discountedAmount
}

fun processOrder(orderItems: List<OrderItem>, discount: Double, customerName: String): String {
    val totalAmount = calculateTotal(orderItems, discount)
    return "Order for $customerName, Total: \$${"%.2f".format(totalAmount)}"
}

/*回答---------------------------------------------------------------------------------------------*/

fun removeFirstMatchV2(list: MutableList<String>, target: String) {
    for (index in list.indices) {
        if (list[index] == target) {
            list.removeAt(index)
            break
        }
    }
}

fun calculateTotalLengthV2(strings: List<String>): Int {
    return strings.sumOf { it.length }
}

data class OrderItemv2(val name: String, val price: Double, val quantity: Int)

fun calculateTotalV2(orderItems: List<OrderItemv2>): Double {
    return orderItems.sumOf { it.price * it.quantity }
}

fun calculateDiscount(total: Double, discount: Double): Double {
    return total * (1 - discount)
}

fun formatOrderSummaryv2(amount: Double, customerName: String): String {
    return "Order for $customerName, Total: \$${"%.2f".format(amount)}"
}

fun processOrderV2(orderItems: List<OrderItemv2>, discount: Double, customerName: String): String {
    val total = calculateTotalV2(orderItems)
    val discountedTotal = calculateDiscount(total, discount)
    return formatOrderSummaryv2(discountedTotal, customerName)
}


/*メモ---------------------------------------------------------------------------------------------*/

/**
 * 変数を削除する
 *
 * ・コードが読みやすくならない変数を削除する
 * ・中間結果を削除する
 * ・whileでループを継続するか制御している変数は使用しないようにプログラミングする
 *
 * 変数のスコープはできる限り縮める
 *
 * 変数は再代入しないようにする
 */

fun sample1() {
    // 巨大な式を分割せず、行っていることが明確になっていない、一度しか使用していないので重複コードの削除にもならない
    val now = LocalDateTime.now()
    val currentTime = now
}


fun sample2(array: MutableList<Any>, valueToRemove: Any) {
    // indexToRemoveは中間結果を保持するためにだけに使用している
    var indexToRemove: Int? = null
    for (i in array.indices) {
        if (array[i] == valueToRemove) {
            indexToRemove = i
            break
        }
    }
    if (indexToRemove != null) {
        array.removeAt(indexToRemove)
    }
}

// sample2の改善版
fun sample2V2(array: MutableList<Any>, valueToRemove: Any) {
    for (index in array.indices) {
        if (array[index] == valueToRemove) {
            array.removeAt(index)
        }
    }
}
