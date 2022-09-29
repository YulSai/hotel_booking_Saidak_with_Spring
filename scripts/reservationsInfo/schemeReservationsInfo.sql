/*
DROP TABLE IF EXISTS reservation_info;
*/

CREATE TABLE IF NOT EXISTS reservation_info (
id BIGSERIAL PRIMARY KEY,
reservation_id BIGSERIAL NOT NULL REFERENCES reservations,
room_id BIGINT NOT NULL REFERENCES rooms,
check_in date NOT NULL,
check_out date NOT NULL,
nights BIGINT NOT NULL,
room_price DECIMAL,
deleted BOOLEAN NOT NULL DEFAULT FALSE
);