from time import sleep

import pymysql
import random
from faker import Faker
from datetime import datetime, timedelta
import sys
from tqdm import tqdm

faker = Faker('ko_KR')

def create_phoneNumber():
    return f"010-{random.randint(0000,9999):04d}-{random.randint(0000,9999):04d}"


def insert_data(query,data):
    try:
        conn = pymysql.connect(
            host='localhost',
            port=3306,
            user='test_user',
            passwd='1234',
            db='learn_db',
            charset='utf8'
        )
        cursor = conn.cursor()
        cursor.executemany(query, data)
        conn.commit()
    except Exception as e:
        print(f"Error: {e}")
    finally:
        if cursor:
            cursor.close()
        if conn:
            conn.close()


def insert_parent_categories():
    parent_categories = [
        "식료품", "가전제품", "컴퓨터 부품", "가구", "의류", "신발", "전자기기", "장난감", "스포츠 용품", "도서",
        "화장품", "건강식품", "악기", "자동차 용품", "애완동물 용품", "사무용품", "정원용품", "주방용품", "여행용품", "예술품"
    ]

    categories = []
    for parent_name in parent_categories:
        categories.append((
            parent_name,  # CategoryName
            None,  # ParentID
            faker.text(max_nb_chars=200),  # Description
            faker.image_url(),  # ImageURL
            random.choice([True, False]),  # IsActive
            faker.date_time_this_decade(),  # CreatedAt
            faker.date_time_this_decade(),  # UpdatedAt
            faker.sentence(),  # MetaTitle
            faker.text(max_nb_chars=200),  # MetaDescription
            faker.words(nb=5, unique=True)  # MetaKeywords
        ))

    parent_query = """
        INSERT INTO Categories (
            CategoryName, ParentID, Description, ImageURL, IsActive,
            CreatedAt, UpdatedAt, MetaTitle, MetaDescription, MetaKeywords
        ) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
    """
    insert_data(parent_query, categories)

# 하위 카테고리 데이터 삽입 함수
def insert_child_categories():
    try:
        conn = pymysql.connect(
            host='localhost',
            port=3306,
            user='test_user',
            passwd='1234',
            db='learn_db',
            charset='utf8'
        )
        cursor = conn.cursor()

        cursor.executemany("SELECT CategoryID FROM Categories WHERE ParentID IS NULL")
        parent_ids = [row[0] for row in cursor.fetchall()]

        categories = []
        for parent_id in parent_ids:
            num_subcategories = random.randint(3, 7)  # 각 상위 카테고리에 3~7개 하위 카테고리 생성
            for _ in range(num_subcategories):
                categories.append((
                    faker.word().capitalize(),  # 하위 카테고리 이름
                    parent_id,  # ParentID
                    faker.text(max_nb_chars=200),  # Description
                    faker.image_url(),  # ImageURL
                    random.choice([True, False]),  # IsActive
                    faker.date_time_this_decade(),  # CreatedAt
                    faker.date_time_this_decade(),  # UpdatedAt
                    faker.sentence(),  # MetaTitle
                    faker.text(max_nb_chars=200),  # MetaDescription
                    faker.words(nb=5, unique=True)  # MetaKeywords
                ))
        return categories


    except Exception as e:
        print(f"에러 발생: {e}")
    finally:
        if cursor:
            cursor.close()
        if conn:
            conn.close()



if __name__ == "__main__":
    # 사용자 데이터 삽입
    user_query = """
        INSERT INTO Users (
            Username, Email, Password, JoinDate, LastLogin,
            FirstName, LastName, DateOfBirth, Gender, PhoneNumber,
            Address, City, PostalCode,
            ProfilePictureURL, Bio, Status, UserType
        ) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
    """

    total = 5000000

    batch_size = 10000

    total_batches = total // batch_size
    with tqdm(total=total_batches, desc="상품 데이터 생성 및 삽입 진행") as pbar:
        for _ in range(total_batches):
            user_data = generate_users(batch_size)
            insert_data(user_query, user_data)
            sleep(0.5)
            pbar.update(1)


