INSERT INTO role (name) values('ROLE_USER');
INSERT INTO role (name) values('ROLE_MODERATOR');

INSERT INTO privilege (name) VALUES('READ_USER');

INSERT INTO privilege (name) VALUES('READ_MODERATOR');

INSERT INTO privilege (name) VALUES('WRITE_USER');

INSERT INTO privilege (name) VALUES('WRITE_MODERATOR');

INSERT INTO roles_privileges(role_id, privilege_id) VALUES(1,1);

INSERT INTO roles_privileges(role_id, privilege_id) VALUES(1,3);

INSERT INTO roles_privileges(role_id, privilege_id) VALUES(2,2);

INSERT INTO roles_privileges(role_id, privilege_id) VALUES(2,4);