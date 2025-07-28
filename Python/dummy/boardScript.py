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

# 설정 값
TOTAL_BOARD_COUNT = 9950000  # 총 게시글 수
COMMENTS_PER_BOARD = 10  # 각 게시글 당 코멘트 개수
BATCH_SIZE = 10_000  # 한 번에 삽입할 데이터 개수 (버퍼)

insert_sql = """
    INSERT INTO comment (id, board_id, author, create_at, update_at,comment)
    VALUES (%s, %s, %s, %s, %s, %s)
"""

data_buffer = []
comment_id = 1  # ID 자동 증가

# 데이터 삽입 루프
for board_id in range(1, TOTAL_BOARD_COUNT + 1):
    now = fake.date_time_between(start_date="-2y", end_date="now")

    for _ in range(COMMENTS_PER_BOARD):
        create_at = fake.date_time_between(start_date="-2y", end_date="now")        # 2년 전 ~ 현재
        update_at = fake.date_time_between(start_date=create_at, end_date="now")    # create_at 이후 ~ 현재

        data_buffer.append((
            comment_id,     # ID
            board_id,       # board_id
            fake.name(),    # author
            create_at,      # create_at
            update_at,      # update_at
            fake.text(),    # comment
        ))

        comment_id += 1  # ID 증가

    # 버퍼가 일정 크기 이상이면 DB에 저장
    if len(data_buffer) >= BATCH_SIZE:
        cursor.executemany(insert_sql, data_buffer)
        conn.commit()
        print(f"Inserted {len(data_buffer)} comments...")  # 진행 상황 출력
        data_buffer.clear()  # 버퍼 초기화

# 남아 있는 데이터 처리
if data_buffer:
    cursor.executemany(insert_sql, data_buffer)
    conn.commit()
    print(f"Inserted remaining {len(data_buffer)} comments.")

# DB 연결 닫기
cursor.close()
conn.close()
print("✅ 모든 데이터 삽입 완료!")