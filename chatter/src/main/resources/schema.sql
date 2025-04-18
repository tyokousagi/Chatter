--DROP TABLE IF EXISTS users;
--DROP TABLE IF EXISTS chat_rooms;
--DROP TABLE IF EXISTS messages;
--DROP TABLE IF EXISTS authentications;

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS chat_rooms (
    id SERIAL PRIMARY KEY,
    room_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS messages (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(50),
    chat_room_id INT,
    message TEXT,
    is_read BOOLEAN,
    created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS authentications (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    icon_url VARCHAR(255)
);