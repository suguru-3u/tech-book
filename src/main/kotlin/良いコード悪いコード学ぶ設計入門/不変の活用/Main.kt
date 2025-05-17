package org.example.良いコード悪いコード学ぶ設計入門.不変の活用


/**
 * 不変について
 * ・再代入しない
 * ・可能であれば、引数も不変にする
 * ・関数内で副作用を発生させない
 *      ・インスタンス変数
 *      ・グローバル変数
 *      ・引数の変更
 *      ・ファイルの読み書き
 *
 *  ・可変にして良い時
 *      ・パフォーマンスに影響がある場合
 *      ・局所的に使用する場合、ループ処理の間
 */

class HitPoint {
    var amount: Int = 0
}

class Member(
    val hitPoint: HitPoint,
) {
    /**
     * ダメージを受ける
     * @param damageAmount ダメージ量
     */
    fun damage(damageAmount: Int) {
        hitPoint.amount -= damageAmount
    }
}

class HitPointV2(val amount: Int) {
    val minHP = 0

    init {
        check(minHP > amount) { "HPに不正な値がリクエストされました" }
    }

    fun damage(damage: HitPointV2): HitPointV2 {
        val damagePoint = amount - damage.amount
        return if (minHP > damagePoint) {
            HitPointV2(minHP)
        } else {
            HitPointV2(damagePoint)
        }
    }

}

class MemberV2(
    val hitPoint: HitPointV2,
) {
    /**
     * ダメージを受ける
     * @param damageAmount ダメージ量
     */
    fun damage(damageAmount: Int) {
        val damagePoint = HitPointV2(damageAmount)
        hitPoint.damage(damagePoint)
    }
}


