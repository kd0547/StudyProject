-- customers 테이블 생성 (고객 정보)
CREATE TABLE customers (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,         -- 고객 ID (기본 키)
                           name VARCHAR(100) NOT NULL, -- 고객 이름
                           email VARCHAR(100) NOT NULL -- 이메일
);

-- orders 테이블 생성 (대량 주문 데이터)
CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT,                  -- 주문 ID (기본 키, 자동 증가)
                        customer_id INT NOT NULL,                   -- 고객 ID (외래 키)
                        total_amount DECIMAL(10,2) NOT NULL,          -- 총 결제 금액
                        order_date DATE NOT NULL,                   -- 주문 날짜
                        order_year INT AS (YEAR(order_date)) STORED,  -- order_date의 연도 값 (생성 컬럼)

                        PRIMARY KEY (id, order_year)
) PARTITION BY RANGE (order_year) (
        PARTITION p2021 VALUES LESS THAN (2022),
        PARTITION p2022 VALUES LESS THAN (2023),
        PARTITION p2023 VALUES LESS THAN (2024),
        PARTITION p2024 VALUES LESS THAN (2025),
        PARTITION p_future VALUES LESS THAN MAXVALUE
        );


CREATE INDEX index_orders_date ON orders(order_date);

WITH rank_total_spent AS (
    WITH total_spent_table AS (
        SELECT
            o.customer_id,
            SUM(o.total_amount) AS total_spent
        FROM orders o
        WHERE order_year = 2023
        GROUP BY o.customer_id
    )
    SELECT
        customer_id,
        total_spent,
        RANK() OVER (ORDER BY total_spent DESC) AS rnk
    FROM total_spent_table
)
SELECT
    r.customer_id,
    c.name,
    c.email,
    r.total_spent
FROM rank_total_spent r
         INNER JOIN customers c ON r.customer_id = c.id
WHERE rnk = 1;


WITH total_spent_table AS (
    SELECT
        o.customer_id,
        SUM(o.total_amount) AS total_spent
    FROM orders o
    WHERE order_year = 2023
    GROUP BY o.customer_id
),
     rank_total_spent AS (
         SELECT
             customer_id,
             total_spent,
             RANK() OVER (ORDER BY total_spent DESC) AS rnk
         FROM total_spent_table
     )
SELECT
    r.customer_id,
    c.name,
    c.email,
    r.total_spent
FROM rank_total_spent r
         INNER JOIN customers c ON r.customer_id = c.id
WHERE rnk = 1;


