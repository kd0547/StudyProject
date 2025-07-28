from faker import Faker
import faker_commerce
import mysql.connector
import random

fake = Faker()
fake.add_provider(faker_commerce.Provider)

conn = mysql.connector.connect(
    host='localhost',       # 예: 'localhost' 또는 실제 호스트 주소
    user='test_user',   # 본인의 사용자 이름
    password='test_password',  # 본인의 비밀번호
    database='test_db',   # 사용할 데이터베이스 이름
    port='3307'
)
cursor = conn.cursor()

data_buffer = []

insert_sql = """
    INSERT INTO product (id, name,description,price,stock,created_at,updated_at,category_id) VALUES (%s,%s,%s,%s,%s,%s,%s,%s)
"""

#최상위 카테고리 설정
root_category = [10001,20001,30001,40001,50001,60001,70001,80001,90001,100001]
#category_id = 1

#for category in root_category:
#    data_buffer.append((category_id, category))
#    category_id += 1

#중위 카테고리 설정
count = 1
for category_id in range(11,1011):
    for _ in range(1, 10001):
        product_id = count
        product_name = fake.ecommerce_name()  # 제품명은 회사명이나 상품명을 활용할 수 있음
        product_description = fake.text(max_nb_chars=200)  # 간단한 설명
        product_price = float(fake.pydecimal(left_digits=3, right_digits=2, positive=True))
        product_stock = fake.random_int(min=0, max=1000)
        product_created_at = fake.date_time_this_year()
        product_updated_at = fake.date_time_this_year()
        # category_id는 미리 정의한 목록에서 랜덤 선택 (기존 값 유지)
        product_category_id = category_id

        data_buffer.append((
            product_id,
            product_name,
            product_description,
            product_price,
            product_stock,
            product_created_at,
            product_updated_at,
            product_category_id
        ))
        count += 1
    print(f"Inserted {len(data_buffer)} comments...")

    cursor.executemany(insert_sql, data_buffer)
    conn.commit()
    data_buffer.clear()




# DB 연결 닫기
cursor.close()
conn.close()
print("✅ 모든 데이터 삽입 완료!")