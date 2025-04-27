CREATE TABLE notification_settings (
    username VARCHAR(50) NOT NULL,
    chat_room_id INT NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (username, chat_room_id)
);