-- customers 테이블 생성 (고객 정보)
CREATE TABLE customers (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- orders 테이블 생성 (주문 정보)
CREATE TABLE orders (
    id INT PRIMARY KEY,
    customer_id INT NOT NULL,
    order_date DATE NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- order_items 테이블 생성 (주문 상세 정보)
CREATE TABLE order_items (
    id INT PRIMARY KEY,
    order_id INT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    price INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- customers 테이블에 데이터 삽입
INSERT INTO customers (id, name, email)
VALUES
    (1, 'Alice', 'alice@email.com'),
    (2, 'Bob', 'bob@email.com'),
    (3, 'Charlie', 'charlie@email.com');

-- orders 테이블에 데이터 삽입
INSERT INTO orders (id, customer_id, order_date)
VALUES
    (1, 1, '2024-01-10'),
    (2, 1, '2024-01-15'),
    (3, 2, '2024-01-20'),
    (4, 3, '2024-02-01'),
    (5, 3, '2024-02-05');

-- order_items 테이블에 데이터 삽입
INSERT INTO order_items (id, order_id, product_name, category, price)
VALUES
    (1, 1, 'Laptop', 'Electronics', 1000),
    (2, 1, 'Mouse', 'Electronics', 50),
    (3, 2, 'Keyboard', 'Electronics', 80),
    (4, 3, 'Desk', 'Furniture', 200),
    (5, 4, 'Chair', 'Furniture', 150),
    (6, 5, 'Lamp', 'Furniture', 50);

with order_price as (
    select
        o.customer_id,
        oi.category,
        sum(price) as total_sales
    from orders o
    left join order_items oi on o.id = oi.order_id
    group by o.customer_id, oi.category
),
rank_customers as (
    select
        op.category,
        c.name,
        c.email,
        op.total_sales,
        rank() over (partition by op.category order by op.total_sales desc) as rnk
    from order_price op
    inner join customers c ON op.customer_id = c.id
)
select category,
       name,
       email,
       total_sales
from rank_customers
where rnk = 1;



WITH order_price AS (
    SELECT
        o.customer_id,
        oi.category,
        SUM(oi.price) AS total_sales
    FROM orders o
    LEFT JOIN order_items oi ON o.id = oi.order_id
    GROUP BY o.customer_id, oi.category
),
ranked_customers AS (
    SELECT
        op.category,
        c.name,
        c.email,
        op.total_sales,
        RANK() OVER (PARTITION BY op.category ORDER BY op.total_sales DESC) AS rnk
    FROM order_price op
    INNER JOIN customers c ON op.customer_id = c.id
)
SELECT
    category,
    name,
    email,
    total_sales
FROM ranked_customers
WHERE rnk = 1;
