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
('201C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 200, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('202C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 300, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('203C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 400, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('204C', (SELECT id FROM room_type t WHERE t.name='COMFORT'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 500, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('301L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 300, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('302L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 400, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('303L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 500, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('304L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 600, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('301L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 300, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('302L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 450, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('303L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 600, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('304L', (SELECT id FROM room_type t WHERE t.name='LUX'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 750, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('401L', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='SINGLE'), 500, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('402L', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='DOUBLE'), 800, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('403L', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='TRIPLE'), 1100, (SELECT id FROM room_status s WHERE s.name='AVAILABLE')),
('404L', (SELECT id FROM room_type t WHERE t.name='PRESIDENT'), (SELECT id FROM room_capacity c WHERE c.name='FAMILY'), 1300, (SELECT id FROM room_status s WHERE s.name='AVAILABLE'));
