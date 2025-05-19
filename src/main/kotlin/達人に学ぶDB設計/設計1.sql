問題

-- 問題（上流設計レベル）:

-- あなたは小規模な図書館のデータベースを設計することになりました。
-- 図書館は以下のような業務を管理したいと考えています。

-- [業務要件]
-- ・利用者は図書館に登録され、名前、住所、電話番号などの情報が記録される。
-- ・図書はISBN、タイトル、著者、出版社、出版年などの情報を持つ。
-- ・図書には複数の実物（冊子）が存在し、それぞれに管理番号がつけられている。
-- ・利用者は図書を1回に最大5冊まで貸し出すことができる。
-- ・貸出情報には、どの冊子が、誰に、いつ貸し出され、いつ返却されたかを記録する必要がある。
-- ・延滞などの状況も記録される可能性がある。

-- [課題]
-- 1. 上記の要件に基づき、必要な実体（エンティティ）を洗い出してください。
-- 2. 各実体の主キー・属性を考えてください。
-- 3. 実体間の関係（リレーション）を洗い出し、リレーションシップの多重度（1対多、多対多など）を整理してください。
-- 4. 以上の設計内容に基づいて、テーブル定義（CREATE TABLE文）を作成してください。
-- 5. 正規化を意識して設計されているかを確認してください（第3正規形を目指す）。

-- ※SQLの記述は5.のみです。1～3はまず紙や図などで整理してから着手することを推奨します。

create table Users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15) NOT NULL
);

create table Books (
    id INT PRIMARY KEY AUTO_INCREMENT,
    isbn VARCHAR(13) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(100) NOT NULL,
    publisher VARCHAR(100) NOT NULL,
    publication_year INT NOT NULL
);

create table Booklets (
    management_number VARCHAR(50) PRIMARY NOT NULL,
    book_id INT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES Books(id)
);

create table BookLendings(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    books_id INT NOT NULL,
    status Boolean NOT NULL DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (books_id) REFERENCES Books(id)
)

create table BookletLendings(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    booklet_id INT NOT NULL,
    status Boolean NOT NULL DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (booklet_id) REFERENCES Booklets(id)
)

create table OverdueBooks(
    id INT PRIMARY KEY AUTO_INCREMENT,
    BookLending_id INT NOT NULL,
    status Boolean NOT NULL DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (BookLending_id) REFERENCES BookLendings(id),
)

create table OverdueBooklets(
    id INT PRIMARY KEY AUTO_INCREMENT,
    BookletLending_id INT NOT NULL,
    status Boolean NOT NULL DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (BookletLending_id) REFERENCES BookletLendings(id)
)


-- 回答
-- 利用者情報
CREATE TABLE Users (
   id INT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(100) NOT NULL,
   address VARCHAR(255) NOT NULL,
   phone_number VARCHAR(15) NOT NULL
);

-- 書籍（ISBN単位）
CREATE TABLE Books (
   id INT PRIMARY KEY AUTO_INCREMENT,
   isbn VARCHAR(13) NOT NULL UNIQUE,
   title VARCHAR(255) NOT NULL,
   author VARCHAR(100) NOT NULL,
   publisher VARCHAR(100) NOT NULL,
   publication_year INT NOT NULL
);

-- 冊子（物理本） - 図書館が所持している実物の本
CREATE TABLE Booklets (
  management_number VARCHAR(50) PRIMARY KEY,
  book_id INT NOT NULL,
  FOREIGN KEY (book_id) REFERENCES Books(id)
);

-- 冊子の貸出記録
CREATE TABLE BookletLendings (
     id INT PRIMARY KEY AUTO_INCREMENT,
     user_id INT NOT NULL,
     booklet_id VARCHAR(50) NOT NULL,
     lend_date DATE NOT NULL,
     due_date DATE NOT NULL,
     return_date DATE,
     status BOOLEAN NOT NULL DEFAULT FALSE, -- FALSE = 未返却, TRUE = 返却済み
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     FOREIGN KEY (user_id) REFERENCES Users(id),
     FOREIGN KEY (booklet_id) REFERENCES Booklets(management_number)
);


✅ 今後の拡張例（任意）
Genres テーブルを追加して書籍ジャンル管理

Authors を独立したテーブルにして複数著者対応

LendingLimits を持たせて、年齢や会員区分で貸出制限を柔軟に

