package org.example.リーダブルコード.一度に一つのことを
/*問題---------------------------------------------------------------------------------------------*/

// 次の関数 `processUserInput` は、ユーザーの入力を読み取り、検証し、データベースに保存し、結果を表示するという複数の責任を持っています。
// この関数を「一度に一つのことを行う」ようにリファクタリングしてください。

fun processUserInput(input: String) {
    if (input.isBlank()) {
        println("入力が空です。")
        return
    }

    val trimmedInput = input.trim()
    val user = User(trimmedInput)

    Database.save(user)
    println("ユーザー「${user.name}」を保存しました。")
}

data class User(val name: String)

object Database {
    fun save(user: User) {
        // ユーザーをデータベースに保存する処理（ダミー）
        println("データベースに保存完了")
    }
}

/*回答---------------------------------------------------------------------------------------------*/

fun processUserInputV2(input: String) {
    if(validation(input)) return

    val user = createUser(input)
    saveUser(user)
    displayResult(user)
}

fun validation(input: String): Boolean {
    return if (input.isBlank()) {
        println("入力が空です。")
        true
    } else {
        false
    }
}

fun createUser(input: String): User {
    return User(input.trim())
}

fun saveUser(user: User) {
    Database.save(user)
}

fun displayResult(user: User) {
    println("ユーザー「${user.name}」を保存しました。")
}

class UserV2(name: String) {
    val name: String

    init {
        val trimmed = name.trim()
        if (trimmed.length < 3) {
            throw IllegalArgumentException("名前は3文字以上で入力してください。")
        }
        this.name = trimmed
    }
}

/*メモ---------------------------------------------------------------------------------------------*/

/**
 * 一度に複数のことをするコードは理解しにくい
 * いくつかのサンプルコードを見て、コードを一つづつタスクを行うようにして行こう
 */

fun voteChanged(oldVote: String, newVote: String) {
    var score = getScore()

    if (newVote != oldVote) {
        when (newVote) {
            "Up" -> {
                score += if (oldVote == "Down") 2 else 1
            }

            "Down" -> {
                score -= if (oldVote == "Up") 2 else 1
            }

            "" -> {
                score += if (oldVote == "Up") -1 else 1
            }
        }
    }

    setScore(score)
}

fun getScore(): Int {
    return 10
}

fun setScore(num: Int) {
    val score = num
}

fun selectScore(newVote: String, oldVote: String): Int {
    if (newVote == oldVote) return 0
    return when (newVote) {
        "Up" -> 2
        "Down" -> 2
        "" -> -1
        else -> 1
    }
}


fun voteChangedV2(oldVote: String, newVote: String) {
    var score = getScore()
    val voteScore = selectScore(newVote, oldVote)
    setScore(score + voteScore)
}