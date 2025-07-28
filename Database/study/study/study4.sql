-- customers 테이블 생성 (고객 정보)
CREATE TABLE customers (
                           id INT PRIMARY KEY,         -- 고객 ID (기본 키)
                           name VARCHAR(100) NOT NULL, -- 고객 이름
                           email VARCHAR(100) NOT NULL -- 이메일
);

-- orders 테이블 생성 (주문 정보)
CREATE TABLE orders (
                        id INT PRIMARY KEY,         -- 주문 ID (기본 키)
                        customer_id INT NOT NULL,   -- 고객 ID (외래 키)
                        order_date DATE NOT NULL,   -- 주문 날짜
                        FOREIGN KEY (customer_id) REFERENCES customers(id)  -- 고객 정보 참조
);

-- order_items 테이블 생성 (주문 상세 정보)
CREATE TABLE order_items (
                             id INT PRIMARY KEY,        -- 주문 상세 ID (기본 키)
                             order_id INT NOT NULL,     -- 주문 ID (외래 키)
                             product_name VARCHAR(255) NOT NULL, -- 상품 이름
                             price DECIMAL(10,2) NOT NULL,       -- 상품 가격
                             FOREIGN KEY (order_id) REFERENCES orders(id)  -- 주문 정보 참조
);

-- customers 테이블 데이터 삽입
INSERT INTO customers (id, name, email)
VALUES
    (1, 'Alice', 'alice@email.com'),
    (2, 'Bob', 'bob@email.com'),
    (3, 'Charlie', 'charlie@email.com');

-- orders 테이블 데이터 삽입
INSERT INTO orders (id, customer_id, order_date)
VALUES
    (1, 1, '2024-01-10'),
    (2, 1, '2024-01-15'),
    (3, 2, '2024-01-20');

-- order_items 테이블 데이터 삽입
INSERT INTO order_items (id, order_id, product_name, price)
VALUES
    (1, 1, 'Laptop', 1000),
    (2, 1, 'Mouse', 50),
    (3, 2, 'Keyboard', 80),
    (4, 3, 'Desk', 200);

#기존에 사용하던 (느린) SQL 쿼리
SELECT
    c.name,
    c.email,
    o.order_date,
    oi.product_name,
    oi.price
FROM customers c
JOIN orders o ON c.id = o.customer_id
JOIN order_items oi ON o.id = oi.order_id
WHERE c.id = 1;

create index order_id_index ON orders(id);
create index order_items_id_index ON order_items(id);