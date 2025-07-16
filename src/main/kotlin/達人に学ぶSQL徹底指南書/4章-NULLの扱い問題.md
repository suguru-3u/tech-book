# 初期設定

-- 問題1: Employees テーブルの作成とデータ挿入
CREATE TABLE Employees (
id INT PRIMARY KEY,
name VARCHAR(50) NOT NULL,
age INT
);

INSERT INTO Employees (id, name, age) VALUES
(1, 'Alice', 30),
(2, 'Bob', NULL),
(3, 'Carol', 25),
(4, 'David', NULL),
(5, 'Eve', 35);

-- 問題2: Projects テーブルの作成とデータ挿入
CREATE TABLE Projects (
project_id INT PRIMARY KEY,
project_name VARCHAR(100) NOT NULL,
department_id INT
);

INSERT INTO Projects (project_id, project_name, department_id) VALUES
(101, 'Alpha', 1),
(102, 'Beta', 2),
(103, 'Gamma', NULL),
(104, 'Delta', 1),
(105, 'Epsilon', NULL);

-- 問題3: ProductsA テーブルの作成とデータ挿入
CREATE TABLE ProductsA (
product_id INT PRIMARY KEY,
name VARCHAR(50) NOT NULL,
price INT
);

INSERT INTO ProductsA (product_id, name, price) VALUES
(1, 'Apple', 100),
(2, 'Banana', 150),
(3, 'Cherry', 200),
(4, 'Date', NULL);

-- 問題3: ProductsB テーブルの作成とデータ挿入
CREATE TABLE ProductsB (
product_id INT PRIMARY KEY,
name VARCHAR(50) NOT NULL,
price INT
);

INSERT INTO ProductsB (product_id, name, price) VALUES
(1, 'Orange', 100),
(2, 'Pear', NULL),
(3, 'Kiwi', 300);

# 問題1

問題1: 不明な年齢のフィルタリング

以下のテーブルEmployeesがあるとします。

id	name	age

1	Alice	30

2	Bob	NULL

3	Carol	25

4	David	NULL

5	Eve	35

ageが不明（NULL）ではない従業員のnameとageをすべて取得してください。

SELECT name, age
from Employees
where age is not null;

# 問題2

問題2: 部署の割り当てと集計

以下のテーブルProjectsがあるとします。

project_id	project_name	department_id

101	Alpha	1

102	Beta	2

103	Gamma	NULL

104	Delta	1

105	Epsilon	NULL

department_idが割り当てられているプロジェクトの数をカウントしてください。

SELECT COUNT(department_id)
FROM Projects;

# 問題3 

問題3: NULL値を含むサブクエリの比較

以下の2つのテーブルProductsAとProductsBがあるとします。

ProductsA
| product_id | name      | price |
|------------|-----------|-------|
| 1          | Apple     | 100   |
| 2          | Banana    | 150   |
| 3          | Cherry    | 200   |
| 4          | Date      | NULL  |

ProductsB
| product_id | name     | price |
|------------|----------|-------|
| 1          | Orange   | 100   |
| 2          | Pear     | NULL  |
| 3          | Kiwi     | 300   |

[クエリ]
ProductsAテーブルから、ProductsBテーブルのpriceと一致するpriceを持つ製品（product_id, name, price）をすべて取得してください。

ただし、priceがNULLである場合は一致とみなさないでください。

SELECT product_id, name, price
FROM ProductsA
WHERE price IS NOT NULL
AND price IN (SELECT price FROM ProductsB WHERE price IS NOT NULL);