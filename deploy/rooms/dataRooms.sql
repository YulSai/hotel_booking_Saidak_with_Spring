/*
 TRUNCATE TABLE rooms CASCADE;.
 TRUNCATE TABLE room_status CASCADE;
 TRUNCATE TABLE room_type CASCADE;
 TRUNCATE TABLE room_capacity CASCADE;
 */

INSERT INTO room_status (name)
VALUES
('AVAILABLE'),
('UNAVAILABLE');

INSERT INTO room_type (name)
VALUES
('STANDARD'),
('COMFORT'),
('LUX'),
('PRESIDENT');

INSERT INTO room_capacity (name)
VALUES
('SINGLE'),
('DOUBLE'),
('TRIPLE'),
('FAMILY');


INSERT INTO rooms (room_number, room_type_id, room_capacity_id, price, room_status_id)
VALUES
('101S', (SELECT id FROM room_type t WHERE t.name='STANDARD'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 100, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('102S', (SELECT id FROM room_type t WHERE t.name='STANDARD'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 150, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('103S', (SELECT id FROM room_type t WHERE t.name='STANDARD'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 200, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('104S', (SELECT id FROM room_type t WHERE t.name='STANDARD'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 250, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('201S', (SELECT id FROM room_type t WHERE t.name='STANDARD'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 100, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('202S', (SELECT id FROM room_type t WHERE t.name='STANDARD'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 150, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('203S', (SELECT id FROM room_type t WHERE t.name='STANDARD'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 200, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('204S', (SELECT id FROM room_type t WHERE t.name='STANDARD'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 250, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('301S', (SELECT id FROM room_type t WHERE t.name='STANDARD'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 100, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('302S', (SELECT id FROM room_type t WHERE t.name='STANDARD'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 150, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('303S', (SELECT id FROM room_type t WHERE t.name='STANDARD'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 200, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('304S', (SELECT id FROM room_type t WHERE t.name='STANDARD'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 250, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('401S', (SELECT id FROM room_type t WHERE t.name='STANDARD'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 100, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('402S', (SELECT id FROM room_type t WHERE t.name='STANDARD'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 150, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('403S', (SELECT id FROM room_type t WHERE t.name='STANDARD'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 200, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('404S', (SELECT id FROM room_type t WHERE t.name='STANDARD'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 250, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('101C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 200, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('102C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 300, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('103C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 400, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('104C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 500, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('201C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 200, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('202C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 300, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('203C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 400, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('204C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 500, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('301C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 200, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('302C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 300, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('303C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 400, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('304C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 500, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('401C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 200, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('402C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 300, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('403C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 400, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('404C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 500, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('101L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 300, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('102L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 400, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('103L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 500, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('104L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 600, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('201L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 300, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('202L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 400, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('203L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 500, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('204L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 600, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('301L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 300, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('302L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 400, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('303L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 500, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('304L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 600, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('401L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 300, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('402L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 400, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('403L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 500, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('404L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 600, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('101P', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 500, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('102P', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 800, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('103P', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 1100, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('104P', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 1300, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('201P', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 500, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('202P', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 800, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('203P', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 1100, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('204P', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 1300, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('301P', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 500, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('302P', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 800, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('303P', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 1100, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('304P', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 1300, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('401P', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 500, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('402P', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 800, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('403P', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 1100, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('404P', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 1300, (SELECT id FROM room_status s WHERE s.name='AVAILABLE'));
