
-----

### MySQL `EXISTS`句 実践問題

#### 事前データ準備SQL

以下のSQLを実行し、必要なテーブルとデータを準備してください。

```sql
-- Projects テーブル
CREATE TABLE Projects (
    project_id VARCHAR(10),
    step_nbr INT,
    status VARCHAR(10)
);

INSERT INTO Projects (project_id, step_nbr, status) VALUES
('AA100', 0, '完了'),
('AA100', 1, '待機'),
('AA100', 2, '待機'),
('B200', 0, '待機'),
('B200', 1, '待機'),
('CS300', 0, '完了'),
('CS300', 1, '完了'),
('CS300', 2, '待機'),
('CS300', 3, '待機'),
('DY400', 0, '完了'),
('DY400', 1, '完了'),
('DY400', 2, '完了');

-- TestScores テーブル
CREATE TABLE TestScores (
    student_id INT,
    subject VARCHAR(20),
    score INT,
    PRIMARY KEY (student_id, subject)
);

INSERT INTO TestScores (student_id, subject, score) VALUES
(100, '算数', 100),
(100, '国語', 80),
(100, '理科', 80),
(200, '算数', 80),
(200, '国語', 95),
(300, '算数', 40),
(300, '国語', 90),
(300, '社会', 55),
(400, '算数', 80);

-- Meetings テーブル
CREATE TABLE Meetings (
    meeting_id VARCHAR(10),
    person VARCHAR(20),
    PRIMARY KEY (meeting_id, person)
);

INSERT INTO Meetings (meeting_id, person) VALUES
('第 1 回', '伊藤'),
('第 1 回', '水島'),
('第 1 回', '坂東'),
('第 2 回', '伊藤'),
('第 2 回', '宮田'),
('第 2 回', '坂東'),
('第 3 回', '伊藤'),
('第 3 回', '宮田'),
('第 3 回', '坂東'),
('第 3 回', '水島');

-- ArrayTbl テーブル
CREATE TABLE ArrayTbl (
    `key` VARCHAR(10),
    col1 INT,
    col2 INT,
    col3 INT,
    col4 INT,
    col5 INT,
    col6 INT,
    col7 INT,
    col8 INT,
    col9 INT,
    col10 INT
);

INSERT INTO ArrayTbl (`key`, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10) VALUES
('A', NULL, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('B', 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
('C', NULL, NULL, NULL, 9, NULL, NULL, NULL, NULL, NULL, NULL),
('D', 1, 9, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('E', 3, 1, 9, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- Tbl_A テーブル (EXISTSの基本動作確認用)
CREATE TABLE Tbl_A (
    name VARCHAR(20),
    sex VARCHAR(5),
    age INT
);

INSERT INTO Tbl_A (name, sex, age) VALUES
('田中', '女', 28),
('鈴木', '男', 21),
('山田', '男', 32);

-- Tbl_B テーブル (EXISTSの基本動作確認用)
CREATE TABLE Tbl_B (
    name VARCHAR(20),
    age INT
);

INSERT INTO Tbl_B (name, age) VALUES
('佐藤', 25),
('田中', 28),
('高橋', 30);
```

#### 問題

以下の各設問に対するSQLクエリを記述してください。

**問題 1: 未完了のプロジェクト**
`Projects`テーブルから、全てのステップが「完了」していないプロジェクトの`project_id`を抽出してください。つまり、一つでも「待機」のステータスを持つステップが存在するプロジェクトを特定してください。

**問題 2: 特定科目の点数が50点未満の学生**
`TestScores`テーブルから、いずれかの科目で50点未満の点数を取った学生の`student_id`を抽出してください。

**問題 3: 全ての会議に参加したことがない人物**
`Meetings`テーブルに名前が記載されている人物の中で、開催された全ての会議（`meeting_id`のユニークな値）に一度も参加していない人物の名前を抽出してください。

**問題 4: 全ての`col`がNULLではない`ArrayTbl`のキー**
`ArrayTbl`テーブルから、`col1`から`col10`までの全ての列が`NULL`ではない`key`を抽出してください。

**問題 5: `Tbl_A`に存在するが`Tbl_B`には存在しない名前を持つ人物**
`Tbl_A`テーブルには存在するが、`Tbl_B`テーブルには存在しない`name`を持つ人物を`Tbl_A`から抽出してください。

-----