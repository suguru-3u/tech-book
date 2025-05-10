package org.example.良いコード悪いコード学ぶ設計入門.設計の初歩

/*問題---------------------------------------------------------------------------------------------*/
/**
 * メモに記載されているプログラムをうまくクラス化させる
 */

/*回答---------------------------------------------------------------------------------------------*/

class Player(private val playerArmPower: Int, private val playerWeaponPower: Int) {
    private val minPowerPoint = 0
    private val maxPowerPoint = 1000

    init {
        check(playerArmPower in minPowerPoint..maxPowerPoint) {
            "playerArmPowerに不正な値が混入されています"
        }
        check(playerWeaponPower in minPowerPoint..maxPowerPoint) {
            "playerWeaponPowerに不正な値が混入されています"
        }
    }

    fun attackAmount(): Int {
        return playerArmPower + playerWeaponPower
    }
}

class Enemy(private val enemyBodyDefence: Int, private val enemyArmorDefence: Int) {
    private var hp = 300
    private val minDefencePoint = 0
    private val maxDefencePoint = 1000

    init {
        check(enemyBodyDefence in minDefencePoint..maxDefencePoint) {
            "enemyBodyDefenceに不正な値が混入されています"
        }
        check(enemyArmorDefence in minDefencePoint..maxDefencePoint) {
            "enemyArmorDefenceに不正な値が混入されています"
        }
    }

    private fun defenceAmount(): Int {
        return enemyBodyDefence + enemyArmorDefence
    }

    fun damage(player: Player) {
        val minAttackPoint = 0
        val damageTotal = player.attackAmount() - defenceAmount()
        if (minAttackPoint < damageTotal) {
            hp -= damageTotal
        }
    }
}

fun mainV2() {
    val player = Player(
        playerArmPower = 100,
        playerWeaponPower = 50
    )

    val enemy = Enemy(
        enemyBodyDefence = 30,
        enemyArmorDefence = 20
    )

    enemy.damage(player)
}

/*メモ---------------------------------------------------------------------------------------------*/

/**
 * 設計の初歩
 * ・省略せずに意図が伝わる名前を設計する
 * ・変数を使いまわさない
 * ・意味のあるまとまりでメソッド化する
 */

fun main() {
    val playerArmPower = 100
    val playerWeaponPower = 50

    val enemyBodyDefence = 30
    val enemyArmorDefence = 20

    var damageAmount = 0
    damageAmount = playerArmPower + playerWeaponPower
    damageAmount = damageAmount - ((enemyBodyDefence + enemyArmorDefence) / 2);
    if (damageAmount < 0) {
        damageAmount = 0;
    }
}

