create table user
(
id INT PRIMARY KEY NOT NULL,
name VARCHAR(100) NOT NULL,
password VARCHAR(100) NOT NULL,
enabled BIT(1)
);

create table role 
(
id INT PRIMARY KEY NOT NULL,
name VARCHAR(100) NOT NULL
);

create table user_role
(
user_id INT NOT NULL,
role_id INT NOT NULL,
PRIMARY KEY(user_id, role_id),
FOREIGN KEY (user_id) REFERENCES user(id),
FOREIGN KEY (role_id) REFERENCES role(id)
);
insert into user(id,name,password) values (1, 'user', 'user');
insert into user(id,name,password) values (2, 'admin', 'admin');
insert into role(id,name) values (1, 'user');
insert into role(id,name) values (2, 'admin');
insert into user_role (user_id,role_id) values (1,1);
insert into user_role (user_id,role_id) values (2,1);
insert into user_role (user_id,role_id) values (2,2);