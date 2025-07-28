from faker import Faker
import mysql.connector
import random

fake = Faker()

conn = mysql.connector.connect(
    host='localhost',       # 예: 'localhost' 또는 실제 호스트 주소
    user='test_user',   # 본인의 사용자 이름
    password='test_password',  # 본인의 비밀번호
    database='test_db',   # 사용할 데이터베이스 이름
    port='3307'
)
cursor = conn.cursor()


# Faker 라이브러리 초기화
fake = Faker()

data_buffer = []

insert_sql = """
    INSERT INTO category (id, category_code,parent_id) VALUES (%s, %s, %s)
"""

#최상위 카테고리 설정
root_category = [10001,20001,30001,40001,50001,60001,70001,80001,90001,100001]
category_id = 1

for category in root_category:
    data_buffer.append((category_id, category,None))
    category_id += 1

#중위 카테고리 설정
count = 1
parent_id = 1
for category in root_category:
    for _ in range(100):
        data_buffer.append((category_id, (category + count), parent_id))
        category_id += 1
        count += 1

    parent_id += 1

cursor.executemany(insert_sql, data_buffer)
conn.commit()

# DB 연결 닫기
cursor.close()
conn.close()
print("✅ 모든 데이터 삽입 완료!")
