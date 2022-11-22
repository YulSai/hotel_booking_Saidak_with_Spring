/*
 TRUNCATE TABLE reservation_info CASCADE;
 */

INSERT INTO reservation_info (reservation_id, room_id, check_in, check_out, nights, room_price)
VALUES
(1, (SELECT id FROM rooms r WHERE r.room_number='101S'), '2022-08-05', '2022-08-06', 1, 100),
(2, (SELECT id FROM rooms r WHERE r.room_number='102S'), '2022-08-04', '2022-08-09', 5, 150),
(3, (SELECT id FROM rooms r WHERE r.room_number='103S'), '2022-08-08', '2022-08-10', 2, 200),
(4, (SELECT id FROM rooms r WHERE r.room_number='104S'),  '2022-08-08', '2022-08-10', 2, 250),
(5, (SELECT id FROM rooms r WHERE r.room_number='201S'), '2022-08-11', '2022-08-12', 1, 200),
(6, (SELECT id FROM rooms r WHERE r.room_number='202S'), '2022-08-10', '2022-08-13',3, 300),
(7, (SELECT id FROM rooms r WHERE r.room_number='203S'), '2022-08-10', '2022-08-12', 2,  400),
(8, (SELECT id FROM rooms r WHERE r.room_number='204S'), '2022-08-05', '2022-08-12', 7, 250);
