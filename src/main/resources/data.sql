insert into sensor(name)
VALUES ('New York 1');
insert into sensor(name)
VALUES ('New York 2');
insert into sensor(name)
VALUES ('Moscow 1');
insert into sensor(name)
VALUES ('London 1');

insert into MEASUREMENT(SENSOR_ID, TEMPERATURE_VALUE, RAINING, CREATED_AT)
VALUES (1, 25, TRUE, '2023-05-01 00:00:00.0');
insert into MEASUREMENT(SENSOR_ID, TEMPERATURE_VALUE, RAINING, CREATED_AT)
VALUES (1, 24, TRUE, '2023-05-01 02:00:00.0');
insert into MEASUREMENT(SENSOR_ID, TEMPERATURE_VALUE, RAINING, CREATED_AT)
VALUES (1, 28.7, FALSE, '2023-05-02 00:00:00.0');
insert into MEASUREMENT(SENSOR_ID, TEMPERATURE_VALUE, RAINING, CREATED_AT)
VALUES (1, 29, FALSE, '2023-05-03 00:00:00.0');
insert into MEASUREMENT(SENSOR_ID, TEMPERATURE_VALUE, RAINING, CREATED_AT)
VALUES (2, -25, FALSE, NOW());
insert into MEASUREMENT(SENSOR_ID, TEMPERATURE_VALUE, RAINING, CREATED_AT)
VALUES (3, 10, FALSE, NOW());