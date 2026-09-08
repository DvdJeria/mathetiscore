-- Habilitar extensión para generación de UUIDs
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- -----------------------------------------------------
-- 1. CATÁLOGOS Y ESTRUCTURA BASE
-- -----------------------------------------------------

CREATE TABLE roles (
                       rol_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       rol_nombre VARCHAR(50) NOT NULL UNIQUE,
                       rol_descripcion TEXT
);

CREATE TABLE establecimiento (
                                 est_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                                 est_nombre VARCHAR(150) NOT NULL,
                                 est_descripcion TEXT
);

CREATE TABLE sede (
                      sed_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                      sed_nombre VARCHAR(150) NOT NULL,
                      sed_direccion VARCHAR(255) NOT NULL,
                      sed_fono VARCHAR(20) NOT NULL,
                      sed_descripcion TEXT,
                      establecimiento_est_id UUID NOT NULL REFERENCES establecimiento(est_id) ON DELETE CASCADE
);

CREATE TABLE periodo_academico (
                                   peac_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                   peac_nombre VARCHAR(50) NOT NULL,
                                   peac_descripcion TEXT
);

-- -----------------------------------------------------
-- 2. IDENTIDADES Y PERFILES (Permite nulos temporales para registro progresivo)
-- -----------------------------------------------------

CREATE TABLE usuario (
                         usu_id UUID PRIMARY KEY, -- Corresponde al UUID de auth.users de Supabase
                         usu_nombre VARCHAR(100),
                         usu_apellido_paterno VARCHAR(100),
                         usu_apellido_materno VARCHAR(100),
                         usu_email VARCHAR(255) NOT NULL UNIQUE,
                         usu_rut VARCHAR(12) UNIQUE,
                         usu_descripcion TEXT,
                         roles_rol_id BIGINT REFERENCES roles(rol_id)
);

CREATE TABLE asignacion_sede (
                                 ased_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                                 ased_nombre VARCHAR(100) NOT NULL,
                                 ased_descripcion TEXT,
                                 sede_sed_id UUID NOT NULL REFERENCES sede(sed_id) ON DELETE CASCADE
);

CREATE TABLE profesor (
                          prof_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                          pro_descripcion TEXT,
                          usuario_usu_id UUID NOT NULL UNIQUE REFERENCES usuario(usu_id) ON DELETE CASCADE,
                          asignacion_sede_ased_id UUID NOT NULL REFERENCES asignacion_sede(ased_id)
);

CREATE TABLE alumnos (
                         al_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                         al_descripcion TEXT,
                         usuario_usu_id UUID NOT NULL UNIQUE REFERENCES usuario(usu_id) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- 3. TÍTULOS Y ANTECEDENTES DOCENTES
-- -----------------------------------------------------

CREATE TABLE titulo_maestros (
                                 tma_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                 tma_nombre VARCHAR(150) NOT NULL,
                                 tma_tipo VARCHAR(50) NOT NULL,
                                 tma_descripcion TEXT
);

CREATE TABLE titulos_profesor (
                                  tprof_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                                  tprof_instituto_egreso VARCHAR(150) NOT NULL,
                                  tprof_anno_titulacion DATE NOT NULL,
                                  tprof_url_documento TEXT NOT NULL,
                                  tprof_descripcion TEXT,
                                  profesor_prof_id UUID NOT NULL REFERENCES profesor(prof_id) ON DELETE CASCADE,
                                  titulo_maestros_tma_id BIGINT NOT NULL REFERENCES titulo_maestros(tma_id)
);

-- -----------------------------------------------------
-- 4. ESTRUCTURA ACADÉMICA
-- -----------------------------------------------------

CREATE TABLE curso (
                       cur_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                       cur_nombre VARCHAR(50) NOT NULL,
                       cur_descripcion TEXT
);

CREATE TABLE asignatura (
                            asig_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                            asig_nombre VARCHAR(100) NOT NULL,
                            asig_descripcion TEXT
);

CREATE TABLE curso_asignatura (
                                  cas_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                                  cas_nombre VARCHAR(100) NOT NULL,
                                  cas_codigo VARCHAR(20) NOT NULL,
                                  cas_tipo_asignatura VARCHAR(50) NOT NULL,
                                  cas_horas_semanales INT NOT NULL,
                                  cas_descripcion TEXT,
                                  profesor_prof_id UUID NOT NULL REFERENCES profesor(prof_id),
                                  curso_cur_id UUID NOT NULL REFERENCES curso(cur_id),
                                  asignatura_asig_id UUID NOT NULL REFERENCES asignatura(asig_id)
);

CREATE TABLE matricula (
                           mat_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                           mat_nombre VARCHAR(100) NOT NULL,
                           mat_descripcion TEXT,
                           alumnos_al_id UUID NOT NULL REFERENCES alumnos(al_id),
                           periodo_academico_peac_id BIGINT NOT NULL REFERENCES periodo_academico(peac_id),
                           curso_cur_id UUID NOT NULL REFERENCES curso(cur_id),
                           CONSTRAINT uq_alumno_periodo UNIQUE (alumnos_al_id, periodo_academico_peac_id)
);

-- -----------------------------------------------------
-- 5. OPERACIONES: ASISTENCIA Y CALIFICACIONES
-- -----------------------------------------------------

CREATE TABLE estado_asistencia (
                                   esis_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                   esis_nombre VARCHAR(50) NOT NULL UNIQUE,
                                   esis_descripcion TEXT
);

CREATE TABLE asistencia (
                            asis_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                            asis_fecha DATE NOT NULL,
                            asis_justificado BOOLEAN NOT NULL DEFAULT FALSE,
                            asis_descripcion TEXT,
                            curso_asignatura_cas_id UUID NOT NULL REFERENCES curso_asignatura(cas_id),
                            matricula_mat_id UUID NOT NULL REFERENCES matricula(mat_id),
                            estado_asistencia_esis_id BIGINT NOT NULL REFERENCES estado_asistencia(esis_id),
                            CONSTRAINT uq_asistencia_diaria UNIQUE (matricula_mat_id, curso_asignatura_cas_id, asis_fecha)
);

CREATE TABLE calificacion (
                              cal_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                              cal_nombre VARCHAR(100) NOT NULL,
                              cal_ponderacion NUMERIC(5,2) NOT NULL,
                              cal_nota NUMERIC(3,2) NOT NULL,
                              cal_fecha DATE NOT NULL,
                              cal_descripcion TEXT,
                              matricula_mat_id UUID NOT NULL REFERENCES matricula(mat_id),
                              curso_asignatura_cas_id UUID NOT NULL REFERENCES curso_asignatura(cas_id)
);