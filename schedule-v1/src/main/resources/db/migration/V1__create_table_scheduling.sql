CREATE TABLE tb_client(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL ,
    email VARCHAR(50) NOT NULL,
    telephone VARCHAR(20) NOT NULL,

    CONSTRAINT uk_client_email UNIQUE (email),
    CONSTRAINT uk_client_telephone UNIQUE (telephone)
);

CREATE TABLE tb_professional(
     id BIGSERIAL PRIMARY KEY,
     name VARCHAR(50) NOT NULL ,
     telephone VARCHAR(20) NOT NULL,
     profession VARCHAR(20) NOT NULL,

    CONSTRAINT uk_professional_telephone UNIQUE (telephone)
);

CREATE TABLE tb_service(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    duration_in_minutes INT NOT NULL,

    CONSTRAINT uk_service_name UNIQUE (name)
);

CREATE TABLE tb_professional_service(
    professional_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,

    CONSTRAINT pk_professional_service PRIMARY KEY (professional_id, service_id),
    CONSTRAINT fk_professional FOREIGN KEY (professional_id) REFERENCES tb_professional(id),
    CONSTRAINT fk_service FOREIGN KEY (service_id) REFERENCES tb_service(id)

);

CREATE TABLE tb_shift(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,

    CONSTRAINT check_hour_valid CHECK (end_time > start_time)
);

CREATE TABLE tb_professional_availability(
    id BIGSERIAL PRIMARY KEY,
    professional_id BIGINT NOT NULL,
    shift_id BIGINT NOT NULL,
    day_of_week VARCHAR(20) NOT NULL,

    CONSTRAINT uk_prof_day_shift UNIQUE (professional_id, day_of_week, shift_id),

    CONSTRAINT fk_professional_id FOREIGN KEY (professional_id) REFERENCES tb_professional(id),
    CONSTRAINT fk_shift_id FOREIGN KEY (shift_id) REFERENCES tb_shift(id)
);

CREATE EXTENSION IF NOT EXISTS btree_gist;

CREATE TABLE tb_scheduling (
    id BIGSERIAL PRIMARY KEY,
    appointment_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    payment_date TIMESTAMP,
    status VARCHAR(50) NOT NULL,

    client_id BIGINT NOT NULL,
    professional_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,

    CONSTRAINT fk_scheduling_client
            FOREIGN KEY (client_id)
            REFERENCES tb_client(id),

    CONSTRAINT fk_scheduling_professional
            FOREIGN KEY (professional_id)
            REFERENCES tb_professional(id),

    CONSTRAINT fk_scheduling_service
            FOREIGN KEY (service_id)
            REFERENCES tb_service(id),

    CONSTRAINT check_hour_valid CHECK (end_time > start_time),

    CONSTRAINT uk_client_hour UNIQUE (client_id, appointment_date, start_time),
    CONSTRAINT uk_professional_hour UNIQUE (professional_id, appointment_date, start_time),

    CONSTRAINT scheduling_no_overlap
        EXCLUDE USING GIST (
            professional_id WITH =,
            tsrange(
                appointment_date + start_time,
                appointment_date + end_time,
                '[)'
            ) WITH &&
        )

)