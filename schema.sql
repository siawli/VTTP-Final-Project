drop schema if exists foodie;

create database foodie;

create table users {
    username varchar(16) not null,
    email varchar(128) not null,
    password varchar(60) not null,
    primary key (email)
}

create table allRecipes {
    recipe_id(16) not null primary key,
    email varchar(128) not null,
    constraint fk_email
        foreign key(email)
        references users(email)
}

create table myRecipes {
    recipe_id(16) not null primary key,
    email varchar(128) not null,
    constraint fk_email
        foreign key(email)
        references users(email)
}

create table recipe {
    recipe_id(16) not null primary key,
    email varchar(128) not null,
    title varchar(128) not null,
    description text not null,
    steps text not null,
    servings int,
    externalLink varchar(512),
    message varchar(254)
    constraint fk_email
        foreign key(email)
        references users(email)
}

create table ingredients {
    recipe_id(16) not null primary key,
    quantity varchar(32) not null,
    ingredient varchar(64) not null,
    constraint fk_recipe_id
        foreign key(recipe_id)
        references users(recipe_id)
}
