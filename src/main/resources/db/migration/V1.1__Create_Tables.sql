CREATE TABLE IF NOT EXISTS makes
(
    id SERIAL,
    name varchar(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT makes_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS models
(
    id SERIAL,
    name varchar(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT models_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS categories
(
    id SERIAL,
    name varchar(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT categoties_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cars
(
    id varchar(255),
    make_id integer,
    year integer,
    model_id integer,
    category_id integer,
    CONSTRAINT cars_pkey PRIMARY KEY (id),
    CONSTRAINT cars_makes_fkey FOREIGN KEY (make_id)
        REFERENCES public.makes (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT cars_models_fkey FOREIGN KEY (model_id)
        REFERENCES public.models (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT cars_categories_fkey FOREIGN KEY (category_id)
        REFERENCES public.categories (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users
(
    id SERIAL,
    username varchar(255) COLLATE pg_catalog."default" NOT NULL,
    password varchar(255) COLLATE pg_catalog."default" NOT NULL,
    role varchar(255) COLLATE pg_catalog."default" NOT NULL,
    account_non_expired boolean,
    account_non_locked boolean,
    credentials_non_expired boolean,
    enabled boolean,
    CONSTRAINT users_pkey PRIMARY KEY (id)
);