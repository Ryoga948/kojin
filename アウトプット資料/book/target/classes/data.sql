INSERT INTO user (id, username,password,role)
VALUES (1, "admin", '$2a$10$Ggxes0KEnd8K2vDzXB6Zde6Oo8h8qVDAB/lOx2P0y/A88ISddxqMC','ADMIN');

INSERT INTO member (id, name, email, zip_code, address, phone, birth, created_at)
VALUES (1, '阿井太郎', 'ai@dd.co.jp', '100-1234', '東京都', '080-1111-1111', '2000-03-12', '2020-03-12');
INSERT INTO member (id, name, email, zip_code, address, phone, birth, created_at)
VALUES (2, '伊田次郎', 'ida@ct.co.jp', '444-1234', '千葉県', '090-2222-2222','1974-10-02', '2020-03-12');
INSERT INTO member (id, name, email, zip_code, address, phone, birth, created_at)
VALUES (3, '江川太郎', 'egawa@dd.co.jp', '444-1234', '佐賀県', '090-4444-4444','1948-10-4', '2020-03-12');
INSERT INTO member (id, name, email, zip_code, address, phone, birth, created_at)
VALUES (4, '岡本太郎', 'okamoto@dd.co.jp', '444-1234', '埼玉県', '090-5555-5555','1972-10-5', '2020-03-12');
INSERT INTO member (id, name, email, zip_code, address, phone, birth, created_at)
VALUES (5, '甲斐三郎', 'kai@dd.co.jp', '444-1234', '徳島県', '090-6666-6666','1971-10-6', '2020-03-12');
INSERT INTO member (id, name, email, zip_code, address, phone, birth, created_at)
VALUES (6, '木田太郎', 'kida@dd.co.jp', '444-1234', '群馬県', '090-7777-7777','1970-3-7', '2020-03-12');
INSERT INTO member (id, name, email, zip_code, address, phone, birth, created_at)
VALUES (7, '草壁次郎', 'kusakabe@dd.co.jp', '444-1234', '高知県', '090-8888-8888','1966-5-8', '2020-03-12');

/* 任意機能から利用する初期データのdata.sql
INSERT INTO category (id, name) VALUES(1, '総記');
INSERT INTO category (id, name) VALUES(2, '哲学');
INSERT INTO category (id, name) VALUES(3, '歴史');
INSERT INTO category (id, name) VALUES(4, '社会科学');
INSERT INTO category (id, name) VALUES(5, '自然科学');
INSERT INTO category (id, name) VALUES(6, '技術');
INSERT INTO category (id, name) VALUES(7, '産業');
INSERT INTO category (id, name) VALUES(8, '芸術');
INSERT INTO category (id, name) VALUES(9, '言語');
INSERT INTO category (id, name) VALUES(10, '文学');

INSERT INTO book (id, isbn, name, author, publisher, published_at, category_id)
VALUES (1, '978-4-7981-6364-0', '人生を盛り上げるための11のレッスン', 'ドミニクノゲーズ', '青土社', '2005-11-11', 6);
INSERT INTO book (id, isbn, name, author, publisher, published_at, category_id)
VALUES (2, '978-4-7981-6751-1', 'ねこと仲良くなるための11のこつ', '辰巳一世', '筑摩書房', '2009-5-11', 6);
INSERT INTO book (id, isbn, name, author, publisher, published_at, category_id)
VALUES (3, '978-4-7981-6751-2', '税金はなぜ高いのか', '税博士', '筑摩書房', '2009-6-11', 7);
INSERT INTO book (id, isbn, name, author, publisher, published_at, category_id)
VALUES (4, '978-4-7981-6751-3', '金融のからくり', '利惟哉', '筑摩書房', '2019-5-11', 7);
INSERT INTO book (id, isbn, name, author, publisher, published_at, category_id)
VALUES (5, '978-4-7981-6751-4', '日本の歴史', '足利信長', '言論出版', '2010-8-20', 3);
INSERT INTO book (id, isbn, name, author, publisher, published_at, category_id)
VALUES (6, '978-4-7981-6751-5', '米国の歴史', 'グッシュ', '言論出版', '2012-7-11', 3);
INSERT INTO book (id, isbn, name, author, publisher, published_at, category_id)
VALUES (7, '978-4-7981-6751-6', 'わかりやすいJava', '益田陽一', '筑摩書房', '2018-4-10', 6);
INSERT INTO book (id, isbn, name, author, publisher, published_at, category_id)
VALUES (8, '978-4-7981-6751-7', 'DBリファレンス', '戸塚信二', '徳口出版', '2009-5-11', 6);
INSERT INTO book (id, isbn, name, author, publisher, published_at, category_id)
VALUES (9, '978-4-7981-6751-8', '戦争と試合', 'ノアドトーレス', '徳口出版', '2007-7-22', 3);
INSERT INTO book (id, isbn, name, author, publisher, published_at, category_id)
VALUES (10, '978-4-7981-6751-9', '摘み賭罰', 'ドストアイスキー', '徳口出版', '2001-3-25', 2);
INSERT INTO book (id, isbn, name, author, publisher, published_at, category_id)
VALUES (11, '978-4-7981-6710-1', '虫でもわかるPython', '内田与作', '言論出版', '2021-8-11', 6);
INSERT INTO book (id, isbn, name, author, publisher, published_at, category_id)
VALUES (12, '978-4-7981-6711-1', 'らくちんダイエット', '細区奈留代', '美エム出版', '2015-3-18', 6);
INSERT INTO book (id, isbn, name, author, publisher, published_at, category_id)
VALUES (13, '978-4-7981-6712-1', 'さんすうができるようになるほん', 'やまもとさや', '筑摩書房', '2005-9-15', 6);
INSERT INTO book (id, isbn, name, author, publisher, published_at, category_id)
VALUES (14, '978-4-7981-6713-1', '楽しい休日の過ごし方', 'ふるたまさあき', '青土社', '2012-6-30', 6);

INSERT INTO book_detail (disposal_at, lent, start_at, book_id, memo)
VALUES
('2015-12-01', 0x00, '2005-12-01', 1, '劣化のため廃棄'),
(NULL, 0x00, '2005-12-01', 1, ''),
(NULL, 0x01, '2005-12-01', 1, ''),
(NULL, 0x01, '1999-11-21', 2, ''),
(NULL, 0x00, '1999-11-21', 2, ''),
(NULL, 0x01, '1999-11-22', 2, ''),
(NULL, 0x00, '1999-11-23', 2, ''),
(NULL, 0x01, '2022-02-11', 3, ''),
(NULL, 0x01, '2022-11-22', 3, ''),
(NULL, 0x01, '2021-10-10', 4, ''),
(NULL, 0x01, '2022-02-02', 4, '');

INSERT INTO lend (created_at, returned_due_at, book_detail_id, member_id)
VALUES
('2024-01-04', '2024-01-19', 2, 1),
('2024-01-03', '2024-01-18', 3, 1),
('2024-01-05', '2024-01-20', 4, 3),
('2024-01-05', '2024-01-20', 6, 6),
('2024-01-05', '2024-01-20', 8, 1),
('2024-01-05', '2024-01-20', 9, 3),
('2024-01-05', '2024-01-20', 10, 6),
('2024-01-05', '2024-01-20', 11, 4);
*/
