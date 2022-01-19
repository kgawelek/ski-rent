insert into public.accounts (user_id, email, password)
values  (1, 'admin@admin.com', 'admin'),
        (2, 'employee@employee.com', 'employee'),
        (3, 'client@client.com', 'client'),
        (8, 'email@email.com', 'password'),
        (9, 'email2@email.com', 'password'),
        (10, 'email3@email.com', 'password'),
        (11, 'testemail@email.com', 'password'),
        (12, 'testemail2@email.com', 'password'),
        (13, 'tesasastemail@email.com', 'asasdaqwead'),
        (14, 'asdasd@asdasd.com', 'asewtgdf'),
        (16, 'tes12312tasdq3email2@email.com', 'asdasfwetr'),
        (17, 'tasdaestemasdasdail@email.com', 'asdwerasd');
		
insert into public.admin (birthday, name, id)
values  (null, 'Admin', 1);

insert into public.checkups (id, date_from, date_to, done, equipment_id)
values  (1, '2022-01-18', '2022-01-22', false, 8);

insert into public.client (birthday, name, id)
values  (null, 'Sample Client', 3),
        ('2021-12-01', 'Imię Nazwisko', 8),
        ('2021-12-01', 'Imię Nazwisko', 9),
        ('2021-12-01', 'Imię Nazwisko', 10),
        ('2021-12-07', 'Testimie Testnazwisko', 11),
        ('2021-12-01', 'Testimie Testnazwisko', 12),
        ('2021-12-02', 'aaaa bbbb', 13),
        ('2021-12-02', 'asdasd asdasd', 14),
        ('2021-12-02', 'Piotr Testnazwisko', 16),
        ('2021-11-30', 'Piotr Testnazwisko', 17);
		
insert into public.employee (birthday, name, id)
values  (null, 'Sample Employee', 2);

insert into public.equipments (equipment_id, category, manufacturer, name, next_check_up, size)
values  (4, 'PANTS', 'Producent spodni', 'Po prostu spodnie', '2023-01-01', 'L'),
        (1, 'SNOWBOARD', 'Producent', 'Nazwa', '2021-12-17', '132'),
        (5, 'HELMET', 'Wed''ze', 'Protector', null, 'S'),
        (6, 'CAP', '4F', 'Nazwa czapki', null, 'ONESIZE'),
        (7, 'SKI_BOOTS', 'Producent', 'Nazwa', null, '44'),
        (8, 'SKI', 'Rossignol', 'SkiMaster', '2023-02-22', '144'),
        (9, 'SKI', 'Rossignol', 'Junior', '2022-12-12', '120'),
        (10, 'SKI_POLES', 'Rossignol', 'Nazwa', '2022-01-09', 'M'),
        (3, 'SNOWBOARD', 'Inny producent', 'Jakaś', '2022-02-02', '132'),
        (2, 'SKI', 'Producent', 'Inna nazwa', '2022-01-03', '120');

insert into public.prices (prices_id, category, days, price)
values  (1, 'SKI', 1, 70),
        (2, 'SKI', 3, 200),
        (3, 'SKI', 7, 400),
        (4, 'SNOWBOARD', 1, 80),
        (5, 'SNOWBOARD', 3, 220),
        (6, 'SNOWBOARD', 7, 450),
        (7, 'SKI_POLES', 1, 15),
        (8, 'SKI_POLES', 3, 35),
        (9, 'SKI_POLES', 7, 70),
        (10, 'GOGGLES', 1, 15),
        (11, 'GOGGLES', 3, 35),
        (12, 'GOGGLES', 7, 70),
        (13, 'SKI_BOOTS', 1, 30),
        (14, 'SKI_BOOTS', 3, 50),
        (15, 'SKI_BOOTS', 7, 110),
        (16, 'HELMET', 1, 20),
        (17, 'HELMET', 3, 45),
        (18, 'HELMET', 7, 100),
        (19, 'PANTS', 1, 15),
        (20, 'PANTS', 3, 40),
        (21, 'PANTS', 7, 90),
        (22, 'JACKET', 1, 20),
        (23, 'JACKET', 3, 50),
        (24, 'JACKET', 7, 110),
        (25, 'GLOVES', 1, 5),
        (26, 'GLOVES', 3, 10),
        (27, 'GLOVES', 7, 20),
        (28, 'BALACLAVAS', 1, 10),
        (29, 'BALACLAVAS', 4, 25),
        (30, 'BALACLAVAS', 7, 50),
        (31, 'CAP', 1, 10),
        (32, 'CAP', 3, 25),
        (33, 'CAP', 7, 50),
        (34, 'SCARF', 1, 10),
        (35, 'SCARF', 3, 25),
        (36, 'SCARF', 7, 50),
        (37, 'OTHER', 1, 20),
        (38, 'OTHER', 3, 50),
        (39, 'OTHER', 7, 100),
        (40, 'SNOWBOARD_BOOTS', 1, 40),
        (41, 'SNOWBOARD_BOOTS', 3, 100),
        (42, 'SNOWBOARD_BOOTS', 7, 210);
		
insert into public.rentals (id, date_from, date_to, accepted, deposit, returned, user_id)
values  (3, '2022-01-17', '2022-02-01', true, 400, false, 3),
        (4, '2022-02-12', '2022-02-15', true, 500, false, 11),
        (5, '2022-01-17', '2022-01-20', true, 500, false, 11);
		
insert into public.rentals_equipment_set (rental_id, equipment_set_equipment_id)
values  (3, 1),
        (3, 5),
        (4, 1),
        (4, 9),
        (5, 10);
		
insert into public.repairs (id, date_from, date_to, done, reason, equipment_id)
values  (1, '2022-02-12', '2022-02-15', false, 'Dziura w czapce', 6);