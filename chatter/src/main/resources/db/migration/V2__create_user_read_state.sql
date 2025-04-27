CREATE TABLE user_read_state (
    username VARCHAR(50) NOT NULL,
    chat_room_id INT NOT NULL,
    last_read_at TIMESTAMP NOT NULL DEFAULT '1970-01-01 00:00:00',
    PRIMARY KEY (username, chat_room_id)
);