package org.example.リーダブルコード.`7章-制御フロー`


/*問題---------------------------------------------------------------------------------------------*/
// 条件分岐と制御フローの可読性を改善するリファクタリングを行いましょう。
// 以下の関数には、読みやすさに欠ける条件式の記述やネストの深い構造が含まれています。
// - 条件文の書き方（肯定形の使用、関心のある条件を先に書く）
// - ネストを浅くする早期リターンの活用
// - ループと条件の整理
// - 条件文の位置の工夫
// これらを考慮し、コード全体の可読性を高めるようリファクタリングしてください。

fun checkUrlBehavior() {
    val url = Url()

    // 条件がわかりにくい・ネストが深い
    if (url.parameter != "expand_all") {
        for (i in 1..100) {
            println("縮小表示: $i")
        }
    } else {
        println("すべて展開されています")
    }

    // 上と同じ意味だが、どちらが読みやすいか？
    if ("expand_all" == url.parameter) {
        println("すべて展開されています")
    } else {
        for (i in 1..100) {
            println("縮小表示: $i")
        }
    }

    // 条件の否定系
    if (!url.parameter.isEmpty()) {
        println("パラメータあり")
    } else {
        println("パラメータなし")
    }

    // ネストの深い処理
    if (url.parameter != "") {
        if (url.parameter == "expand_all") {
            println("展開モード")
        } else {
            println("通常モード")
        }
    }

    // 不要なdo-while
    var count = 0
    do {
        println("カウント: $count")
        count++
    } while (count < 3)
}

class Url {
    val parameter: String = "expand_all"
}

/*問題---------------------------------------------------------------------------------------------*/
// 複雑な条件とネストされた構造を読みやすくリファクタリングしてください。
// - 関数 `processRequest` は複数の条件分岐、ネスト、否定条件を含んでいます。
// - この関数を、より読みやすく、分かりやすくなるようにリファクタリングしてください。
// - 具体的には以下を意識してください:
//   - 否定条件を肯定形に変える
//   - ネストを浅くする（早期リターンの活用）
//   - ネストされたループや条件分岐を整理する
//   - 処理の意図が明確に読み取れるようにする
//
// 注意: 処理のロジック自体は変更しないでください。

fun processRequest(request: Request?) {
    if (request != null) {
        if (!request.isAuthorized) {
            println("Unauthorized access")
        } else {
            if (request.params.isEmpty()) {
                println("No parameters provided")
            } else {
                for (param in request.params) {
                    if (!param.isValid()) {
                        println("Invalid parameter: ${param.name}")
                    } else {
                        if (param.name == "special") {
                            println("Processing special parameter")
                            continue
                        }
                        println("Processing parameter: ${param.name}")
                    }
                }
            }
        }
    } else {
        println("Request is null")
    }
}

// 想定されるデータクラス（修正不要）
data class Request(val isAuthorized: Boolean, val params: List<Param>)
data class Param(val name: String) {
    fun isValid(): Boolean = name.isNotBlank()
}

/*問題---------------------------------------------------------------------------------------------*/
// 分岐ロジックと状態処理を明確に分離して読みやすくしてください。
// - 関数 `handleSubmission` は複数のフラグと状態に依存した分岐を持ち、処理が追いにくくなっています。
// - この関数を、より読みやすく、意図が明確に伝わるようにリファクタリングしてください。
//
// 具体的なポイント:
//   - 状態フラグ（isSubmitted, isApproved, isArchived）を使った条件分岐の整理
//   - 否定条件の肯定化（e.g., !isSubmitted → isNotSubmitted）
//   - 早期リターンを活用してネストを浅くする
//   - 処理のまとまり（「アーカイブ済みなら何もしない」など）を明示的に表現する
//
// 注意: 処理の意味・順番は変えず、ロジックは保持してください。

