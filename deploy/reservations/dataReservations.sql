/*
 TRUNCATE TABLE reservations CASCADE;
  TRUNCATE TABLE statuses CASCADE;
 */

INSERT INTO reservation_statuses (name)
VALUES
('IN_PROGRESS'),
('CONFIRMED'),
('REJECTED'),
('DELETED');

INSERT INTO reservations (user_id, total_cost, status_id)
VALUES
((SELECT id FROM users u WHERE u.email='antony_jeffery@einrot.com'), 100, (SELECT id FROM reservation_statuses s WHERE s.name='IN_PROGRESS')),
((SELECT id FROM users u WHERE u.email='antony_jeffery@einrot.com'), 750, (SELECT id FROM reservation_statuses s WHERE s.name='CONFIRMED')),
((SELECT id FROM users u WHERE u.email='rida_tran@teleworm.us'), 400, (SELECT id FROM reservation_statuses s WHERE s.name='IN_PROGRESS')),
((SELECT id FROM users u WHERE u.email='rida_tran@teleworm.us'), 500, (SELECT id FROM reservation_statuses s WHERE s.name='REJECTED')),
((SELECT id FROM users u WHERE u.email='sophia_mckenzie@kwontol.com'),200, (SELECT id FROM reservation_statuses s WHERE s.name='CONFIRMED')),
((SELECT id FROM users u WHERE u.email='sophia_mckenzie@kwontol.com'), 900, (SELECT id FROM reservation_statuses s WHERE s.name='CONFIRMED')),
((SELECT id FROM users u WHERE u.email='john-paul_blake@fleckens.hu'), 800, (SELECT id FROM reservation_statuses s WHERE s.name='IN_PROGRESS')),
 ((SELECT id FROM users u WHERE u.email='nannie_crawford@dayrep.com'), 3500, (SELECT id FROM reservation_statuses s WHERE s.name='CONFIRMED'));