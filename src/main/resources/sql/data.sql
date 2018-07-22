INSERT INTO USER (ID, USERNAME, PASSWORD, ENABLED, LASTPASSWORDRESETTIME) VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 1, PARSEDATETIME('2018-07-21', 'yyyy-MM-dd'));
INSERT INTO USER (ID, USERNAME, PASSWORD, ENABLED, LASTPASSWORDRESETTIME) VALUES (2, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 1, PARSEDATETIME('2018-07-21','yyyy-MM-dd'));
INSERT INTO USER (ID, USERNAME, PASSWORD, ENABLED, LASTPASSWORDRESETTIME) VALUES (3, 'disabled', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 0, PARSEDATETIME('2018-07-21','yyyy-MM-dd'));

INSERT INTO AUTHORITY (ID, NAME) VALUES (1, 'ADMIN');
INSERT INTO AUTHORITY (ID, NAME) VALUES (2, 'USER');

INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (1, 2);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (2, 2);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (3, 2);
