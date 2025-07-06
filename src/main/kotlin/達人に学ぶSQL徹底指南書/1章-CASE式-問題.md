# 初期設定
都道府県の人口を地方名に読み替えて集計する

前提テーブル作成SQL:

-- PopTblテーブル作成
CREATE TABLE PopTbl (
pref_name VARCHAR(50),
population INTEGER
);

-- PopTblデータ挿入
INSERT INTO PopTbl (pref_name, population) VALUES
('徳島', 100),
('香川', 200),
('愛媛', 150),
('高知', 200),
('福岡', 300),
('佐賀', 100),
('長崎', 200),
('東京', 400),
('群馬', 50);

-- PopTbl2テーブル作成
CREATE TABLE PopTbl2 (
pref_name VARCHAR(50),
sex INTEGER,
population INTEGER
);

-- PopTbl2データ挿入
INSERT INTO PopTbl2 (pref_name, sex, population) VALUES
('徳島', 1, 60),
('徳島', 2, 40),
('香川', 1, 100),
('香川', 2, 100),
('愛媛', 1, 100),
('愛媛', 2, 50),
('高知', 1, 100),
('高知', 2, 100),
('福岡', 1, 200),
('福岡', 2, 300),
('佐賀', 1, 20),
('佐賀', 2, 80),
('長崎', 1, 125),
('長崎', 2, 125),
('東京', 1, 250),
('東京', 2, 150);

-- CourseMasterテーブル作成
CREATE TABLE CourseMaster (
course_id INTEGER PRIMARY KEY,
course_name VARCHAR(100)
);

-- CourseMasterデータ挿入
INSERT INTO CourseMaster (course_id, course_name) VALUES
(1, '経理入門'),
(2, '財務知識'),
(3, '簿記検定開講講座'),
(4, '税理士');

-- OpenCoursesテーブル作成
CREATE TABLE OpenCourses (
month INTEGER,
course_id INTEGER
);

-- OpenCoursesデータ挿入
INSERT INTO OpenCourses (month, course_id) VALUES
(201806, 1),
(201806, 3),
(201806, 4),
(201807, 4),
(201808, 2),
(201808, 4);

# No1
問題文:

PopTbl テーブルには、都道府県名（pref_name）と人口（population）が格納されています。<br>
このテーブルから、都道府県を以下の地方に分類し、それぞれの地方ごとの総人口を算出するSQLクエリを作成してください。

四国: 徳島、香川、愛媛、高知

九州: 福岡、佐賀、長崎

その他: 上記以外の都道府県

結果は「地方名」と「人口」の2列で表示し、地方名でグループ化してください。

# No2
問題文:

PopTbl テーブルのデータを用いて、都道府県の人口を以下の階級に分類し、それぞれの人口階級に属する都道府県の数をカウントするSQLクエリを作成してください。

01: 人口が 100 未満

02: 人口が 100 以上 200 未満

03: 人口が 200 以上 300 未満

04: 人口が 300 以上

結果は「人口階級」と「都道府県数」の2列で表示し、人口階級でグループ化してください。

# No3
問題文:

PopTbl2 テーブルには、都道府県名（pref_name）、性別（sex：1が男性、2が女性）、人口（population）が格納されています。<br>
このテーブルから、各都道府県の男性人口と女性人口を、それぞれ別の列として集計するSQLクエリを作成してください。

結果は「県名」、「男性人口」、「女性人口」の3列で表示し、県名でグループ化してください。

# No4
CourseMaster テーブルには講座の情報が、OpenCourses テーブルには各講座がどの月に開講されたかが格納されています。

この2つのテーブルを用いて、各講座が2018年6月、7月、8月に開講されたかどうかを示すクロス集計表を作成するSQLクエリを作成してください。開講されている場合は「○」、開講されていない場合は「×」と表示してください。

結果は「course_name」、「6月」、「7月」、「8月」の4列で表示してください。