fun handleSubmission(doc: Document?) {
    if (doc != null) {
        if (!doc.isSubmitted) {
            println("Document has not been submitted yet.")
        } else {
            if (doc.isArchived) {
                println("Document is archived and cannot be processed.")
            } else {
                if (doc.isApproved) {
                    println("Document already approved. Archiving now...")
                    doc.archive()
                } else {
                    println("Approving document...")
                    doc.approve()
                }
            }
        }
    } else {
        println("Document is null.")
    }
}

// 想定されるデータクラス（修正不要）
class Document(
    val isSubmitted: Boolean,
    val isApproved: Boolean,
    val isArchived: Boolean
) {
    fun approve() = println("Approved!")
    fun archive() = println("Archived!")
}

/*問題---------------------------------------------------------------------------------------------*/
// 複雑な状態遷移と重複した処理を読みやすくリファクタリングしてください。
// - 関数 `processWorkflow` はドキュメントの状態に応じて処理を行います。
// - 状態管理が複雑で、重複した出力や処理が散らばっています。
// - 以下の点を意識して、関数をリファクタリングしてください:
//   - 重複した処理の抽出
//   - ネストの削減と早期リターン
//   - 状態ごとの責務を明確にし、処理の意図が読み取りやすくなるようにする
//   - 可能であれば関数の抽出や分割も検討する
//
// 注意: 処理のロジック自体は変更しないこと。

fun processWorkflow(doc: Document2?) {
    if (doc == null) {
        println("Error: Document is null.")
        return
    }

    if (!doc.isValid()) {
        println("Error: Invalid document.")
        return
    }

    if (doc.isArchived) {
        println("Cannot process archived document.")
        return
    }

    if (!doc.isSubmitted) {
        println("Document not submitted. Submitting now...")
        doc.submit()
    }

    if (!doc.isReviewed) {
        println("Reviewing document...")
        doc.review()
        println("Document reviewed.")
    } else {
        println("Already reviewed.")
    }

    if (!doc.isApproved) {
        println("Approving document...")
        doc.approve()
        println("Document approved.")
    } else {
        println("Already approved.")
    }

    if (!doc.isArchived) {
        println("Archiving document...")
        doc.archive()
        println("Document archived.")
    } else {
        println("Document already archived.")
    }
}

// 想定されるデータクラス（修正不要）
class Document2(
    var isSubmitted: Boolean = false,
    var isReviewed: Boolean = false,
    var isApproved: Boolean = false,
    var isArchived: Boolean = false
) {
    fun isValid(): Boolean = true
    fun submit() {
        isSubmitted = true
    }

    fun review() {
        isReviewed = true
    }

    fun approve() {
        isApproved = true
    }

    fun archive() {
        isArchived = true
    }
}


/*回答---------------------------------------------------------------------------------------------*/

fun checkUrlBehaviorAnswer() {
    val url = Url3()

    // 条件がわかりにくい・ネストが深い
    if (url.parameter == "expand_all") {
        println("すべて展開されています")
    } else {
        for (i in 1..100) {
            println("縮小表示: $i")
        }
    }

    // 上と同じ意味だが、どちらが読みやすいか？
    if (url.parameter == "expand_all") {
        println("すべて展開されています")
    } else {
        for (i in 1..100) {
            println("縮小表示: $i")
        }
    }

    // 条件の否定系
    if (url.parameter.isEmpty()) {
        println("パラメータなし")
    } else {
        println("パラメータあり")
    }

    // ネストの深い処理
    if (url.parameter == "") return
    if (url.parameter == "expand_all") {
        println("展開モード")
    } else {
        println("通常モード")
    }

    // 不要なdo-while
    for (count in 0..2) {
        println("カウント: $count")
    }
}

class Url3 {
    val parameter: String = "expand_all"
}

