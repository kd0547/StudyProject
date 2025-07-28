# JPA 적용 전 쿼리 테스트
# product id 조회가 여러 개인 상황

# ex(query) - 1
select *
from product
where id = 1 or id = 5;

# ex(query) - 2
select *
from product
where id IN (1,5);