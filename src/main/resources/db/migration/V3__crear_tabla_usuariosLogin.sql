CREATE TABLE usuarios (
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email          VARCHAR(150) NOT NULL,
    password_hash  VARCHAR(255) NOT NULL,
    rol            VARCHAR(20)  NOT NULL DEFAULT 'CLIENTE',
    activo         BOOLEAN      NOT NULL DEFAULT TRUE,
    cliente_id     BIGINT UNIQUE REFERENCES clientes(id),
    barbero_id     BIGINT UNIQUE REFERENCES barberos(id),
    ultimo_login   TIMESTAMPTZ,
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at     TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT ck_usuarios_rol CHECK (rol IN ('ADMIN','BARBERO','CLIENTE'))
);

CREATE UNIQUE INDEX uq_usuarios_email ON usuarios (lower(email));