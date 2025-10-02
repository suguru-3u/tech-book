package org.example.Javaで学ぶデザインパターン入門.Adapter

interface Notifier {
    /**
     * 新しいシステム向けの通知メソッド
     * @param message 送信するメッセージ
     */
    fun send(message: String?)
}

// アダプティ (Adaptee) クラス
class LegacyLogger {
    /**
     * レガシーシステム向けのログ出力メソッド
     * @param logData ログとして記録するデータ
     */
    fun recordLog(logData: String) {
        // レガシーなログ出力処理 (例: ファイルやコンソールへの出力)
        println("[LEGACY LOG] $logData")
    }
}

// アダプター (Adapter) クラス
class NotifierAdapter(
    private val legacyLogger: LegacyLogger
) : Notifier {
    /**
     * Notifierインターフェースのsendメソッドを実装し、LegacyLoggerのrecordLogメソッドを呼び出す
     */
    override fun send(message: String?) {
        // メッセージがnullの場合は空文字列に変換してログに記録
        val logData = message ?: ""
        legacyLogger.recordLog(logData)
    }
}

// クライアント (Client) クラス
class Application {
    /**
     * 新しい通知システムが利用するメソッド
     */
    fun processNotification(notifier: Notifier, content: String?) {
        println("--- New Notification System Start ---")
        notifier.send(content)
        println("--- New Notification System End ---")
    }
}


fun main() {
    // 既存のレガシーオブジェクト
    val legacyLogger = LegacyLogger()
    val app = Application()
    val modernNotifier = NotifierAdapter(legacyLogger)

    app.processNotification(modernNotifier, "本日のシステムアップデートが完了しました。")
}