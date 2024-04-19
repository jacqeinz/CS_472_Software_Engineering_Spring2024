INSERT INTO guest (Name, homeAddress, dateOfBirth, email, phoneNumber, userName, userPassword) VALUES ('Jacqueline Chavez', '111 Roadway', '5/9/1996', 'user@gmail.com', '461-8786', 'user', 'password');
INSERT INTO admin (Name, homeAddress, dateOfBirth, email, phoneNumber, userName, userPassword) VALUES ('Jacqueline Chavez', '111 Roadway', '5/9/1996', 'user@gmail.com', '461-8786', 'user', 'password');
INSERT INTO room (roomNumber, roomType, price, floorNumber) VALUES ('Room 128', 'Queen', 199, 1);

INSERT INTO reservationDetails (paymentInformation) VALUES ('DebitCard');
INSERT INTO roomReservation (guest, room, total) VALUES (Jacqueline Chavez, Room 128, 299);
INSERT INTO cart (Guest, price, cartSize, roomReservation, total) values (Jacqueline Chavez, 1, roomReservation, 299)
INSERT INTO reservation (guest, dates, room) VALUES ('Jacqueline Chavez' ['02/02/2024'], Room 128);
