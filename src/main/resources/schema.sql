-- drop table if exists AGE_RATING cascade;
-- drop table if exists FILM cascade;
-- drop table if exists FILM_GENRE cascade;
-- drop table if exists FILM_LIKE cascade;
-- drop table if exists FOLLOWER cascade;
-- drop table if exists GENRE cascade;
-- drop table if exists "user" cascade;

create table if not exists AGE_RATING
(
    AGE_RATING_ID INTEGER auto_increment,
    NAME          CHARACTER VARYING,
    constraint AGE_RATING_PK
        primary key (AGE_RATING_ID)
);

create unique index if not exists AGE_RATING_NAME_UINDEX
    on AGE_RATING (NAME);

create table if not exists FILM
(
    FILM_ID       BIGINT auto_increment,
    NAME          CHARACTER VARYING,
    DESCRIPTION   CHARACTER VARYING,
    DURATION      INTEGER,
    RELEASE_DATE  TIMESTAMP,
    AGE_RATING_ID INTEGER,
    constraint FILM_PK
        primary key (FILM_ID),
    constraint FILM_AGE_RATING_AGE_RATING_ID_FK
        foreign key (AGE_RATING_ID) references AGE_RATING
);

create unique index if not exists FILM_NAME_UINDEX
    on FILM (NAME);

create table if not exists GENRE
(
    GENRE_ID BIGINT auto_increment,
    NAME     CHARACTER VARYING,
    constraint GENRE_PK
        primary key (GENRE_ID)
);

create table if not exists FILM_GENRE
(
    FILM_GENRE_ID BIGINT auto_increment,
    FILM_ID       BIGINT,
    GENRE_ID      BIGINT,
    constraint FILM_GENRE_PK
        primary key (FILM_GENRE_ID),
    constraint FILM_GENRE_FILM_FILM_ID_FK
        foreign key (FILM_ID) references FILM,
    constraint FILM_GENRE_GENRE_GENRE_ID_FK
        foreign key (GENRE_ID) references GENRE
);

create unique index if not exists FILM_GENRE_FILM_ID_GENRE_ID_UINDEX
    on FILM_GENRE (FILM_ID, GENRE_ID);

create unique index if not exists GENRE__UINDEX
    on GENRE (NAME);

create table if not exists "user"
(
    USER_ID  BIGINT auto_increment,
    NAME     CHARACTER VARYING,
    LOGIN    CHARACTER VARYING,
    EMAIL    CHARACTER VARYING,
    BIRTHDAY TIMESTAMP,
    constraint USER_PK
        primary key (USER_ID)
);

create table if not exists FILM_LIKE
(
    FILM_LIKE_ID BIGINT auto_increment,
    FILM_ID      BIGINT,
    USER_ID      BIGINT,
    constraint FILM_LIKE_PK
        primary key (FILM_LIKE_ID),
    constraint FILM_LIKE_FILM_FILM_ID_FK
        foreign key (FILM_ID) references FILM,
    constraint FILM_LIKE_USER_USER_ID_FK
        foreign key (USER_ID) references "user"
);

create unique index if not exists FILM_LIKE_FILM_ID_USER_ID_UINDEX
    on FILM_LIKE (FILM_ID, USER_ID);

create table if not exists FOLLOWER
(
    FOLLOWER_ID       BIGINT auto_increment,
    FOLLOWING_USER_ID BIGINT not null,
    FOLLOWED_USER_ID  BIGINT not null,
    IS_CONFIRM        BOOLEAN default FALSE,
    constraint FOLLOWER_PK
        primary key (FOLLOWER_ID),
    constraint FOLLOWER_USER_USER_ID_FK
        foreign key (FOLLOWING_USER_ID) references "user",
    constraint FOLLOWER_USER_USER_ID_FK_2
        foreign key (FOLLOWED_USER_ID) references "user"
);

create unique index if not exists FOLLOWER_FOLLOWED_USER_ID_FOLLOWING_USER_ID_UINDEX
    on FOLLOWER (FOLLOWED_USER_ID, FOLLOWING_USER_ID);

create unique index if not exists USER_LOGIN_UINDEX
    on "user" (LOGIN);

-- insert into AGE_RATING(NAME)
--     values ('G'),
--             ('PG'),
--             ('PG-13'),
--             ('R'),
--             ('NC-17');
--
-- insert into GENRE(NAME)
--     values ('Комедия'),
--            ('Драма'),
--            ('Мультфильм'),
--            ('Триллер'),
--            ('Документальный'),
--            ('Боевик');
