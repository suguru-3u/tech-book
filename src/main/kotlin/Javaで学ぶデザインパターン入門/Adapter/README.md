# Adapterパターン (オブジェクトアダプター) の実装問題   

-----

## プログラミング問題: Adapterパターンを用いたレガシーシステムとの連携

### 状況設定

あなたは、新しい**通知システム**を開発しています。このシステムでは、すべての通知機能が以下の`Notifier`インターフェースを実装することを期待しています。

```java
// ターゲット (Target) インターフェース
public interface Notifier {
    /**
     * 新しいシステム向けの通知メソッド
     * @param message 送信するメッセージ
     */
    void send(String message);
}
```

しかし、あなたの会社には、古くから使われているレガシーな**ログ記録クラス**があります。このクラスは、通知機能として利用したいのですが、メソッドの命名規則やパラメータが新しい`Notifier`インターフェースとは異なっています。

```java
// アダプティ (Adaptee) クラス
public class LegacyLogger {
    /**
     * レガシーシステム向けのログ出力メソッド
     * @param logData ログとして記録するデータ
     */
    public void recordLog(String logData) {
        System.out.println("[LEGACY LOG] " + logData);
    }
}
```

### 問題

`LegacyLogger`クラスのコードを**変更することなく**、Adapterパターン（オブジェクトアダプター）を使用して、`LegacyLogger`オブジェクトを新しい通知システム（`Notifier`インターフェース）で使用できるようにしてください。

1.  `Notifier`インターフェースを実装する**アダプタークラス**（例：`LoggerAdapter`）を作成してください。
2.  このアダプタークラス内で、`LegacyLogger`オブジェクトを保持してください。
3.  アダプターの`send(String message)`メソッドが呼び出された際に、保持している`LegacyLogger`オブジェクトの`recordLog(String logData)`メソッドを呼び出すように実装してください。

### 検証コード

以下の`Application`クラスの`main`メソッドの`TODO`部分を完成させ、`LegacyLogger`のインスタンスを新しい通知システムで利用できるようにしてください。

```java
public class Application {
    /**
     * 新しい通知システムが利用するメソッド
     */
    public static void processNotification(Notifier notifier, String content) {
        System.out.println("--- New Notification System Start ---");
        notifier.send(content);
        System.out.println("--- New Notification System End ---");
    }

    public static void main(String[] args) {
        // 既存のレガシーオブジェクト
        LegacyLogger legacyLogger = new LegacyLogger();

        // TODO: Adapterパターンを用いてlegacyLoggerをNotifierとして利用できるようにする
        // Notifier modernNotifier = ...

        // processNotification(modernNotifier, "本日のシステムアップデートが完了しました。");
    }
}
```

### 求められる成果物

1.  `LoggerAdapter`クラスの完全な実装コード
2.  `Application.main`メソッドの`TODO`部分を完成させたコード