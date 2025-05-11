package org.example.良いコード悪いコード学ぶ設計入門.クラス設計


/*メモ---------------------------------------------------------------------------------------------*/
/**
 * クラス変数とメソッドは一つのクラスに集中するようにする
 * コンストラクタで確実に正常値を設定する
 * ロジックをデータ保持側に寄せる
 * 作成したインスタンス変数は不変にすることで思わぬバグを防ぐ
 * 変更したい場合は、新しいインスタンスを作成する
 * クラス内のメソッドの引数やローカル変数も可能であれば不変にする
 * 引数の型をプリミティブ型ではなく、クラスの型にすることで引数の値渡し間違えを防止できる
 *
 * プログラム設計の構造を改善する設計パターン
 * ・完全コンストラクタ
 * ・値オブジェクト
 * ・ストラテジ
 * ・ポリシー
 * ・ファーストクラスコレクション
 * ・スプラウトクラス
 */

/**
 * サンプルプログラム
 * 以下の機能を追加してください
 *
 * 1.amountは0以上、currencyはnull以外の値が入るようにしてください
 * 2.amountを足すaddメソッドを作成してください
 * 3.addメソッドの戻り値に、amountの情報がレスポンスされるようにしてください
 * 4.addメッド実行時にcurrencyの名前が同じ場合のみ、実行されるようにしてください
 * 5.できる限り不変にしてください
 */
data class Currency(val name: String)

class Money(
    val amount: Int,
    val currency: Currency
)

class MoneyV2(
    val amount: Int,
    val currency: Currency
) {
    private val minAmount = 0

    init {
        check(minAmount <= amount) { "amountに不正な値が設定されています" }
        check(currency == null) { "currencyにnullが設定されています" }
    }

    fun add(money: MoneyV2): MoneyV2 {
        if (money.currency.equals(currency.name)) {
            throw IllegalArgumentException("通貨の単位が異なります")
        }
        return MoneyV2(amount + money.amount, currency)
    }
}
