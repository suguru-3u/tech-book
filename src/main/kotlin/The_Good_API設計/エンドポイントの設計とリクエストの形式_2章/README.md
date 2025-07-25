# 概要

オライリージャパン「Web API The Good Parts」の2章-エンドポイントの設計とリクエストの形式を読んで<br>
問題を作成しました。

# Web API エンドポイント設計とリクエスト形式 - 問題と正解

## 問題 1
### 問題文
APIエンドポイントのURL設計において、推奨されない慣習はどれですか？

1.  URLに複数の名詞を使用し、ハイフンで区切る。
2.  **URLにサーバ側のアーキテクチャ（例: .php）を反映させる。**
3.  URLは大文字小文字が混在しないようにし、基本小文字を使用する。
4.  短く入力しやすく、人間が読んで理解できるURLにする。

### 答え
No.2

---

## 問題 2
### 問題文
API設計における「改造しやすいURL」とは、主にどのような特性を指しますか？

1.  URL全体を簡単に変更して、別の機能にアクセスできるようにすること。
2.  **URLの末尾のIDを変更するだけで、異なるリソース（例：異なるTodo）にアクセスできること。**
3.  クエリパラメータを追加・削除することで、APIの動作を調整しやすいこと。
4.  APIのバージョンアップ時に、簡単にURL構造を変更できること。

### 答え
No.2

---

## 問題 3
### 問題文
HTTPメソッドとURLの関係を理解するための適切な考え方はどれですか？

1.  URLは動詞、HTTPメソッドは名詞。
2.  **URLは対象（名詞）、HTTPメソッドはその対象に対する操作（動詞）。**
3.  URLとHTTPメソッドは完全に独立しており、関連性はない。
4.  URLは常に大文字、HTTPメソッドは常に小文字。

### 答え
No.2

---

## 問題 4
### 問題文
一部の環境でGETとPOSTしか使用できない場合に、本来のHTTPメソッド（例: PUTやDELETE）をシミュレートするための一般的な方法は何ですか？

1.  リクエストボディにHTTPメソッド名を直接記述する。
2.  特別なポート番号を使用して異なるメソッドを表現する。
3.  **リクエストヘッダ `X-HTTP-Method-Override` またはパラメータ `_method` を使用する。**
4.  常にPOSTメソッドを使用し、URLのパスで目的の操作を識別する。

### 答え
No.3

---

## 問題 5
### 問題文
大規模なデータセットにおいて、`offset`と`limit`を使用したページネーションで発生する可能性のあるパフォーマンス上の問題は何ですか？

1.  データベースのストレージ容量がすぐに不足する。
2.  **データの取得位置が後になるにつれて、データベースのクエリ速度が低下する。**
3.  ネットワーク帯域幅が著しく消費される。
4.  クライアント側でのデータ処理が複雑になる。

### 答え
No.2

---

## 問題 6
### 問題文
`offset`と`limit`によるパフォーマンス問題を解決するために推奨されるクエリパラメータの設計変更は何ですか？

1.  `page`パラメータの値を無限に大きくできるようにする。
2.  **`max-id`のような「絶対位置」を示すパラメータを使用し、指定したIDからデータを取得する。**
3.  `limit`の値を非常に大きく設定し、一度にすべてのデータを取得する。
4.  クエリパラメータを全て削除し、パスパラメータのみを使用する。

### 答え
No.2

---

## 問題 7
### 問題文
APIエンドポイントの設計において、特定の検索サービス（例：Yahoo!、Bing）のAPIで`search`という単語をURIに含めることが許容される理由として最も適切なものはどれですか？

1.  `search`は名詞なので、URIに含めるのが適切だから。
2.  **検索サービスは「検索を行う」ことがAPIの主な利用目的であり、利用者がAPIを理解しやすくなるから。**
3.  URIを短くするためには、`search`のような短い単語が必要だから。
4.  ほとんどのWeb APIが`search`をURIに含んでいるから。

### 答え
No.2

---

## 問題 8
### 問題文
クエリパラメータとパスパラメータの使い分けに関する記述として、最も適切なものはどれですか？

1.  一意なリソースを表す情報も、省略可能な情報も、全てクエリパラメータとして指定すべきである。
2.  **ユーザーIDのような一意なリソースを示す情報はパスに含め、アクセス・トークンやページネーション用のパラメータはクエリパラメータとして指定する。**
3.  全てのパラメータはクエリパラメータとして指定し、パスは常に固定すべきである。
4.  情報の重要度が高い場合はクエリパラメータ、低い場合はパスパラメータを使用する。

### 答え
No.2

---