fun processRequestAnswer(request: Request?) {
    if (request == null) {
        println("Request is null")
        return
    }

    if (request.isAuthorized.not()) {
        println("Unauthorized access")
        return
    }

    if (request.params.isEmpty()) {
        println("No parameters provided")
        return
    }

    for (param in request.params) {
        if (param.isValid().not()) {
            println("Invalid parameter: ${param.name}")
            continue
        }

        if (param.name == "special") {
            println("Processing special parameter")
            continue
        }
        println("Processing parameter: ${param.name}")
    }

    val results = request.params
        .map { param ->
            when {
                param.isValid().not() -> "Invalid parameter: ${param.name}"
                param.name == "special" -> "Processing special parameter"
                else -> "Processing parameter: ${param.name}"
            }
        }

    results.forEach { println(it) }
}

fun handleSubmissionAnswer(doc: Document?) {
    if (doc == null) {
        println("Document is null.")
        return
    }

    if (doc.isSubmitted.not()) {
        println("Document has not been submitted yet.")
        return
    }
    if (doc.isArchived) {
        println("Document is archived and cannot be processed.")
        return
    }
    if (doc.isApproved) {
        println("Document already approved. Archiving now...")
        doc.archive()
        return
    }

    println("Approving document...")
    doc.approve()
}

fun processWorkflowAnswer(doc: Document2?) {
    if (doc == null) {
        println("Error: Document is null.")
        return
    }

    if (doc.isValid().not()) {
        println("Error: Invalid document.")
        return
    }

    if (doc.isArchived) {
        println("Cannot process archived document.")
        return
    }

    fun processStep(
        condition: Boolean,
        firstDoingMsg: String,
        secondDoingMsg: String,
        doneMsg: String,
        action: () -> Unit
    ) {
        if (condition) {
            println(firstDoingMsg)
            action()
            println(secondDoingMsg)
        } else {
            println(doneMsg)
        }
    }

    if (doc.isSubmitted.not()) {
        println("Document not submitted. Submitting now...")
        doc.submit()
    }

    processStep(
        condition = doc.isReviewed,
        firstDoingMsg = "Reviewing document...",
        secondDoingMsg = "Document reviewed.",
        doneMsg = "Already reviewed.",
        action = { doc.review() }
    )


    if (doc.isApproved.not()) {
        println("Approving document...")
        doc.approve()
        println("Document approved.")
    } else {
        println("Already approved.")
    }

    if (doc.isArchived.not()) {
        println("Archiving document...")
        doc.archive()
        println("Document archived.")
    } else {
        println("Document already archived.")
    }
}

/*メモ---------------------------------------------------------------------------------------------*/

/**
 * 条件文の位置
 *
 * QA: どっちの条件式が読みやすい？
 */
fun sample() {
    val length = 10
    // こっちの方が読みやすい
    if (length > 10) {
        println("Hello")
    }

    if (10 < length) {
        println("Hello")
    }
}

/**
 * 条件は否定系よりも肯定形を使う
 *
 * 単純な条件を先に書く、見やすい
 *
 * 関心を引く条件や目立つ条件を先に書く
 *
 * ※上記の優劣は衝突することがあるので、その時に自己判断が必要
 */

class Url2 {
    val parameter: String = "expand_all"
}

fun sample2() {
    if (Url2().parameter != "expand_all") {
        for (num in 1..100) {
            TODO()
        }
    } else {
        TODO()
    }

    if (Url2().parameter == "expand_all") {
        TODO()
    } else {
        for (num in 1..100) {
            TODO()
        }
    }
}

/**
 *  三項演算子は他の人が理解するのに時間がかからないのであれば、実装してもいい
 *  if文を使用した方が理解しやすいケースがある
 */

/**
 * do-while文は条件が下に記述することになるので、読みづらいとされている。
 * 基本的には使用しない方がいいと思う。
 */

/**
 * 関数から早く返す、早期リターンを活用する
 * 下記の例だと、一番最初に条件分を使用した方が可読性が高くなる
 * また早期リターンはネストを浅くすることもできる
 * ループ内のネストもcontinueやbreakを使用して、解消することができる
 */
fun sample3() {
    TODO()
    TODO()
    TODO()
    TODO()
    TODO()
    TODO()
    if (Url2().parameter.isEmpty()) return
}
