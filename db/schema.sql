CREATE TABLE IF NOT EXISTS site_credentials (
    id serial PRIMARY KEY,
    site varchar(100) UNIQUE NOT NULL,
    login varchar(50) UNIQUE NOT NULL,
    password varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS url_shortcuts (
    id serial PRIMARY KEY,
    url varchar(300) UNIQUE NOT NULL,
    code varchar(100) UNIQUE NOT NULL,
    total bigint default 0
);
