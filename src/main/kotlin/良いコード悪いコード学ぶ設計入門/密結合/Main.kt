package org.example.良いコード悪いコード学ぶ設計入門.密結合

/*メモ---------------------------------------------------------------------------------------------*/

/**
 * あるクラスが、ほかの多くのクラスに依存している構造を密結合と呼びます。
 * 密結合なコードは理解が難しく、変更が非常にやっかいです。
 * 結合度の低い、疎結合な構造へ改善すると、コードの変更が楽になります。
 * どう改善すればよいのか、考え方と方法をこの章で解説します。
 */

/**
 * このクラスに問題があるのか？？
 * このクラスに夏の特別セールを管理するSummerDiscountManagerが追加された。
 * 内容はDiscountManagerと同じ
 */

class DiscountManager {
    val discountProducts: MutableList<Product> = mutableListOf()
    var totalPrice: Int = 0

    /**
     * 商品を追加する
     *
     * @param product         商品
     * @param productDiscount 商品割引情報
     * @return 追加に成功した場合true
     */
    fun add(product: Product, productDiscount: ProductDiscount): Boolean {
        // Productクラスのロジックがあるような..
        if (product.id < 0) {
            throw IllegalArgumentException("Product id must be non-negative")
        }
        if (product.name.isEmpty()) {
            throw IllegalArgumentException("Product name cannot be empty")
        }
        if (product.price < 0) {
            throw IllegalArgumentException("Product price must be non-negative")
        }
        if (product.id != productDiscount.id) {
            throw IllegalArgumentException("Product id and discount id must match")
        }

        // ここはProductDiscountのロジックがある。。
        val discountPrice = getDiscountPrice(product.price)
        val tmp = if (productDiscount.canDiscount) {
            totalPrice + discountPrice
        } else {
            totalPrice + product.price
        }
        // マジックナンバーがあるぞ！
        return if (tmp <= 20000) {
            totalPrice = tmp
            discountProducts.add(product)
            true
        } else {
            false
        }
    }

    /**
     * 割引価格を取得する
     *
     * @param price 商品価格
     * @return 割引価格
     */
    // この関数はここにあるのが正しいのか？？
    companion object {
        fun getDiscountPrice(price: Int): Int {
            var discountPrice = price - 300
            if (discountPrice < 0) {
                discountPrice = 0
            }
            return discountPrice
        }
    }
}

// 商品
data class Product(
    val id: Int,         // 商品ID
    val name: String,    // 商品名
    val price: Int,       // 価格
    val canDiscount: Boolean // 夏季割引可能な場合true
)

// 商品割引情報
data class ProductDiscount(
    val id: Int,           // 商品ID
    val canDiscount: Boolean  // 割引可能な場合true
)

// 夏の割引クラスを追加
class SummerDiscountManager(private val discountManager: DiscountManager) {

    /**
     * 商品を追加する
     *
     * @param product 商品
     * @return 追加に成功した場合true
     */
    fun add(product: Product): Boolean {
        if (product.id < 0) {
            throw IllegalArgumentException("Product id must be non-negative")
        }
        if (product.name.isEmpty()) {
            throw IllegalArgumentException("Product name cannot be empty")
        }

        val tmp = if (product.canDiscount) {
            discountManager.totalPrice + DiscountManager.getDiscountPrice(product.price)
        } else {
            discountManager.totalPrice + product.price
        }

        return if (tmp < 30000) {
            discountManager.totalPrice = tmp
            discountManager.discountProducts.add(product)
            true
        } else {
            false
        }
    }
}


/**
 * どんな問題が起きるのか？
 * ・DiscountManagerクラスの変更によるSummerDiscountManagerへの影響
 * ・ロジックの置き場所がチグハく
 * ・単一責任の原則が守れていない
 */

// 改善してみる
// クラスが通常割引価格、夏季割引価格の責務ごとに個別に分かれています.
// そのため、割引価格の仕様がそれぞれ変更されても互いに影響はありません。
// このように関心事それぞれが分離、独立している構造を疎結合と呼びます。
// 密結合とは逆の構造です。疎結合な設計を目指しましょう。
@JvmInline
value class RegularPrice(val amount: Int) {
    init {
        require(amount >= 0) { "価格が0以上でありません。" }
    }
}

class RegularDiscountedPrice(price: RegularPrice) {
    companion object {
        private const val MIN_AMOUNT = 0
        private const val DISCOUNT_AMOUNT = 400
    }

    val amount: Int

    init {
        val discounted = price.amount - DISCOUNT_AMOUNT
        amount = if (discounted < MIN_AMOUNT) MIN_AMOUNT else discounted
    }
}

class SummerDiscountedPrice(price: RegularPrice) {
    companion object {
        private const val MIN_AMOUNT = 0
        private const val DISCOUNT_AMOUNT = 300
    }

    val amount: Int

    init {
        val discounted = price.amount - DISCOUNT_AMOUNT
        amount = if (discounted < MIN_AMOUNT) MIN_AMOUNT else discounted
    }
}


// 商品
data class ProductV2(
    val id: Int,         // 商品ID
    val name: String,    // 商品名
    val price: RegularPrice,       // 価格
) {
    companion object {
        const val MIN_PRICE: Int = 0
        const val MIN_ID: Int = 0

        fun create(id: Int, name: String, price: Int): ProductV2 {
            // Productクラスのロジックがあるような..
            if (id < MIN_ID) {
                throw IllegalArgumentException("Product id must be non-negative")
            }
            if (name.isEmpty()) {
                throw IllegalArgumentException("Product name cannot be empty")
            }
            if (price < MIN_PRICE) {
                throw IllegalArgumentException("Product price must be non-negative")
            }

            return ProductV2(
                id, name, RegularPrice(price),
            )
        }
    }
}

// 商品割引情報
data class ProductDiscountV2(
    val id: Int,           // 商品ID
    val canDiscount: Boolean  // 割引可能な場合true
) {
    fun canDiscount(): Boolean {
        return true
    }
}

class DiscountManagerV2 {
    val discountProducts: MutableList<Product> = mutableListOf()
    var totalPrice: Int = 0

    /**
     * 商品を追加する
     * 夏の割引を行う際は、addの型を変更したものを用意してあげればいい or interFaceの使用
     *
     * @return 追加に成功した場合true
     */
    fun add(
        product: Product,
        regularDiscountedPrice: RegularDiscountedPrice,
        productDiscountV2: ProductDiscountV2
    ): Boolean {
        val discountPrice = regularDiscountedPrice.amount
        val tmp = if (productDiscountV2.canDiscount) {
            totalPrice + discountPrice
        } else {
            totalPrice + product.price
        }
        // マジックナンバーがあるぞ！
        return if (tmp <= 20000) {
            totalPrice = tmp
            discountProducts.add(product)
            true
        } else {
            false
        }
    }
}

/**
 * 継承を行う際は、委譲を扱うようにする
 * 継承はクラス間の結合を高めてしまう。
 * 継承を使用する際は、値オブジェクトや委譲など使用して回避できないか設計すること
 */

/**
 * なんでもPublicにすると結合を強めてしまう
 * 同じパッケージないからのみアクセスできるようにする
 */

/**
 * 概念が異なる場合、クラスを分割すること
 */

/**
 * うまく単一責任の原則が守れていると一つのクラス200行前後に落ち着く
 */