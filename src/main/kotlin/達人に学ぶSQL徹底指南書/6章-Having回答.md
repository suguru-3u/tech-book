### 問題1

1.  **提出日がすべて埋まっている学部を抽出してください。**

    ```sql
    SELECT dpt
    FROM Students
    GROUP BY dpt
    HAVING COUNT(sbmt_date) = COUNT(*);
    ```

2.  **男子の平均点が女子の平均点よりも高いクラスを抽出してください。ただし、どちらかの性別の学生がいないクラスは除外してください。**

    ```sql
    SELECT class
    FROM TestResults
    GROUP BY class
    HAVING AVG(CASE WHEN sex = '男' THEN score ELSE NULL END) > AVG(CASE WHEN sex = '女' THEN score ELSE NULL END);
    ```

-----

### 問題2

1.  **全員が「待機」状態であるチームの`team_id`を抽出してください。**

    ```sql
    SELECT team_id
    FROM Teams
    GROUP BY team_id
    HAVING COUNT(*) = COUNT(CASE WHEN status = '待機' THEN 1 END);
    ```

2.  **`ShopItems`テーブル内で、販売している商品の種類数が最も多い店舗を抽出してください。**

    ```sql
    SELECT shop
    FROM ShopItems
    GROUP BY shop
    HAVING COUNT(DISTINCT item) >= ALL(
        SELECT COUNT(DISTINCT item)
        FROM ShopItems
        GROUP BY shop
    );
    ```