package org.example.clean_architecture_達人に学ぶソフトウェアの構造と設計.Solid原則

/**
 * SOLID原則：単一責任の原則 (SRP) の問題と解答
 *
 * このファイルは、単一責任の原則に違反しているコードと、
 * それを原則に従ってリファクタリングしたコードを示しています。
 * * 問題：
 * 以下の `OrderProcessor` クラスは、注文の検証、データベースへの保存、
 * 顧客への確認メール送信という複数の責任を担っています。
 * この設計は単一責任の原則に違反しています。
 */

// --- 違反しているコード例 ---
data class Order(val id: Int, val items: List<String>, val customerEmail: String)

class OrderProcessor {
    fun processOrder(order: Order) {
        // 注文の検証
        validateOrder(order)
        // データベースに注文を保存
        saveToDatabase(order)
        // 顧客に確認メールを送信
        sendConfirmationEmail(order)
    }

    private fun validateOrder(order: Order) {
        println("注文 ${order.id} の検証を実行中...")
        // 検証ロジック
    }

    private fun saveToDatabase(order: Order) {
        println("注文 ${order.id} をデータベースに保存中...")
        // データベース保存ロジック
    }

    private fun sendConfirmationEmail(order: Order) {
        println("注文 ${order.id} の確認メールを ${order.customerEmail} に送信中...")
        // メール送信ロジック
    }
}

/**
 * 解答：
 * 単一責任の原則に従い、注文の処理に関する各責任を独立したクラスに分割します。
 * - `OrderValidator`: 注文の検証のみを担当。
 * - `OrderRepository`: 注文の永続化（保存）のみを担当。
 * - `EmailNotifier`: 顧客への通知（メール送信）のみを担当。
 *
 * これにより、例えばメール送信方法を変更したい場合でも、`EmailNotifier`クラスのみを
 * 修正すればよく、他のクラスに影響を与える心配がなくなります。
 */

// --- 原則に準拠した改善コード例 ---
class OrderValidator {
    fun validate(order: Order) {
        println("注文 ${order.id} の検証を実行中...")
        // 検証ロジック
    }
}

class OrderRepository {
    fun save(order: Order) {
        println("注文 ${order.id} をデータベースに保存中...")
        // データベース保存ロジック
    }
}

class EmailNotifier {
    fun notifyCustomer(order: Order) {
        println("注文 ${order.id} の確認メールを ${order.customerEmail} に送信中...")
        // メール送信ロジック
    }
}

class RefactoredOrderProcessor(
    private val validator: OrderValidator,
    private val repository: OrderRepository,
    private val notifier: EmailNotifier
) {
    fun processOrder(order: Order) {
        validator.validate(order)
        repository.save(order)
        notifier.notifyCustomer(order)
    }
}

// 実行例
fun main() {
    val order = Order(123, listOf("Keyboard", "Mouse"), "test@example.com")

    println("--- 違反しているコードの実行 ---")
    val oldProcessor = OrderProcessor()
    oldProcessor.processOrder(order)
    println()

    println("--- 原則に準拠したコードの実行 ---")
    val newProcessor = RefactoredOrderProcessor(
        OrderValidator(),
        OrderRepository(),
        EmailNotifier()
    )
    newProcessor.processOrder(order)
}