-- 問題：
-- あなたはオンラインストアのデータベースを設計しています。
-- 顧客（customers）を管理するテーブルを作成してください。
-- 以下の条件を満たすように、CREATE TABLE文を記述しなさい。
--
-- 条件：
-- ・顧客ID（id）：整数型、自動採番、主キー
-- ・顧客名（name）：50文字以内の文字列、NULLを許可しない
-- ・メールアドレス（email）：100文字以内の文字列、一意でNULLを許可しない
-- ・登録日（created_at）：日付型、NULLを許可しない

-- ここにCREATE TABLE文を記述してください

CREATE TABLE　customers(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at DATE NOT NULL DEFAULT CURRENT_DATE
)