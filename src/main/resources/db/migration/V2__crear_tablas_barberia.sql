CREATE EXTENSION IF NOT EXISTS btree_gist;

CREATE TABLE barberos (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    apellido    VARCHAR(100) NOT NULL,
    telefono    VARCHAR(20),
    activo      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE TABLE servicios (
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre       VARCHAR(100)  NOT NULL,
    descripcion  VARCHAR(255),
    duracion_min INTEGER       NOT NULL,
    precio       NUMERIC(10,2) NOT NULL,
    activo       BOOLEAN       NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMPTZ   NOT NULL DEFAULT now(),
    updated_at   TIMESTAMPTZ   NOT NULL DEFAULT now(),
    CONSTRAINT ck_servicios_duracion CHECK (duracion_min > 0),
    CONSTRAINT ck_servicios_precio   CHECK (precio >= 0)
);

-- Qué servicios ofrece cada barbero (N:M)
CREATE TABLE barbero_servicios (
    barbero_id  BIGINT NOT NULL REFERENCES barberos(id),
    servicio_id BIGINT NOT NULL REFERENCES servicios(id),
    PRIMARY KEY (barbero_id, servicio_id)
);

-- Horario semanal de cada barbero (1 = lunes ... 7 = domingo)
CREATE TABLE horarios_barbero (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    barbero_id  BIGINT   NOT NULL REFERENCES barberos(id),
    dia_semana  SMALLINT NOT NULL,
    hora_inicio TIME     NOT NULL,
    hora_fin    TIME     NOT NULL,
    CONSTRAINT ck_horarios_dia   CHECK (dia_semana BETWEEN 1 AND 7),
    CONSTRAINT ck_horarios_horas CHECK (hora_fin > hora_inicio)
);

CREATE TABLE citas (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cliente_id  BIGINT        NOT NULL REFERENCES clientes(id),
    barbero_id  BIGINT        NOT NULL REFERENCES barberos(id),
    servicio_id BIGINT        NOT NULL REFERENCES servicios(id),
    inicio      TIMESTAMPTZ   NOT NULL,
    fin         TIMESTAMPTZ   NOT NULL,
    estado      VARCHAR(20)   NOT NULL DEFAULT 'PENDIENTE',
    precio      NUMERIC(10,2) NOT NULL,
    notas       VARCHAR(255),
    created_at  TIMESTAMPTZ   NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ   NOT NULL DEFAULT now(),
    CONSTRAINT ck_citas_fechas CHECK (fin > inicio),
    CONSTRAINT ck_citas_estado CHECK (estado IN ('PENDIENTE','CONFIRMADA','COMPLETADA','CANCELADA','NO_ASISTIO')),
    -- Un barbero no puede tener dos citas activas que se traslapen
    CONSTRAINT ex_citas_sin_traslape EXCLUDE USING gist (
        barbero_id WITH =,
        tstzrange(inicio, fin) WITH &&
    ) WHERE (estado <> 'CANCELADA')
);

CREATE INDEX ix_citas_cliente ON citas (cliente_id);
CREATE INDEX ix_citas_barbero_inicio ON citas (barbero_id, inicio);