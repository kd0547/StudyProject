from datetime import datetime, timedelta

from faker import Faker
import mysql.connector
import random


fake = Faker('ko-KR')

conn = mysql.connector.connect(
    host='localhost',       # 예: 'localhost' 또는 실제 호스트 주소
    user='test_user',   # 본인의 사용자 이름
    password='test_password',  # 본인의 비밀번호
    database='test_db',   # 사용할 데이터베이스 이름
    port='3307'
)

cursor = conn.cursor()

fake = Faker()

data_buffer = []

insert_sql = """
    INSERT INTO user (id, username,email,password,phone,status,create_at,access_at) VALUES (%s, %s, %s,%s, %s, %s,%s,%s)
"""

TOTAL_USER = 10000000

BATCH_SIZE = 10000

ROUTINE = TOTAL_USER // BATCH_SIZE

count = 1

for _ in range(ROUTINE):
    for _ in range(BATCH_SIZE):
        id = count
        name = fake.name()
        email = fake.email()
        password = fake.password()
        phone = fake.phone_number()
        create_at = fake.date_between(start_date='-5y', end_date='today')
        access_at = fake.date_between(start_date=create_at, end_date='today')

        today = datetime.today().date()

        one_year_ago = timedelta(days=365)

        if(today - access_at) >= one_year_ago:
            status = 'Inactive'
        else:
            status = 'Active'

        data_buffer.append((id, name, email, password, phone, status,create_at, access_at))
        count += 1

    if(len(data_buffer) >= BATCH_SIZE):
        cursor.executemany(insert_sql, data_buffer)
        conn.commit()
        data_buffer.clear()  # 버퍼 초기화

if data_buffer:
    cursor.executemany(insert_sql, data_buffer)
    conn.commit()

# DB 연결 닫기
cursor.close()
conn.close()