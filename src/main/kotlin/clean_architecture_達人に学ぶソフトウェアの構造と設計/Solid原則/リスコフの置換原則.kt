package org.example.clean_architecture_達人に学ぶソフトウェアの構造と設計.Solid原則

/**
 * SOLID原則：リスコフの置換原則 (LSP) の問題と解答
 *
 * このファイルは、リスコフの置換原則に違反しているコードと、
 * それを原則に従ってリファクタリングしたコードを示しています。
 *
 * 問題：
 * 以下の `Square` クラスは `Rectangle` を継承していますが、`setWidth` や `setHeight`
 * メソッドの振る舞いを変更し、両辺を常に同じ値にしています。
 * その結果、`Square`オブジェクトを`Rectangle`として扱った場合に、
 * 親クラスの期待する動作（幅と高さが独立していること）が破綻するため、
 * リスコフの置換原則に違反しています。
 */

// --- 違反しているコード例 ---
open class Rectangle(var width: Int, var height: Int) {
    open fun setWidth(width: Int) {
        this.width = width
    }

    open fun setHeight(height: Int) {
        this.height = height
    }

    fun getArea(): Int = width * height
}

class Square(size: Int) : Rectangle(size, size) {
    override fun setWidth(width: Int) {
        this.width = width
        this.height = width // 振る舞いを変更
    }

    override fun setHeight(height: Int) {
        this.width = height // 振る舞いを変更
        this.height = height
    }
}

fun useRectangle(rect: Rectangle) {
    // クライアントはrectの幅と高さを独立して設定できると期待している
    rect.setWidth(5)
    rect.setHeight(10)
    println("期待される面積: 50, 実際の面積: ${rect.getArea()}")
}

/**
 * 解答：
 * リスコフの置換原則に従うためには、不適切な継承関係を解消します。
 * このケースでは、`Rectangle`と`Square`を共通のインターフェース`Shape`を実装する
 * 独立したクラスとして再設計するのが最も適切です。
 * これにより、`Square`は`Rectangle`の振る舞いを継承する必要がなくなり、
 * 自身の正しい振る舞い（辺が常に同じ長さであること）を維持できます。
 */

// --- 原則に準拠した改善コード例 ---
interface ShapeWithArea {
    fun getArea(): Int
}

class RefactoredRectangle(var width: Int, var height: Int) : ShapeWithArea {
    override fun getArea(): Int = width * height
}

class RefactoredSquare(var size: Int) : ShapeWithArea {
    override fun getArea(): Int = size * size
}

fun useShape(shape: ShapeWithArea) {
    // クライアントはgetArea()メソッドが適切に機能するとだけ期待する
    println("図形の面積: ${shape.getArea()}")
}

// 実行例
fun main() {
    println("--- 違反しているコードの実行 ---")
    val rect = Rectangle(2, 2)
    useRectangle(rect) // 期待通り 50 が出力される

    val square = Square(2)
    useRectangle(square) // 期待は 50 だが、実際は 10 * 10 で 100 になる -> LSP違反

    println("\n--- 原則に準拠したコードの実行 ---")
    val refactoredRect = RefactoredRectangle(5, 10)
    val refactoredSquare = RefactoredSquare(5)

    useShape(refactoredRect) // 面積 50
    useShape(refactoredSquare) // 面積 25
}