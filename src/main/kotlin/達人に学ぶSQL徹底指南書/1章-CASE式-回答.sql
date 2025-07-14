--No1 回答

SELECT
  CASE pref_name
    WHEN '徳島' THEN '四国'
    WHEN '香川' THEN '四国'
    WHEN '愛媛' THEN '四国'
    WHEN '高知' THEN '四国'
    WHEN '福岡' THEN '九州'
    WHEN '佐賀' THEN '九州'
    WHEN '長崎' THEN '九州'
    ELSE 'その他' END AS Area,
  SUM(population)
FROM PopTbl
GROUP BY
  CASE pref_name
    WHEN '徳島' THEN '四国'
    WHEN '香川' THEN '四国'
    WHEN '愛媛' THEN '四国'
    WHEN '高知' THEN '四国'
    WHEN '福岡' THEN '九州'
    WHEN '佐賀' THEN '九州'
    WHEN '長崎' THEN '九州'
    ELSE 'その他' END;


--No2 回答
SELECT
	CASE
		WHEN population < 100 THEN '01'
		WHEN 100 <= population AND population < 200 THEN '02'
		WHEN 200 <= population AND population < 300 THEN '03'
		ELSE '04'
	END,
	Count(*)
from PopTbl
group by
	CASE
		WHEN population < 100 THEN '01'
		WHEN 100 <= population AND population < 200 THEN '02'
		WHEN 200 <= population AND population < 300 THEN '03'
		ELSE '04'
	END;

--No3 回答
SELECT
	pref_name,
	SUM(
		CASE
			WHEN sex = 1 THEN population
			ELSE 0
		END
	) AS '男性',
	SUM(
		CASE
			WHEN sex = 2 THEN population
			ELSE 0
		END
	) AS '女性'
FROM PopTbl2
Group by pref_name;

--No4 回答
SELECT
  CM.course_name,
  CASE WHEN EXISTS (SELECT 1 FROM OpenCourses OC WHERE OC.course_id = CM.course_id AND OC.month = 201806) THEN '○' ELSE '×' END AS "6月",
  CASE WHEN EXISTS (SELECT 1 FROM OpenCourses OC WHERE OC.course_id = CM.course_id AND OC.month = 201807) THEN '○' ELSE '×' END AS "7月",
  CASE WHEN EXISTS (SELECT 1 FROM OpenCourses OC WHERE OC.course_id = CM.course_id AND OC.month = 201808) THEN '○' ELSE '×' END AS "8月"
FROM CourseMaster CM;

--No5 回答
SELECT
  std_id,
  MAX(
    CASE
      WHEN main_club_fig = 'Y' THEN club_id
      ELSE NULL
    END
  ) AS main_club
FROM StudentClub
GROUP BY std_id
HAVING COUNT(*) = 1 AND COUNT(CASE WHEN main_club_fig = 'Y' THEN 1 ELSE NULL END) = 1;