## 問題 9
### 問題文
以下のHTTPメソッドのうち、既存リソースの「一部」を変更するために推奨されるものはどれですか？

1.  PUT
2.  POST
3.  **PATCH**
4.  GET

### 答え
No.3

---

## 問題 10
### 問題文
API設計の最初のステップとして、「どんなAPIを作成したいのか考え整理しよう」とありますが、その際に一緒に考えると良いとされている要素は何ですか？

1.  APIのデプロイ方法とサーバの選定。
2.  **ユーザーがAPIをどう使用するのかというユースケースやアプリの画面と遷移の関係。**
3.  APIの収益化モデルとマーケティング戦略。
4.  競合他社のAPIの完全なコピー。

### 答え
No.2

## 問題 11
### 問題文
あるAPIがユーザーのプロフィール画像を更新するために、`PUT /users/{id}/profile_image` というエンドポイントを提供しています。このエンドポイントにリクエストボディとして新しい画像データを送信する際、最も適切な`Content-Type`ヘッダの値はどれですか？

1.  `application/json`
2.  `text/plain`
3.  `image/jpeg`
4.  `application/x-www-form-urlencoded`

### 答え
No.3

---

## 問題 12
### 問題文
APIの設計において、リクエストのペイロードサイズが非常に大きい場合（例：大量のデータを一括で登録する場合）、どのようなHTTPメソッドが最も適切ですか？

1.  GET
2.  POST
3.  PUT
4.  DELETE

### 答え
No.2

---

## 問題 13
### 問題文
APIが特定のユーザーのToDoリストをフィルタリングする機能を提供しています。例えば、完了済みのToDoのみを取得したい場合、`GET /users/{user_id}/todos` に追加すべきクエリパラメータとして最も適切なものはどれですか？

1.  `/users/{user_id}/todos/completed`
2.  `/users/{user_id}/todos?status=completed`
3.  `/users/{user_id}/todos?filter_by=completed`
4.  リクエストヘッダに`X-Filter-Status: completed`を含める

### 答え
No.2

---

## 問題 14
### 問題文
APIがリソースの削除に成功したが、クライアントに返す必要のあるコンテンツがない場合、最も適切なHTTPステータスコードはどれですか？

1.  200 OK
2.  201 Created
3.  204 No Content
4.  404 Not Found

### 答え
No.3

---

## 問題 15
### 問題文
APIがユーザーのリストを返す際、各ユーザーの情報にそのユーザーの詳細情報を取得するためのURIを含めるべきであるという原則は、API設計のどの概念に最も関連していますか？

1.  RESTful API
2.  SOAP
3.  HATEOAS
4.  RPC

### 答え
No.3

---

## 問題 16
### 問題文
APIクライアントがリクエストを送信する際、サーバーが期待するデータ形式ではない場合に返すべきHTTPステータスコードとして最も適切なものはどれですか？

1.  400 Bad Request
2.  401 Unauthorized
3.  403 Forbidden
4.  406 Not Acceptable

### 答え
No.1

---

## 問題 17
### 問題文
APIのバージョン管理において、URIにバージョンを含める方法（例: `/v1/users`）の欠点として考えられるものはどれですか？

1.  クライアントが常に最新バージョンに更新する必要がある。
2.  URIが長くなり、可読性が低下する可能性がある。
3.  APIの破壊的変更をリリースできない。
4.  サーバー側のルーティングが複雑になる。

### 答え
No.2

---

## 問題 18
### 問題文
APIが特定の期間内のイベントを取得する機能を提供する場合、クエリパラメータで期間を指定する際のベストプラクティスとして最も適切なものはどれですか？

1.  `start_date`と`end_date`のような明確な名前を使用する。
2.  `from`と`to`のような短縮形のみを使用する。
3.  期間指定はパスパラメータとして含める。
4.  期間はリクエストボディに含める。

### 答え
No.1

---

## 問題 19
### 問題文
APIのレスポンスにおいて、日付と時刻の情報を返す際の推奨されるフォーマットはどれですか？

1.  `YYYY/MM/DD HH:MM:SS` (例: 2023/07/18 10:30:00)
2.  タイムスタンプ（Unix時間）
3.  ISO 8601フォーマット (例: `2023-07-18T10:30:00Z`)
4.  各国のローカルタイムゾーンに合わせた文字列

### 答え
No.3

---

## 問題 20
### 問題文
APIがユーザーのパスワードを変更する機能を提供する場合、そのリクエストに最も適切なHTTPメソッドはどれですか？

1.  GET
2.  POST
3.  PUT
4.  DELETE

### 答え
No.3