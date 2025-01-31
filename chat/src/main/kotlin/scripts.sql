create table public.users
(
    id bigserial primary key,
    login varchar(255) not null unique,
    password_hash varchar(255),
    last_name  varchar(255) not null,
    first_name varchar(255),
    gender varchar(6), --6 потому что либо male(4), female(6), other(5)- то есть маскимальная длина 6, больше не нужно, так как будет избыточно
    age INT

);

create table public.reactions
(
    id bigserial primary key,
    reactions_type_id bigint not null references public.reactions_type(id),
    sender_type_id bigint not null references public.users(id),
    receiver_type_id bigint not null references public.users(id)
);


create table public.reactions_type
(
    id bigserial primary key,
    type varchar(255),
    emotion_type varchar(255),
    emoji varchar(255)
)

--база данных состоит из 3 таблиц
-- таблица users предназначена для хранения данных о пользователях (их id, логин, хэш пароля, имя, фамилия, возраст и пол)
--таблица reactions создана для того, чтобы хранить данные о реакциях (кто отправил, кому отправил - соответственно эти поля связаны с таблицей users)
-- таблица reactions_type создана для того, чтобы хранить информацию о реакциях (какая конкретно реакция, то есть ее тип)