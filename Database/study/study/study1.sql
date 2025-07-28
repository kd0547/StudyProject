CREATE TABLE customers (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100)
);

INSERT INTO customers (id, name, email)
VALUES
    (1, 'Alice', 'alice@email.com'),
    (2, 'Bob', 'bob@email.com'),
    (3, 'Charlie', 'charlie@email.com');

INSERT INTO customers (id, name, email)
VALUE (4,'kim','kim@email.com');

-- 테이블 생성
CREATE TABLE orders (
                        id INT PRIMARY KEY,
                        customer_id INT,
                        total_amount INT,
                        order_date DATE
);

-- 데이터 삽입
INSERT INTO orders (id, customer_id, total_amount, order_date)
VALUES
    (1, 1, 100, '2024-01-10'),
    (2, 1, 200, '2024-01-15'),
    (3, 2, 150, '2024-01-20'),
    (4, 3, 300, '2024-02-01'),
    (5, 3, 50, '2024-02-05');


-- solution #1
SELECT s.name,
       s.email,
       IFNULL(SUM(total_amount), 0) as total_amount

FROM customers s
LEFT JOIN orders o ON s.id = o.customer_id
group by s.id, s.name, s.email
;

-- solution #2 my
with ranked_orders as (
    SELECT s.name,
           s.email,
           SUM(total_amount) as total_spent,
           o.order_date as last_order_date,
           rank() over (PARTITION BY s.id order by o.order_date desc) as rn
    FROM customers s
             LEFT JOIN orders o ON s.id = o.customer_id
    group by s.id, s.name, s.email, o.order_date
)
select name,
       email,
       IFNULL(last_order_date,'NULL') as last_order_date,
       IFNULL(total_spent,0) as total_spent
from ranked_orders
where rn = 1;


WITH total_spent_cte AS (
    -- 고객별 총 주문 금액을 먼저 집계
    SELECT
        customer_id,
        SUM(total_amount) AS total_spent
    FROM orders
    GROUP BY customer_id
),
     ranked_orders AS (
         -- 고객별 최신 주문을 찾기 위한 랭킹 연산
         SELECT
             s.id,
             s.name,
             s.email,
             o.order_date AS last_order_date,
             RANK() OVER (PARTITION BY s.id ORDER BY o.order_date DESC) AS rn
         FROM customers s
                  LEFT JOIN orders o ON s.id = o.customer_id
     )
-- 두 개의 CTE를 최종 결합
SELECT
    r.name,
    r.email,
    COALESCE(r.last_order_date, NULL) AS last_order_date,
    COALESCE(t.total_spent, 0) AS total_spent
FROM ranked_orders r
         LEFT JOIN total_spent_cte t ON r.id = t.customer_id
WHERE r.rn = 1;


