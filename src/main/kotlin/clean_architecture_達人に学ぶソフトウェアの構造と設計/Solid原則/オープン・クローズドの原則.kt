package org.example.clean_architecture_達人に学ぶソフトウェアの構造と設計.Solid原則

/**
 * SOLID原則：オープン/クローズドの原則 (OCP) の問題と解答
 *
 * このファイルは、オープン/クローズドの原則に違反しているコードと、
 * それを原則に従ってリファクタリングしたコードを示しています。
 *
 * 問題：
 * 以下の`AreaCalculator`クラスは、新しい図形を追加するたびに、
 * `calculateArea`メソッドを変更しなければなりません。
 * これは「拡張に対しては開かれているが、変更に対しては閉じている」という
 * オープン/クローズドの原則に違反しています。
 */

// --- 違反しているコード例 ---
class Rectangle(val width: Double, val height: Double)

class Circle(val radius: Double)

class AreaCalculator {
    fun calculateArea(shape: Any): Double {
        if (shape is Rectangle) {
            return shape.width * shape.height
        } else if (shape is Circle) {
            return Math.PI * shape.radius * shape.radius
        }
        throw IllegalArgumentException("無効な図形です")
    }
}

/**
 * 解答：
 * ポリモーフィズムを利用して、各図形が自身の面積計算の責任を持つように分離します。
 * `Shape`インターフェースを定義し、各図形クラスがそれを実装することで、
 * `AreaCalculator`は新しい図形が追加されても変更する必要がなくなります。
 * 新しい図形（例：`Triangle`）は、`Shape`インターフェースを実装するだけでよくなります。
 */

// --- 原則に準拠した改善コード例 ---
interface Shape {
    fun area(): Double
}

class RefactoredRectangle(val width: Double, val height: Double) : Shape {
    override fun area(): Double {
        return width * height
    }
}

class RefactoredCircle(val radius: Double) : Shape {
    override fun area(): Double {
        return Math.PI * radius * radius
    }
}

class Triangle(val base: Double, val height: Double) : Shape {
    override fun area(): Double {
        return 0.5 * base * height
    }
}

class RefactoredAreaCalculator {
    fun calculateArea(shape: Shape): Double {
        // 新しい図形が追加されても、このメソッドは変更する必要がない
        return shape.area()
    }
}

// 実行例
fun main() {
    println("--- 違反しているコードの実行 ---")
    val oldCalculator = AreaCalculator()
    println("長方形の面積: ${oldCalculator.calculateArea(Rectangle(10.0, 5.0))}")
    println("円の面積: ${oldCalculator.calculateArea(Circle(5.0))}")
    // 新しい図形を追加する場合、AreaCalculatorクラスを変更する必要がある

    println("\n--- 原則に準拠したコードの実行 ---")
    val newCalculator = RefactoredAreaCalculator()
    val rect: Shape = RefactoredRectangle(10.0, 5.0)
    val circle: Shape = RefactoredCircle(5.0)
    val triangle: Shape = Triangle(10.0, 5.0)

    println("長方形の面積: ${newCalculator.calculateArea(rect)}")
    println("円の面積: ${newCalculator.calculateArea(circle)}")
    // 新しい図形（三角形）を追加しても、AreaCalculatorは変更不要
    println("三角形の面積: ${newCalculator.calculateArea(triangle)}")
}