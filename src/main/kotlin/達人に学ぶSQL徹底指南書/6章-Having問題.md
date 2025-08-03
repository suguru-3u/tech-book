
## 事前準備
- No1

CREATE TABLE Students (
student_id INT PRIMARY KEY,
dpt VARCHAR(255),
sbmt_date DATE
);

INSERT INTO Students (student_id, dpt, sbmt_date) VALUES
(100, '理学部', '2018-10-10'),
(101, '理学部', '2018-09-22'),
(102, '文学部', NULL),
(103, '文学部', '2018-09-10'),
(200, '文学部', '2018-09-22'),
(201, '工学部', NULL),
(202, '経済学部', '2018-09-25');

CREATE TABLE TestResults (
student_id INT,
class VARCHAR(1),
sex VARCHAR(1),
score INT
);

INSERT INTO TestResults (student_id, class, sex, score) VALUES
(001, 'A', '男', 100),
(002, 'A', '女', 100),
(003, 'A', '女', 49),
(004, 'A', '男', 30),
(005, 'B', '男', 100),
(006, 'B', '男', 92),
(007, 'B', '男', 80),
(008, 'B', '男', 80),
(009, 'B', '女', 10),
(010, 'C', '男', 92),
(011, 'C', '男', 80),
(012, 'C', '女', 21),
(013, 'D', '女', 100),
(014, 'D', '男', 0),
(015, 'D', '女', 0);

- No2

CREATE TABLE Teams (
member VARCHAR(255),
team_id INT,
status VARCHAR(255)
);

INSERT INTO Teams (member, team_id, status) VALUES
('ジョー', 1, '待機'),
('ケン', 1, '出動中'),
('ミック', 1, '待機'),
('カレン', 2, '出動中'),
('キース', 2, '休暇'),
('ジャン', 3, '待機'),
('ハート', 3, '待機'),
('ディック', 3, '待機'),
('ベス', 4, '待機'),
('アレン', 5, '出動中'),
('ロバート', 5, '休暇'),
('ケーガン', 5, '待機');

CREATE TABLE ShopItems (
shop VARCHAR(255),
item VARCHAR(255)
);

INSERT INTO ShopItems (shop, item) VALUES
('仙台', 'ビール'),
('仙台', '紙オムツ'),
('仙台', '自転車'),
('仙台', 'カーテン'),
('東京', 'ビール'),
('東京', '紙オムツ'),
('東京', '自転車'),
('大阪', 'テレビ'),
('大阪', '紙オムツ'),
('大阪', '自転車');

### 問題1
`Students`テーブルと`TestResults`テーブルを使用して、以下の問いに答えるSQLクエリを作成してください。

1.  **提出日がすべて埋まっている学部を抽出してください。**
2.  **男子の平均点が女子の平均点よりも高いクラスを抽出してください。ただし、どちらかの性別の学生がいないクラスは除外してください。**

***

### 問題2
`Teams`テーブルと`ShopItems`テーブルを使用して、以下の問いに答えるSQLクエリを作成してください。

1.  **全員が「待機」状態であるチームの`team_id`を抽出してください。**
2.  **`ShopItems`テーブル内で、販売している商品の種類数が最も多い店舗を抽出してください。**