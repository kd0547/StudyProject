-- products 테이블 생성 (상품 정보)
CREATE TABLE products (
                          id INT PRIMARY KEY,      -- 상품 ID
                          name VARCHAR(100) NOT NULL,  -- 상품 이름
                          stock INT NOT NULL,      -- 재고 수량
                          price INT NOT NULL       -- 가격
);

-- orders 테이블 생성 (주문 정보)
CREATE TABLE orders (
                        id INT PRIMARY KEY,      -- 주문 ID
                        product_id INT NOT NULL, -- 주문한 상품 ID
                        quantity INT NOT NULL,   -- 주문 수량
                        order_date DATE NOT NULL, -- 주문 날짜
                        FOREIGN KEY (product_id) REFERENCES products(id) -- 외래 키 설정
);

-- products 테이블에 데이터 삽입
INSERT INTO products (id, name, stock, price)
VALUES
    (1, 'Laptop', 10, 1000),
    (2, 'Keyboard', 5, 80),
    (3, 'Mouse', 8, 50);

-- orders 테이블에 데이터 삽입
INSERT INTO orders (id, product_id, quantity, order_date)
VALUES
    (1, 1, 2, '2024-02-15'),
    (2, 2, 1, '2024-02-16');

SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
START TRANSACTION;

SELECT stock FROM products WHERE id = 1 FOR UPDATE;

-- 재고가 충분한 경우만 주문 추가
INSERT INTO orders (product_id, quantity, order_date)
SELECT 1, 2, '2024-02-18'
WHERE (SELECT stock FROM products WHERE id = 1) >= 2;

-- 재고 업데이트
UPDATE products
SET stock = stock - 2
WHERE id = 1 AND stock >= 2;


COMMIT;
