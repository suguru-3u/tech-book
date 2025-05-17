package org.example.良いコード悪いコード学ぶ設計入門.低凝集

/*問題---------------------------------------------------------------------------------------------*/
/**
 * 問題：
 * あなたは会員のギフトポイントを管理するシステムを開発しています。
 *
 * ギフトポイントは、必ず0以上でなければならず、加算・消費・残高チェックの機能を持ちます。
 * また、消費時にポイントが不足していれば例外をスローしなければなりません。
 *
 * 次の要件を満たす `GiftPoint` クラスと `ConsumptionPoint` クラスを作成しなさい：
 *
 * - `GiftPoint` クラスは、現在のポイント数（整数）を保持し、以下の機能を持つこと：
 *     - 他の `GiftPoint` を加算して新しい `GiftPoint` を返す `add` メソッド
 *     - `ConsumptionPoint` を引数にとり、十分なポイントがあるかを返す `isEnough` メソッド
 *     - ポイントを消費し、新しい `GiftPoint` を返す `consume` メソッド（不足していれば例外をスローする）
 * - `ConsumptionPoint` クラスは、消費するポイント数を表す
 * - `GiftPoint` のポイント数が0未満の場合はインスタンス生成時に例外をスローすること
 * - 静的メソッドを用いたファクトリーメソッドを `GiftPoint` に1つ定義すること（任意の初期値から生成する用途）
 *
 * 備考：
 * 共通ユーティリティや static 的な設計の濫用に注意し、凝集度を意識してクラスを構成してください。
 */

@JvmInline
value class ConsumptionPoint(val value: Int)

class GiftPointV2 private constructor(private val value: Int) {

    private val minPoint = 0

    init {
        check(minPoint > value) { "GiftPointV2に不正な値がリクエストされました" }
    }

    fun add(addPoint: GiftPointV2): GiftPointV2 {
        return GiftPointV2(value + addPoint.value)
    }

    fun isEnough(point: ConsumptionPoint): Boolean {
        return point.value <= value
    }

    fun consume(point: ConsumptionPoint): GiftPointV2 {
        if (!isEnough(point)) {
            throw IllegalArgumentException("ポイントが不足しています。")
        }
        return GiftPointV2(value - point.value)
    }

    companion object {
        private val standardMembershipPoint = 3000

        fun forStandardMembershipPoint(): GiftPointV2 {
            return GiftPointV2(standardMembershipPoint)
        }
    }
}

fun main() {
    // ここでGiftPointやConsumptionPointを使ったテストが可能です
}

/*メモ---------------------------------------------------------------------------------------------*/
/**
 * 基本的にデータクラスではなく、データとロジックで一つのクラスになっていれば、低凝集になることはないが
 * 静的メソッドを正しく使用できていないと、低凝集になる可能性がある
 */

// このクラスは、メソッドが扱うデータが定義されていない
class OrderManager {
    companion object {
        fun add(moneyAmount1: Int, moneyAmount2: Int): Int {
            return moneyAmount1 + moneyAmount2
        }
    }
}

// 一見データとメソッドのクラスに見えるが、インスタンス変数をメソッドが使用していない..
class PaymentManager {
    private var discountRate: Int = 0  // 割引率

    // 省略

    fun add(moneyAmount1: Int, moneyAmount2: Int): Int {
        return moneyAmount1 + moneyAmount2
    }
}

/**
 * staticメソッドには正しい使い方があります。 凝集度に影響がない場合に、staticメソッドを使えます。
 * ログ出力用メソッドやフォーマット変換用メソッドなど、凝集度に無関係なものはstaticメソッドとして設計して良い。
 * ファクトリメソッドとしてstaticメソッドを用いるのが良いでしょう。
 */



// ファクトリメソッドを使用してみた（生成ロジックが増えすぎたらファクトリクラスの作成も検討する）
class GiftPoint private constructor(private val value: Int) {

    private val minPOINT = 0

    init {
        if (value < minPOINT) {
            throw IllegalArgumentException("ポイントが0以上ではありません。")
        }
    }

    /**
     * ポイントを加算する。
     *
     * @param other 加算ポイント
     * @return 加算後の残余ポイント
     */
    fun add(other: GiftPoint): GiftPoint {
        return GiftPoint(value + other.value)
    }

    /**
     * @return 残余ポイントが消費ポイント以上であればtrue
     */
    fun isEnough(point: ConsumptionPoint): Boolean {
        return point.value <= value
    }

    /**
     * ポイントを消費する。
     *
     * @param point 消費ポイント
     * @return 消費後の残余ポイント
     */
    fun consume(point: ConsumptionPoint): GiftPoint {
        if (!isEnough(point)) {
            throw IllegalArgumentException("ポイントが不足しています。")
        }
        return GiftPoint(value - point.value)
    }

    companion object {
        private val standardMembershipPoint = 3000

        private val premiumMembershipPoint = 10000

        fun forStandardMembershipPoint(): GiftPoint {
            return GiftPoint(standardMembershipPoint)
        }

        fun forPremiumMembershipPoint(): GiftPoint {
            return GiftPoint(premiumMembershipPoint)
        }
    }
}
/**
 * 基本的にデータクラスではなく、データとロジックで一つのクラスになっていれば、低凝集になることはないが
 * 静的メソッドを正しく使用できていないと、低凝集になる可能性がある
 */

/**
 * staticメソッドには正しい使い方があります。 凝集度に影響がない場合に、staticメソッドを使えます。
 * ログ出力用メソッドやフォーマット変換用メソッドなど、凝集度に無関係なものはstaticメソッドとして設計して良い。
 * ファクトリメソッドとしてstaticメソッドを用いるのが良いでしょう。
 */

/**
 * commonやutilクラスを作成する際に気をつけること
 * ・基本的には作成せずに、オブジェクト指向でクラスを作成するようにする
 *
 * さまざまなユースケースに広く横断する事柄を、横断的関心事と呼びます。
 * 代表的なものとして以下が挙げられます。 ログ出力 エラー検出 デバッグ 例外処理 キャッシュ 同期処理 分散処理
 * これらの処理は共通処理としてまとめていい！
 */

/**
 * その他のメモ
 * 多すぎする引数は関心毎を分割して正しくクラスにする
 * プリミティブ型をクラス化させる
 * メソッドチェインは使用しないようにする。尋ねるな命じろ
 */