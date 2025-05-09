package org.example.リーダブルコード.無関係の下位問題を抽出する

import kotlin.math.*
import kotlin.time.Duration.Companion.seconds

/*問題---------------------------------------------------------------------------------------------*/
/*
問題：
あなたは天気観測所のシステムを開発しています。
観測所は日ごとに観測された気温のリストを記録しています（単位は摂氏°C）。

以下の要件を満たす関数 `getHottestDay` を作成してください。

要件：
- 入力として「日付（文字列）」とその日の「気温リスト（List<Double>）」を複数日分受け取る。
  例：List<Pair<String, List<Double>>>（"2025-05-09", listOf(21.5, 25.3, 19.8) など）
- 各日の気温リストのうち、日中で最も暑かった時間帯（最高気温）を使って比較し、最も暑かった日を返す。
- 戻り値は「最も暑かった日付（String）」

制約：
- 各日の気温リストは1つ以上の値を含む
- 入力リストは空でないものとする
*/

fun getHottestDay(data: List<Pair<String, List<Double>>>): String {
    // 実装する
    return ""
}

/*回答---------------------------------------------------------------------------------------------*/

fun getHottestDayV2(data: List<Pair<String, List<Double>>>): String {
    var maxTodayIndex = 0
    var maxTodayTemperature = 0.0
    for((index, value) in data.withIndex()){
        val todayMax = findTodayMax(value.second)
        if(maxTodayTemperature < todayMax){
            maxTodayIndex = index
            maxTodayTemperature = todayMax
        }
    }
    return data[maxTodayIndex].first
}

fun getHottestDayV3(data: List<Pair<String, List<Double>>>): String {
    return data.maxByOrNull { findTodayMax(it.second) }!!.first
}

fun findTodayMax(temperatures : List<Double>): Double {
    return temperatures.max()
}

/*メモ---------------------------------------------------------------------------------------------*/

/**
 * 無関係の下位問題を積極的にに見つけて抽出すること
 *
 * 1. 関数やコードブロックを見て「高レベルの目標は何か」自問する
 * 2. コードの各行に対して「高レベルの目標に直接的に効果があるのか？あるいは無関係の下位問題を解決しているのか？」自問する
 * 3. 無関係の下位問題を解決しているコードが相当量ある場合、それらを抽出して別の関数にする
 *
 * ・汎用性が高い関数はutilクラスを作成して、そこで管理するようにするのがいい
 * ※ただし命名規則に気をつけないとutilクラスが肥大化することになる
 *
 * ・使い勝手がよくない関数は、ラップしてしまって使いやすいように改造するのがいい
 */


data class Location(val latitude: Double, val longitude: Double)

// 改善前のコード
// 高レベル = 関数の本来の目的
fun findClosetLocation(lat: Double, lng: Double, array: List<Location>): Location? {
    var closet: Location? = null
    var closestDist = Double.MAX_VALUE

    // 関数の目標に直積付きな効果はない
    for (i in array.indices) {
        val latRad = Math.toRadians(lat)
        val lngRad = Math.toRadians(lng)
        val lat2Rad = Math.toRadians(array[i].latitude)
        val lng2Rad = Math.toRadians(array[i].longitude)

        val dist = acos(
            sin(latRad) * sin(lat2Rad) +
                    cos(latRad) * cos(lat2Rad) * cos(lng2Rad - lngRad)
        )

        if (dist < closestDist) {
            closet = array[i]
            closestDist = dist
        }
    }

    return closet
}

// 改善後
fun findClosetLocationV2(lat: Double, lng: Double, array: List<Location>): Location? {
    var closet: Location? = null
    var closestDist = Double.MAX_VALUE

    // 下位レベルの問題は別の関数に抽出する対応を撮とった
    for (i in array.indices) {
        val dist = sphericalDistance(lat, lng, array, i)
        if (dist < closestDist) {
            closet = array[i]
            closestDist = dist
        }
    }

    return closet
}

fun sphericalDistance(lat: Double, lng: Double, array: List<Location>, i: Int): Double {
    val latRad = Math.toRadians(lat)
    val lngRad = Math.toRadians(lng)
    val lat2Rad = Math.toRadians(array[i].latitude)
    val lng2Rad = Math.toRadians(array[i].longitude)

    return acos(
        sin(latRad) * sin(lat2Rad) +
                cos(latRad) * cos(lat2Rad) * cos(lng2Rad - lngRad)
    )
}