# 問題1

SELECT name, age
from Employees
where age is not null;

# 問題2

SELECT COUNT(department_id)
FROM Projects;

# 問題3

SELECT product_id, name, price
FROM ProductsA
WHERE price IS NOT NULL
AND price IN (SELECT price FROM ProductsB WHERE price IS NOT NULL);