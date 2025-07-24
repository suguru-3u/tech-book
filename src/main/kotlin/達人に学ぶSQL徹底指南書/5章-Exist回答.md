
-----

### MySQL `EXISTS`句 実践問題 - 解答

#### 問題と解答

**問題 1: 未完了のプロジェクト**
`Projects`テーブルから、全てのステップが「完了」していないプロジェクトの`project_id`を抽出してください。つまり、一つでも「待機」のステータスを持つステップが存在するプロジェクトを特定してください。

**解答 1:**

```sql
SELECT project_id
FROM Projects p1
WHERE EXISTS (
	SELECT project_id
	FROM Projects p2
	WHERE p1.project_id = p2.project_id AND status = '待機'
);
```

-----

**問題 2: 特定科目の点数が50点未満の学生**
`TestScores`テーブルから、いずれかの科目で50点未満の点数を取った学生の`student_id`を抽出してください。

**解答 2:**

```sql
SELECT student_id
FROM TestScores t1
WHERE EXISTS (
	SELECT student_id
	FROM TestScores t2
	WHERE t1.student_id = t2.student_id AND score < 50
);
```

-----

**問題 3: 全ての会議に参加したことがない人物**
`Meetings`テーブルに名前が記載されている人物の中で、開催された全ての会議（`meeting_id`のユニークな値）に一度も参加していない人物の名前を抽出してください。

**解答 3:**
*注記: この問題は、現在の`Meetings`テーブルのデータ構造と「全ての会議に一度も参加していない」という厳密な条件を組み合わせると、SQLでの直接的な表現が困難になる場合があります。もし、`Meetings`テーブルに名前は存在するが、どの会議にも参加していない人物という意図であれば、それは該当者がいないことになります。ここでは「`Meetings`テーブルに登場する人物のうち、全ての開催された会議には参加していない人物」と解釈し、そのための典型的な`NOT EXISTS`のパターンで解答します。これは「全ての会議に参加している人物」の逆を求めることで実現します。*

```sql
SELECT DISTINCT m2.person
FROM Meetings m1 CROSS JOIN Meetings m2
WHERE NOT EXISTS(
	SELECT person
	FROM Meetings m3
	WHERE m1.meeting_id = m3.meeting_id AND m1.person = m2.person	
);
```

-----

**問題 4: 全ての`col`がNULLではない`ArrayTbl`のキー**
`ArrayTbl`テーブルから、`col1`から`col10`までの全ての列が`NULL`ではない`key`を抽出してください。

**解答 4:**

```sql
SELECT `key`
FROM ArrayTbl
WHERE col1 IS NOT NULL
  AND col2 IS NOT NULL
  AND col3 IS NOT NULL
  AND col4 IS NOT NULL
  AND col5 IS NOT NULL
  AND col6 IS NOT NULL
  AND col7 IS NOT NULL
  AND col8 IS NOT NULL
  AND col9 IS NOT NULL
  AND col10 IS NOT NULL;
```

-----

**問題 5: `Tbl_A`に存在するが`Tbl_B`には存在しない名前を持つ人物**
`Tbl_A`テーブルには存在するが、`Tbl_B`テーブルには存在しない`name`を持つ人物を`Tbl_A`から抽出してください。

**解答 5:**

```sql
SELECT name
FROM Tbl_A ta
WHERE NOT EXISTS(
	SELECT name
	FROM Tbl_B tb
	WHERE ta.name = tb.name
);
```