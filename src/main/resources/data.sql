insert into sensor(name) VALUES ('New York 1');
insert into sensor(name) VALUES ('New York 2');
insert into sensor(name) VALUES ('Moscow 1');
insert into sensor(name) VALUES ('London 1');

insert into MEASUREMENT(SENSOR_ID, TEMPERATURE_VALUE, RAINING, CREATED_AT) VALUES ( 1, 25, FALSE, '2023-05-01 00:00:00.0' );
insert into MEASUREMENT(SENSOR_ID, TEMPERATURE_VALUE, RAINING, CREATED_AT) VALUES ( 1, 28.7, FALSE, '2023-05-02 00:00:00.0' );
insert into MEASUREMENT(SENSOR_ID, TEMPERATURE_VALUE, RAINING, CREATED_AT) VALUES ( 1, 29, FALSE, '2023-05-03 00:00:00.0' );
insert into MEASUREMENT(SENSOR_ID, TEMPERATURE_VALUE, RAINING, CREATED_AT) VALUES ( 2, -25, TRUE, NOW() );
insert into MEASUREMENT(SENSOR_ID, TEMPERATURE_VALUE, RAINING, CREATED_AT) VALUES ( 3, 10, FALSE, NOW() );

-- login: admin pass: 123123
INSERT INTO Person (username, password, role) VALUES ('admin', '$2a$10$CM6DEGnB8mfG07JjSK80AeVL0B5nFeoXgimoFnS006BqGYzY0fGAW', 'ROLE_ADMIN');
-- login: user pass: 123123
INSERT INTO Person (username, password, role) VALUES ('user', '$2a$10$hH54AhGVuyjRKZ8sH669cOLLzNIH.VAiiAmGl3GbaRMwXC6x7zow.', 'ROLE_USER');