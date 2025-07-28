from faker import Faker
import requests
import random
fake = Faker()


url = "http://localhost:8080/test/order/new"

products = []

for _ in range(1000):
    member_id = random.randint(1, 10000000)
    num_products = random.randint(1, 10)

    for _ in range(num_products):
        product_id = random.randint(1, 1000000)
        count = random.randint(1, 4)

        products.append({
            "id": product_id,
            "count": count,
        })

    # 최종 payload 구성
    payload = {
        "memberId": member_id,
        "productDtos": products
    }

    print(payload)
    response = requests.post(url, json=payload)
    print("Status code:", response.status_code)
    print("Response:", response.json())
    products.clear()
    payload.clear()


