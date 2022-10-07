drop schema if exists foodie;

create database foodie;

create table users (
    username varchar(16) not null,
    email varchar(128) not null,
    password varchar(60) not null,
    -- password must be 60 because of bcrypt encryption
    primary key (email)
)

create table savedRecipes (
    recipe_id int(32) not null primary key,
    email varchar(128) not null,
    constraint fk_email
        foreign key(email)
        references users(email)
)

create table post (
    post_id int auto increment not null primary key,
    email varchar(128) not null,
    recipe_id varchar(32) not null,
    title varchar(128) not null,
    description (254) not null,
    likes int not null,
    date Date not null,
    constraint fk_email
        foreign key(email)
        references users(email)
)
