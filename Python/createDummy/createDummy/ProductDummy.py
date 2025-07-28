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

def generate_users(batch_size):
    users = []
    for _ in range(batch_size):
        users.append((
            faker.user_name(),
            faker.email(),
            faker.password(),
            faker.date_time_this_decade(),
            faker.date_time_this_year(),
            faker.first_name(),
            faker.last_name(),
            faker.date_of_birth(minimum_age=18, maximum_age=65),
            random.choice(['Male', 'Female', 'Other']),
            create_phoneNumber(),
            faker.street_address(),
            faker.city(),
            faker.postcode(),
            faker.image_url(),
            faker.text(max_nb_chars=200),
            random.randint(0, 1),
            random.choice(['User']),
        ))
    return users


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


