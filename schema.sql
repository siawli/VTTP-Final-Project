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

 create table posts (
    post_id int auto_increment not null primary key,
    email varchar(128) not null,
    title varchar(128) not null,
    caption varchar(254) not null,
    recipe_id varchar(32) not null,
    ratings float not null,
    likes int not null,
    date varchar(16) not null,
    imageUUID varchar(8) not null,
    constraint fk_email
        foreign key(email)
        references users(email)
        on delete cascade
)

create table likedPosts (
    email varchar(128) not null,
    post_id int not null,
    primary key(email, post_id)
)

create table savedRecipes (
    email varchar(128) not null,
    recipe_id varchar(32) not null,
    primary key(email, recipe_id)
)
