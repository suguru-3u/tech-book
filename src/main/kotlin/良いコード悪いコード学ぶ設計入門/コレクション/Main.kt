package org.example.良いコード悪いコード学ぶ設計入門.コレクション

/*メモ---------------------------------------------------------------------------------------------*/

/**
 * 自前でコレクション処理を実装しない
 * 便利な処理は先人が作成してくれている
 */

fun sample1() {
    val testList = listOf(1, 2, 3, 4, 5)

    // 良くない例
    for (test in testList) {
        if (test > 2) {
            println("2以上だ")
        }
    }

    // 改善案
    testList.filter { it > 2 }.forEach { println("2以上だ") }
}

/**
 * ループ処理中の条件分岐ネスト
 * ループの中身もネストしないように気をつけましょう！
 */
enum class StateType {
    poison,
    dead
}

class Member(
    var hitPoint: Int,
    private val states: MutableSet<StateType> = mutableSetOf()
) {
    fun containsState(state: StateType): Boolean {
        return state in states
    }

    fun addState(state: StateType) {
        states.add(state)
    }

    fun removeState(state: StateType) {
        states.remove(state)
    }

    override fun toString(): String {
        return "HP: $hitPoint, States: $states"
    }
}

fun sample2() {
    val members = listOf(
        Member(hitPoint = 50, states = mutableSetOf(StateType.poison)),
        Member(hitPoint = 30),
        Member(hitPoint = 10, states = mutableSetOf(StateType.poison)),
        Member(hitPoint = 0, states = mutableSetOf(StateType.dead))
    )

    // 良くない例
    for (member in members) {
        if (member.hitPoint > 0) {
            if (member.containsState(StateType.poison)) {
                member.hitPoint -= 10
                if (member.hitPoint <= 0) {
                    member.hitPoint = 0
                    member.addState(StateType.dead)
                    member.removeState(StateType.poison)
                }
            }
        }
    }

    // 改善例
    // ネストが改善された（場合によっては、breakの使用も可能）
    // さらに改善するなら、Memberクラス内に条件分岐のロジックを追加する
    for (member in members) {
        if (member.hitPoint < 0) {
            continue
        }

        if (!member.containsState(StateType.poison)) {
            continue
        }

        member.hitPoint -= 10

        if (member.hitPoint >= 0) {
            continue
        }

        member.hitPoint = 0
        member.addState(StateType.dead)
        member.removeState(StateType.poison)
    }
}

/**
 * 低凝集なコレクション処理
 * コレクションのデータと操作する処理が分離していると
 * 同じ処理が別の箇所で実装されてしまう可能性がある。
 * 保守性や可読性も悪くなってしまう
 */


const val MAX_MEMBER_COUNT = 4 // 上限数は適宜設定してください

// フィールドマップ上の制御を担当するクラス
class rpgMember(
    val id: Int,
    var hitPoint: Int
) {
    fun isAlive(): Boolean = hitPoint > 0
}


class FieldManager {

    // メンバーを追加する
    fun addMember(members: MutableList<rpgMember>, newMember: rpgMember) {
        if (members.any { it.id == newMember.id }) {
            throw RuntimeException("既にパーティに加わっています。")
        }
        if (members.size == MAX_MEMBER_COUNT) {
            throw RuntimeException("これ以上メンバーを追加できません。")
        }

        members.add(newMember)
    }

    // パーティメンバーが1人でも生存している場合 true を返す
    fun partyIsAlive(members: List<rpgMember>): Boolean {
        return members.any { it.isAlive() }
    }
}

/**
 * 改善案
 * コレクションをクラス内に配置したことで高凝集になった。
 * これでこの処理が別の場所で、実装されることはない
 */
class Party(
    // Listを受け取るようにしているが、静的メソッドで値を受け取ってListを生成するパターンもあり。下に書いてみた
    val members: List<rpgMember>
) {
    private val MAX_MEMBER_COUNT = 4

    // メンバーを追加する
    // Listをレスポンスすることで、クラスの中身が変更されることもない
    fun addMember(newMember: rpgMember): Party {
        if (members.any { it.id == newMember.id }) {
            throw RuntimeException("既にパーティに加わっています。")
        }
        if (members.size == MAX_MEMBER_COUNT) {
            throw RuntimeException("これ以上メンバーを追加できません。")
        }

        val newParty = members + newMember
        return Party(newParty)
    }

    // パーティメンバーが1人でも生存している場合 true を返す
    fun partyIsAlive(members: List<rpgMember>): Boolean {
        return members.any { it.isAlive() }
    }

    companion object {
        fun create(member: rpgMember): Party {
            return Party(listOf(member))
        }
    }
}

