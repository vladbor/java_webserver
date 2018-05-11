begin;

drop schema if exists stepik cascade;
create schema stepik;

drop table if exists stepik.users;
create table stepik.users (
  id    serial             not null,
  name varchar(64) unique not null,
  password varchar(256)   not null,

  primary key (id)
);

commit;