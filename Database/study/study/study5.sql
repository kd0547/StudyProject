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

CREATE INDEX idx_orders_date ON orders(order_year, order_date);

SELECT customer_id,
       order_year,
       month(order_date) as month,
       sum(total_amount) FROM orders
where order_year = 2023
group by customer_id,month(order_date) ORDER BY NULL;

CREATE INDEX idx_orders_year_month ON orders(order_year, order_date, customer_id, total_amount);

WITH monthly_sales AS (
    SELECT
        customer_id,
        order_year,
        MONTH(order_date) AS month,
        SUM(total_amount) AS total_sales
    FROM orders
    WHERE order_year = 2023
    GROUP BY customer_id, order_year, MONTH(order_date) ORDER BY NULL
)
SELECT * FROM monthly_sales;


SELECT YEAR(order_date) AS year,
       MONTH(order_date) AS month,
       SUM(total_amount) AS total_sales
FROM orders
WHERE order_year = 2023
GROUP BY YEAR(order_date), MONTH(order_date);

