select * from board;

select count(id)
from board;

#인덱스가 생성되지 않았을 경우 소요시간은 7s
#인덱스가 생서된 후 소요시간은 약 284ms
select * from board
order by create_at desc
limit 10 offset 10;

select * from board
order by create_at desc
limit 100 offset 0;


create index board_index_page ON board(create_at,id)