package org.example.良いコード悪いコード学ぶ設計入門.名前設計

// 概要

/**
 * 適切に名前をつけることは、クラス間の結合度合いを低くし
 * 可読性だけでなく、保守性もあげることができる
 */

// 関心事の分離

/**
 * Userなど広い定義でクラスを作成するとさまざまな関心毎のロジックがクラスに集中してしまい
 * クラスが肥大化し、保守しにくくなってしまう。
 * そのため、関心毎事にクラスを作成することがいいとされている。
 *
 * 例：
 * Userクラス →　法人ユーザー、一般ユーザー、管理者ユーザー
 * 商品クラス　→　予約品・注文品・在庫品・発送品
 */

// ポイント

/**
 * 関心事にクラスを分割する
 * 可能な限り具体的で、意味範囲が狭い、特化した名前を選ぶ
 * 存在ベースではなく、目的に特化した名前を考える
 * どんな関心事があるか分析する
 * 声に出して話してみる
 * 利用規約を読んでみることで、関心事や概念を把握することができる
 * 違う名前に置き換えられないか検討することで、名前をより洗練されたものにする
 * 疎結合高凝集になっているか点検することで、クラス同士の関連性を確認する。多いい場合は名前のつけ直しを行う
 */

// 設計時に注意すべきポイント

/**
 * 仕様が変更される際の変化に気をつける。必要であればクラスを分割する
 * 会話に登場するのにコードに登場しない場合、その言葉はクラス化した方がいい
 * 形容詞で区別が必要な時はクラス化のチャンス
 *
 * 例：
 * 違いのよくわからないロジックを同僚に尋ねる場合、形容詞に注意を傾けましょう。いくつか例を挙げます。
 * 「このフラグが立っているときのUserは要注意会員」
 * 「この行のpriceは新品価格で、次の行のpriceは中古価格」
 * 「Ticketクラスは、年齢が60歳以上ならシニア料金用になって、さらに平日なら平日のシニア料金用に変わるんだ」
 * このように違いを形容詞で表現している場合、それぞれクラスとして設計できないか検討してみましょう。
 */

// 改善前のコード
class Member(val maxHitPoint: Int)

class Accessory(private val bonus: Int) {
    fun maxHitPointIncrements(): Int = bonus
}

class Armor(private val bonus: Int) {
    fun maxHitPointIncrements(): Int = bonus
}

fun main() {
    val member = Member(maxHitPoint = 100)
    val accessory = Accessory(bonus = 20)
    val armor = Armor(bonus = 30)

    //
    val maxHitPoint = member.maxHitPoint +
            accessory.maxHitPointIncrements() +
            armor.maxHitPointIncrements()

    println("最大HP: $maxHitPoint") // → 最大HP: 150
}

//　改善後のコード
// ヒットポイントの合計をクラスにまとめることで、保守性が上がっている。（値オブジェクトにしても良いと思う）
class OriginalMaxHitPoint(val value: Int) {
    companion object {
        private const val MIN = 10
        private const val MAX = 999
    }

    init {
        require(value in MIN..MAX) {
            "value must be between $MIN and $MAX"
        }
    }
}

class CorrectedMaxHitPoint(
    originalMaxHitPoint: OriginalMaxHitPoint,
    accessory: Accessory,
    armor: Armor
) {
    val value: Int = originalMaxHitPoint.value +
            accessory.maxHitPointIncrements() +
            armor.maxHitPointIncrements()
}


// 技術駆動命名

/**
 * 技術に関数用語を使用して命名することもある。
 * そのことを技術駆動命名という。
 * ハード系の開発なら使うこともあるが、WEB系は基本使用しない
 */

// 驚き最小の原則

/**
 * countにはヒットポイントの値がレスポンスされることが想像できますが
 * ヒットポイントの別の値がレスポンスされるかもしれません。
 * この現象は機能改修を行った際に発生する可能性があり、別の値をレスポンスする場合は
 * クラスや関数を分離する必要がある。
 */
val count = OriginalMaxHitPoint(10).value

// クラスが巨大化する名前

/**
 * Managerやinfoなど抽象度が高い名前をつけてしまうと様々なロジックがクラスに記述されてしまいます。
 * その結果、巨大な神クラスとなってしまいます。
 * クラス名を名付ける際は、できる限り具体的な名前をつけるようにする
 */

// 状況によって意味が異なる言葉

/**
 * 状況によって意味が異なる言葉はコンテキスト毎にパッケージを分けることで対応するようにする。
 *
 * 例：
 * Carクラス
 * 配送時と販売時では意味が異なる
 * 配送時は、配送元と配送先などの情報が必要
 * 販売時は、販売価格、販売オプションが必要になる
 * この場合、配送パッケージと販売パッケージを分け、それぞれCarクラスを作成することで異なるコンテキスト内でも
 * 適切にクラス設計ができる、疎結合にすることができる
 */

// 動詞と目的語のメソッド

/**
 * クラスのメソッドが動詞と目的語になっている場合は、クラス設計を見直した方が良いかもしれない
 * 例
 * Member2クラスにitemの配列をもったクラスを作成しているが、これはItemのファーストコレクションクラスを作成し
 * そこでメソッドを追加することの方が良さそう
 *
 * 以下の例のように基本的にメソッドは一言で住むように設計してあげること大切
 */

@JvmInline
value class Item(val name:String)

// 改善前
class Member2(
    val item: List<Item>
){
    fun addItem(){}
}

// 改善後
// 責務の分離を行えてテストもしやすく・再利用しやすくもなった
class Items(
    val item: List<Item>
){
    fun add(item: Item){}
}

class Member3(){}

// booleanをメソッドを作成する場所のポイント

/**
 * 英語に翻訳してみて、(クラス名) は　(メソッドのやりたいこと) です。
 * この意味がおかしくなければ、booleanをメソッドを配置する場所は正しいと言えます。
 */

// 名前の省略はしないこと