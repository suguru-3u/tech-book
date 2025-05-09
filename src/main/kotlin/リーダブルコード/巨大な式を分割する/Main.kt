package org.example.リーダブルコード.巨大な式を分割する

import java.time.LocalDate

/*問題---------------------------------------------------------------------------------------------*/

/**
 *
 * オンラインショップで商品を注文する際の割引適用ロジックを実装してください。
 * 条件が複雑なので、可読性が低くなっています。
 * そのため、「説明変数」や「要約変数」などを使って式を分割し、
 * 可読性の高いコードへリファクタリングしてください。
 *
 * 条件：
 * ・注文者がプレミアム会員である
 * ・かつ注文金額が10000円以上である
 * ・かつ注文日がセール期間内である（例：2025年5月1日〜5月10日）
 *
 * 上記のすべてを満たす場合に、注文に対して割引を適用してください。
 *
 */

fun applyDiscountIfEligible(
    isPremiumUser: Boolean,
    totalAmount: Int,
    orderDate: LocalDate
): Boolean {
    return isPremiumUser &&
            totalAmount >= 10_000 &&
            (orderDate >= LocalDate.of(2025, 5, 1) &&
                    orderDate <= LocalDate.of(2025, 5, 10))
}

fun main() {
    val isPremiumUser = true
    val totalAmount = 12000
    val orderDate = LocalDate.of(2025, 5, 4)

    if (applyDiscountIfEligible(isPremiumUser, totalAmount, orderDate)) {
        println("割引を適用しました")
    } else {
        println("割引の適用条件を満たしていません")
    }
}

/*メモ---------------------------------------------------------------------------------------------*/

/**
 * 式を分割する際は、式を表す変数を使うことで可動性が向上する。
 * この変数は説明・要約変数という
 * 短絡評価は使ってもちろんいい。一瞬で判断できる式は要約する必要はない
 * 逆にすぐに理解できなければ、説明・要約変数を使うといい。
 */

fun sample1() {

    // 説明変数--------------------------------------------------------------------------------------
    val line = "root:x:0:0:root:/root:/bin/bash"

    if (line.split(":")[0].trim() == "root") {
        println("Root user found")
    }

    val userName = line.split(":")[0].trim()
    if (userName == "root") {
        println("何を比較しているのかわかりやすい！")
    }

    // 要約変数--------------------------------------------------------------------------------------
    data class User(val id: String)
    data class Request(val user: User)
    data class Document(val ownerId: String)

    val user = User(id = "123")
    val request = Request(user = user)
    val document = Document(ownerId = "123")

    if (request.user.id == document.ownerId) {
        println("User is the owner of the document.")
    }
    if (request.user.id != document.ownerId) {
        println("User is NOT the owner.")
    }

    val userOwnsDocument = request.user.id == document.ownerId
    if (userOwnsDocument) {
        println("わかりやすい！")
    }

    if (!userOwnsDocument) {
        5
        println("わかりやすい！")
    }
}

/**
 * 複雑なロジックは反対から問題を解決してみる
 *
 * このプログラムは、2つの数値範囲（Range）に重なり（オーバーラップ）があるかどうかを判定するもの。
 * 複雑なロジックは反対に考えてみると簡単に問題を解決することができる。
 * またはド・モルガンの法則を使用することでも解消できる
 *
 */

fun sample2() {
    data class Range(val begin: Int, val end: Int) {
        fun overlapsWithV1(other: Range): Boolean {
            return (begin >= other.begin && begin <= other.end) ||
                    (end >= other.begin && end <= other.end)
        }

        // 重ならないように判定することで式が単純化する！
        fun overlapsWithV2(other: Range): Boolean {
            if (other.end <= begin) return false
            if (other.begin >= end) return false
            return true
        }
    }

    fun main() {
        val range1 = Range(5, 10)
        val range2 = Range(8, 15)

        if (range1.overlapsWithV1(range2)) {
            println("Ranges overlap!")
        } else {
            println("Ranges do not overlap.")
        }

        if (range1.overlapsWithV2(range2)) {
            println("Ranges overlap!")
        } else {
            println("Ranges do not overlap.")
        }
    }
}
