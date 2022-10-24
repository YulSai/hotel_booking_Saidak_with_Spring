/*
 * TRUNCATE TABLE users CASCADE;
 * TRUNCATE TABLE roles CASCADE;
 */

INSERT INTO roles (name)
VALUES
('ADMIN'),
('CLIENT'),
('GUEST');

INSERT INTO users (first_name, last_name, email, password, phone_number, role_id, avatar)
VALUES
('Maxim', 'Hammond', 'maxim_hammond@kwontol.com', 'DFD0CCF4CEF833270A89F57894F7532443868960', '+48511906624', (SELECT id FROM roles c WHERE c.name = 'ADMIN'), 'avatar01.png'),
('Adem', 'Johnson', 'adem_johnson@armyspy.com', 'ED8F7406C72A5BE96D5CCD4A0AF66D43109E2B5', '+485101085423', (SELECT id FROM roles c WHERE c.name = 'ADMIN'), 'avatar02.png'),
('Reese', 'Mckenzie', 'reese_mckenzie@cuvox.de', 'C213B18EBEF1F9E309B65EEA1EE9AC8E77EECA29', '+375295741238', (SELECT id FROM roles c WHERE c.name = 'ADMIN'), 'avatar03.png'),
('Saad', 'Nielsen','saad_nielsen@dayrep.com', 'C8ED25D7F4B8A8419A9311984142BE4E2715D6E1', '+375335213890', (SELECT id FROM roles c WHERE c.name = 'CLIENT'), 'avatar04.png'),
('Alesha', 'Peterson', 'alesha_peterson@einrot.com', 'D155C6775F215119BE912550F86EB96EC8E61318', '+48458632147', (SELECT id FROM roles c WHERE c.name = 'CLIENT'), 'avatar05.png'),
('Anne-Marie', 'Leon', 'anne-marie_leon@fleckens.hu','E4859DABE8E9DAC57403E3C6EDBFC9612B9A5EB5', '+38789254632', (SELECT id FROM roles c WHERE c.name = 'CLIENT'), 'avatar06.png'),
('Muneeb', 'Cousins', 'muneeb_cousins@gustr.com', '9E31494185FDD60DA0AFDC41EE50D19BB0D1EBC6', '+38128456208', (SELECT id FROM roles c WHERE c.name = 'CLIENT'), 'avatar07.png'),
('Carol', 'Rivas', 'carol_rivas@jourrapide.com', '51C7ABBADE2F928F8D39D93357798ECF51298A12', '+48123548697', (SELECT id FROM roles c WHERE c.name = 'CLIENT'), 'avatar08.png'),
('Braiden', 'Allan', 'braiden_allan@rhyta.com', '5173EDE7A9D1351958C66CD6380D2226B1AF7A46', '+34258159641', (SELECT id FROM roles c WHERE c.name = 'CLIENT'), 'avatar09.png'),
('Jaya', 'Nielsen', 'jaya_nielsenl@superrito.com', '86D2C5D58B84E2F0264716E713CA2B400ECD74F0', '+34208962145', (SELECT id FROM roles c WHERE c.name = 'CLIENT'), 'avatar10.png'),
('Kristian', 'Hanson', 'kristian_hanson@teleworm.us', '391F5D3C0CC1C28DC91B16DB178B84A54FEB8DD5', '+48124569783', (SELECT id FROM roles c WHERE c.name = 'CLIENT'), 'avatar11.png'),
('Cosmo', 'Franklin', 'cosmo_franklin@kwontol.com', '6E184250F2D910904F5FE22A7FC9DCC8615B6F45', '+375442596872', (SELECT id FROM roles c WHERE c.name = 'CLIENT'), 'avatar12.png'),
('Isa', 'Mckenzie', 'isa_mckenzie@armyspy.com', '25EDA2B2292306848811AC1661D2CB86EA45A0C7', '+375445236987', (SELECT id FROM roles c WHERE c.name = 'CLIENT'), 'avatar13.png'),
('Denis', 'Wormald', 'denis_wormald@cuvox.de', 'AB4D89CE9158A9C3F70BA505CB5FF51B5DB713F3', '+48145369875', (SELECT id FROM roles c WHERE c.name = 'CLIENT'), 'avatar14.png'),
('Nannie', 'Crawford', 'nannie_crawford@dayrep.com', '3DE65B69A77DB9DD7B8279B10995552D6BAF1CB9', '+38549782654', (SELECT id FROM roles c WHERE c.name = 'CLIENT'), 'avatar15.png'),
('Antony', 'Jeffery', 'antony_jeffery@einrot.com', '2EC7C7DB7829E04A2E88CDB6D1CB5BA775107CFF', '+375254781235', (SELECT id FROM roles c WHERE c.name = 'CLIENT'), 'avatar16.png'),
('John-Paul', 'Blake', 'john-paul_blake@fleckens.hu', 'F808CFC088A56260431D2FE4AD95ADFA3425999E', '+38547632987', (SELECT id FROM roles c WHERE c.name = 'CLIENT'), 'avatar17.png'),
('Jocelyn', 'Porter', 'jocelyn_porter@rhyta.com', 'B47463361E1F4447ECE480AE1937D88FA22EAA08', '+48215369742', (SELECT id FROM roles c WHERE c.name = 'CLIENT'), 'avatar18.png'),
('Rida', 'Tran', 'rida_tran@teleworm.us', '5D92FF7CD144E27FBA5C9939CCA3E44B06C3C88C', '+375296543218', (SELECT id FROM roles c WHERE c.name = 'CLIENT'), 'avatar19.png'),
('Sophia', 'Mckenzie', 'sophia_mckenzie@kwontol.com', '683319EFF885EA3D988487CC319CE12ECB8F9F84', '+375256982148', (SELECT id FROM roles c WHERE c.name = 'CLIENT'), 'avatar20.png');
