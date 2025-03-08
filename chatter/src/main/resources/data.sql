INSERT INTO messages (user_id, chat_room_id, message, is_read, created_at) VALUES
('sou', 1, 'こんにちは！', false, NOW()),
('sou', 1, 'はじめまして！', true, NOW() - INTERVAL '1 hour'),
('sou', 1, 'よろしくお願いします！', true, NOW() - INTERVAL '30 minute');

INSERT INTO users (name, password) VALUES
('アリス', 'alice123'),
('ボブ', 'bob123');

INSERT INTO chat_rooms (room_name, created_at) VALUES
('雑談部屋', NOW());

INSERT INTO authentications (username,password,icon_url) VALUES 
('admin', '$2a$10$yGJhkW8baTD7ZiE1Q8Siq.Mo99q2Bj4qdoNk5aznEvr269j6R697u',NULL),
('sou','$2a$10$GgttR6BKPpIIQ/uVyqa7YuEYdXULvXZghfRz6.HezmaUwbJu.nmCm','/images/sou.png'),
('naoko','$2a$10$DEOI4xODMOTC1fDx8ehagOvwZDaF2DsjeGRp5.FKeERx7RqK5h3wW',NULL);