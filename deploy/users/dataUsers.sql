/*
 * TRUNCATE TABLE users CASCADE;
 * TRUNCATE TABLE roles CASCADE;
 */

INSERT INTO roles (name)
VALUES
('ROLE_ADMIN'),
('ROLE_CLIENT');

INSERT INTO users (username, first_name, last_name, email, password, phone_number, role_id, avatar)
VALUES
('maxim_hammond', 'Maxim', 'Hammond', 'maxim_hammond@kwontol.com', '$2a$10$UJV2JQ2l.ToXTJdwJQ1PgOdGMioGcUmy6AJY2BoQ6pQiXFGPQjfLa', '+48511906624', (SELECT id FROM roles c WHERE c.name = 'ROLE_ADMIN'), 'avatar01.png'),
('adem_johnson', 'Adem', 'Johnson', 'adem_johnson@armyspy.com', '$2a$10$I1VtbFhHQ4hqo9Yk0ygvgeocJ9e1dI84L7pIEbv9fwmg/72Q6r6MK', '+485101085423', (SELECT id FROM roles c WHERE c.name = 'ROLE_ADMIN'), 'avatar02.png'),
('reese_mckenzie', 'Reese', 'Mckenzie', 'reese_mckenzie@cuvox.de', '$2a$10$6xAjpaiSfp65nrjvJVj3Ae4mw8cxZBmTkofecZm8nPKoh4LxUveZK', '+375295741238', (SELECT id FROM roles c WHERE c.name = 'ROLE_ADMIN'), 'avatar03.png'),
('saad_nielsen', 'Saad', 'Nielsen','saad_nielsen@dayrep.com', '$2a$10$fE9XPcLv53jPOmL2Fuv3nuDMYQRFxJh0XqgrzZDMByTaWQ0bFcm6S', '+375335213890', (SELECT id FROM roles c WHERE c.name = 'ROLE_CLIENT'), 'avatar04.png'),
('alesha_peterson', 'Alesha', 'Peterson', 'alesha_peterson@einrot.com', '$2a$10$xv8t0va6wsncWUw8b.5T4.1WyESxYPmdTtKcinsORuoaaMBXcskzi', '+48458632147', (SELECT id FROM roles c WHERE c.name = 'ROLE_CLIENT'), 'avatar05.png'),
('anne-marie_leon', 'Anne-Marie', 'Leon', 'anne-marie_leon@fleckens.hu','$2a$10$hg3y3vkgV9a9HLg/IolWhOltAL7HeW.QynZLd.YGv4qvKWF/XYNlO', '+38789254632', (SELECT id FROM roles c WHERE c.name = 'ROLE_CLIENT'), 'avatar06.png'),
('muneeb_cousins', 'Muneeb', 'Cousins', 'muneeb_cousins@gustr.com', '$2a$10$wjcv87VL2wifjxoi6E52vu3Op62SswJIsi2B8SblXbOgU4PSICkOi', '+38128456208', (SELECT id FROM roles c WHERE c.name = 'ROLE_CLIENT'), 'avatar07.png'),
('carol_rivas', 'Carol', 'Rivas', 'carol_rivas@jourrapide.com', '$2a$10$YMTzT55uyyMn3jTWOHpttehrnUTZZhn/GgUcsuDhC5Enyf191By9G', '+48123548697', (SELECT id FROM roles c WHERE c.name = 'ROLE_CLIENT'), 'avatar08.png'),
('braiden_allan', 'Braiden', 'Allan', 'braiden_allan@rhyta.com', '$2a$10$4aVFYKVYkBQB75eU7vGm9OSo4s55Dj/LHKdke2MuQ0aVp4BdHSiqC', '+34258159641', (SELECT id FROM roles c WHERE c.name = 'ROLE_CLIENT'), 'avatar09.png'),
('jaya_nielsenl', 'Jaya', 'Nielsen', 'jaya_nielsenl@superrito.com', '$2a$10$JDzSaXxLzy9JQBgpgHZpD.FsK6zaMovriZWuF.W1rerc.o3huAixK', '+34208962145', (SELECT id FROM roles c WHERE c.name = 'ROLE_CLIENT'), 'avatar10.png'),
('kristian_hanson', 'Kristian', 'Hanson', 'kristian_hanson@teleworm.us', '$2a$10$/VtSrf./PpMhH8kymSPwTerGYYxGKhOD21kAcpq2M3OmQ9aD6SenO', '+48124569783', (SELECT id FROM roles c WHERE c.name = 'ROLE_CLIENT'), 'avatar11.png'),
('cosmo_franklin', 'Cosmo', 'Franklin', 'cosmo_franklin@kwontol.com', '$2a$10$Z5W88QhfZa4adBOiclQET.BpDt0s7mJZ5wqh5pRw7RN85WZ/834z2', '+375442596872', (SELECT id FROM roles c WHERE c.name = 'ROLE_CLIENT'), 'avatar12.png'),
('isa_mckenzie', 'Isa', 'Mckenzie', 'isa_mckenzie@armyspy.com', '$2a$10$W.p3U8FQAiQ9sf6CtWLlvOsbxXtUt5.wr/K7NAqWbqqHOaqotoGOW', '+375445236987', (SELECT id FROM roles c WHERE c.name = 'ROLE_CLIENT'), 'avatar13.png'),
('denis_wormald', 'Denis', 'Wormald', 'denis_wormald@cuvox.de', '$2a$10$nrHy92LjvScnkg22t3QZuOCZwB8aLoO89DbJ4xc.fuzrj2E0EyEwi', '+48145369875', (SELECT id FROM roles c WHERE c.name = 'ROLE_CLIENT'), 'avatar14.png'),
('nannie_crawford', 'Nannie', 'Crawford', 'nannie_crawford@dayrep.com', '$2a$10$rxEH6v/savRa1o0Z6SOXH.5ypJmYULIFfw5q1q5ItsSm9deyTRhKS', '+38549782654', (SELECT id FROM roles c WHERE c.name = 'ROLE_CLIENT'), 'avatar15.png'),
('antony_jeffery', 'Antony', 'Jeffery', 'antony_jeffery@einrot.com', '$2a$10$qXcD7f6Vo1aeEMAZQitet.m7hkINJ5yexvB4E693/FV.dL90gA1Ue', '+375254781235', (SELECT id FROM roles c WHERE c.name = 'ROLE_CLIENT'), 'avatar16.png'),
('john-paul_blake', 'John-Paul', 'Blake', 'john-paul_blake@fleckens.hu', '$2a$10$XwwdPWpZ0NueiBZycmFh.empJNzY8B8bQoYKgwrXc6/euLJMMhWqu', '+38547632987', (SELECT id FROM roles c WHERE c.name = 'ROLE_CLIENT'), 'avatar17.png'),
('jocelyn_porter', 'Jocelyn', 'Porter', 'jocelyn_porter@rhyta.com', '$2a$10$N4YAYgGXEvoirbTUTg637eFulMEzqPUypXRiZr18idoDX4qlPkEym', '+48215369742', (SELECT id FROM roles c WHERE c.name = 'ROLE_CLIENT'), 'avatar18.png'),
('rida_tran', 'Rida', 'Tran', 'rida_tran@teleworm.us', '$2a$10$mukXOcPXIipuie.u60hm9ekWLdGy6Pzp9LUbVUCVlCmBpaS6ACgn2', '+375296543218', (SELECT id FROM roles c WHERE c.name = 'ROLE_CLIENT'), 'avatar19.png'),
('sophia_mckenzie', 'Sophia', 'Mckenzie', 'sophia_mckenzie@kwontol.com', '$2a$10$wKOCFsL8WyOGLEO2RxH4/.xdGdd5D2GJMAxD3XYj1gU6.kllM0uCK', '+375256982148', (SELECT id FROM roles c WHERE c.name = 'ROLE_CLIENT'), 'avatar20.png');
