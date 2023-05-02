insert into sensor(name) VALUES ('New York 1');
insert into sensor(name) VALUES ('New York 2');
insert into sensor(name) VALUES ('Paris 1');
insert into sensor(name) VALUES ('London 1');

-- login: admin pass: 123123
INSERT INTO Person (username, password, role) VALUES ('admin', '$2a$10$CM6DEGnB8mfG07JjSK80AeVL0B5nFeoXgimoFnS006BqGYzY0fGAW', 'ROLE_ADMIN');
-- login: user pass: 123123
INSERT INTO Person (username, password, role) VALUES ('user', '$2a$10$hH54AhGVuyjRKZ8sH669cOLLzNIH.VAiiAmGl3GbaRMwXC6x7zow.', 'ROLE_USER');
