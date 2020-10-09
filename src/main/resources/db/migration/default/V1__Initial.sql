create table users(id serial primary key, user_name varchar(50),latitude decimal, longitude decimal);

create table driver(id serial primary key, driver_name varchar(50), free_status bool, latitude decimal, longitude decimal);

create table driver_assignment_status(id serial primary key, driver_id integer references driver(id),
user_id integer references users(id), dest_lat decimal, dest_long decimal, ride_status varchar(20));