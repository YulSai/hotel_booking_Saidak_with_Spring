/*
 TRUNCATE TABLE reservation_info CASCADE;
 */

insert into reservation_info (reservation_id, room_id, check_in, check_out, nights, room_price)
values
(1, (select id from rooms r where r.room_number='101S'), '2022-08-05', '2022-08-06', 1, 100),
(2, (select id from rooms r where r.room_number='102S'), '2022-08-04', '2022-08-09', 5, 150),
(3, (select id from rooms r where r.room_number='103S'), '2022-08-08', '2022-08-10', 2, 200),
(4, (select id from rooms r where r.room_number='104S'),  '2022-08-08', '2022-08-10', 2, 250),
(5, (select id from rooms r where r.room_number='201S'), '2022-08-11', '2022-08-12', 1, 200),
(6, (select id from rooms r where r.room_number='202S'), '2022-08-10', '2022-08-13',3, 300),
(7, (select id from rooms r where r.room_number='203S'), '2022-08-10', '2022-08-12', 2,  400),
(8, (select id from rooms r where r.room_number='204S'), '2022-08-05', '2022-08-12', 7, 500);
