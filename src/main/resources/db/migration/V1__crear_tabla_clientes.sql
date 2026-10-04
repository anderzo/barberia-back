CREATE TABLE clientes (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    apellido    VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL,
    telefono    VARCHAR(20),
    activo      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT ck_clientes_email CHECK (position('@' in email) > 1)
);

-- Email único sin distinguir mayúsculas
CREATE UNIQUE INDEX uq_clientes_email ON clientes (lower(email));

COMMENT ON TABLE clientes IS 'Clientes de la barbería';
COMMENT ON COLUMN clientes.activo IS 'Borrado lógico: false = cliente dado de baja';