import concurrent.futures
import requests
import random

url = "http://localhost:8080/test/order/new"

def send_request():
    member_id = random.randint(1, 10000000)
    products = [{"id": random.randint(1, 1000000),
                 "count": random.randint(1, 4)} for _ in range(random.randint(1, 10))]
    payload = {"memberId": member_id, "productDtos": products}
    response = requests.post(url, json=payload)
    return response.status_code, response.json()

with concurrent.futures.ThreadPoolExecutor(max_workers=50) as executor:
    futures = [executor.submit(send_request) for _ in range(100000)]
    for future in concurrent.futures.as_completed(futures):
        print(future.result())