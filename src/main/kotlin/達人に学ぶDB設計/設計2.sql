--DB設計問題
--
--
--📌サービス概要
--	•	利用者が自転車をスマートフォンアプリで予約・利用・返却できるサービスです。
--	•	都市内の複数のステーションに自転車が配備されており、どのステーションでも借りたり返したりできます。
--	•	利用者はアプリに登録し、ログインしてサービスを利用します。
--	•	利用料金は時間単位で課金され、料金プランによって異なります。
--
--⸻
--
--usersテーブル
--ID、名前、電話番号、メールアドレス、パスワード
--プランコード(外部キー)
--
--plans
--プランコード、プラン名、料金、作成日、更新日
--
--bicycles
--識別番号、ステーションID(外部キー)、ステータス
--
--stations
--ID、名称、住所、最大補完数
--
--usagehistories
--ID、ユーザーID(外部キー)、自転車ID(外部キー)、貸出日時、返却日時、貸出したステーションID(外部キー)
--
--
--📋ビジネス要件
--	1.	ユーザー管理
--	•	利用者は名前、電話番号、メールアドレスを登録してアカウントを作成する。
--	•	ログインのためのパスワードも必要。
--	•	一部の利用者は月額プランに加入している。
--	2.	自転車管理
--	•	各自転車には一意の識別番号があり、現在のステーションまたは移動中の状態を持つ。
--	•	故障中やメンテナンス中の自転車も存在する。
--	3.	ステーション管理
--	•	ステーションは都市内の特定の場所にあり、名称・住所・最大保管台数が設定されている。
--	4.	利用履歴の記録
--	•	ユーザーがどの自転車をどのステーションで借り、どこで返却したかを記録する。
--	•	借用時刻・返却時刻も記録し、利用時間を算出可能にする。
--	•	料金の計算も必要（ただし、ここでは計算方法までは指定しない）。
--	5.	料金プラン
--	•	料金プランには「従量課金」「月額プラン」があり、それぞれで料金設定が異なる。
--	•	ユーザーは1つの料金プランに加入できる。

CREATE TABLE users(
    id INT AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    email VARCHAR(300) NOT NULL,
    password VARCHAR(50) NOT NULL,
    plan_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (plan_id) REFERENCES plans(plan_code),
    PRIMARY KEY(id)
)

CREATE TABLE plans(
    plan_code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    price SMALLINT unsigned NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(plan_code)
)

CREATE TABLE bicycles(
    bicycle_number INT unsigned NOT NULL,
    station_id INT NOT NULL,
    status ENUM('available', 'in_use', 'maintenance', 'broken') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(bicycle_number),
    FOREIGN KEY (station_id) REFERENCES stations(id),
)

CREATE TABLE stations(
    id INT AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    latitude VARCHAR(250) NOT NULL,
    longitude VARCHAR(250) NOT NULL,
    max_number_storage INT unsigned NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
)

CREATE TABLE usagehistories(
    id INT AUTO_INCREMENT,
    user_id INT NOT NULL,
    bicycle_id INT NOT NULL,
    lend_date DATETIME NOT NULL,
    return_date DATETIME,
    lend_station_id INT NOT NULL,
    return_station_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (bicycle_id) REFERENCES bicycles(bicycle_number),
    FOREIGN KEY (lend_station_id) REFERENCES stations(id),
    FOREIGN KEY (return_station_id) REFERENCES stations(id),
    PRIMARY KEY(id)
)


--さらに洗練させたい場合は、「料金計算の履歴」「返却遅延ペナルティ」「自転車移動履歴」などもテーブル化できますが、今回は基本設計としてはとても良いスタートです！
--ご希望があれば、ER図やINSERT例、クエリ作成練習問題も出せますよ！