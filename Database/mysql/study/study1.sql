-- customers 테이블 생성
CREATE TABLE customers (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- orders 테이블 생성 (customer_id는 customers 테이블의 id를 참조)
CREATE TABLE orders (
    id INT PRIMARY KEY,
    customer_id INT NOT NULL,
    total_amount INT NOT NULL,
    order_date DATE NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- customers 테이블에 데이터 삽입
INSERT INTO customers (id, name, email)
VALUES
    (1, 'Alice', 'alice@email.com'),
    (2, 'Bob', 'bob@email.com'),
    (3, 'Charlie', 'charlie@email.com');

-- orders 테이블에 데이터 삽입
INSERT INTO orders (id, customer_id, total_amount, order_date)
VALUES
    (1, 1, 100, '2024-01-10'),
    (2, 1, 200, '2024-01-15'),
    (3, 2, 150, '2024-01-20'),
    (4, 3, 300, '2024-02-01'),
    (5, 3, 50,  '2024-02-05');


create index idx_orders_date ON orders(order_date);

-- 만약 동률인 경우 하나만 출력되니까 정답은 아님
select
    c.name,
    c.email,
    count(o.id) as order_count
from customers c
inner join orders o on c.id = o.customer_id
where o.order_date between '2024-01-01' and '2024-01-31'
group by c.id
order by order_count desc
limit 1
;

CREATE INDEX idx_orders_date_customer ON orders(order_date, customer_id);

with rank_customers as (
    select
    c.name,
    c.email,
    count(o.id) as order_count,
    RANK() OVER (ORDER BY COUNT(o.id) DESC) AS rnk
from customers c
inner join orders o on c.id = o.customer_id
where o.order_date between '2024-01-01' and '2024-01-31'
group by c.id,c.name, c.email
) select * from rank_customers
where rnk = 1;



