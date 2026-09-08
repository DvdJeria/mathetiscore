-- Usamos un bloque anónimo DO para manejar variables temporales e inserciones en orden
DO $$
DECLARE
v_est_id UUID;
    v_sed_central_id UUID;
    v_sed_norte_id UUID;
    v_ased_id UUID;
    v_peac_id BIGINT;
    v_cur_1a_id UUID;
    v_asig_mat_id UUID;
    v_usu_prof_id UUID := uuid_generate_v4();
    v_usu_alum_id UUID := uuid_generate_v4();
    v_prof_id UUID;
    v_al_id UUID;
    v_cas_id UUID;
    v_mat_id UUID;
BEGIN
    -- 1. CREAR ESTABLECIMIENTO Y SEDES
INSERT INTO public.establecimiento (est_nombre, est_descripcion)
VALUES ('Liceo Polivalente Mathetis', 'Establecimiento de prueba modelo para desarrollo')
    RETURNING est_id INTO v_est_id;

INSERT INTO public.sede (sed_nombre, sed_direccion, sed_fono, sed_descripcion, establecimiento_est_id)
VALUES ('Sede Central', 'Av. Libertador 1234, Santiago', '+56221112222', 'Sede principal de enseñanza media', v_est_id)
    RETURNING sed_id INTO v_sed_central_id;

INSERT INTO public.sede (sed_nombre, sed_direccion, sed_fono, sed_descripcion, establecimiento_est_id)
VALUES ('Sede Norte', 'Calle Las Heras 567, Santiago', '+56223334444', 'Sede de enseñanza básica', v_est_id)
    RETURNING sed_id INTO v_sed_norte_id;

-- 2. ASIGNACIÓN DE SEDE Y PERIODO ACADÉMICO
INSERT INTO public.asignacion_sede (ased_nombre, ased_descripcion, sede_sed_id)
VALUES ('Jornada Mañana - Sede Central', 'Asignación operativa de docentes', v_sed_central_id)
    RETURNING ased_id INTO v_ased_id;

INSERT INTO public.periodo_academico (peac_nombre, peac_descripcion)
VALUES ('Año Lectivo 2026', 'Periodo escolar anual activo')
    RETURNING peac_id INTO v_peac_id;

-- 3. CREAR USUARIOS Y PERFILES (PROFESOR Y ALUMNO)
INSERT INTO public.usuario (usu_id, usu_nombre, usu_apellido_paterno, usu_apellido_materno, usu_email, usu_rut, roles_rol_id)
VALUES (v_usu_prof_id, 'Roberto', 'Gómez', 'Bolaños', 'roberto.docente@mathetiscore.com', '15222333-4', 3);

INSERT INTO public.profesor (pro_descripcion, usuario_usu_id, asignacion_sede_ased_id)
VALUES ('Profesor Titular de Matemática', v_usu_prof_id, v_ased_id)
    RETURNING prof_id INTO v_prof_id;

INSERT INTO public.usuario (usu_id, usu_nombre, usu_apellido_paterno, usu_apellido_materno, usu_email, usu_rut, roles_rol_id)
VALUES (v_usu_alum_id, 'Lucas', 'Silva', 'Mendoza', 'lucas.alumno@mathetiscore.com', '23444555-K', 4);

INSERT INTO public.alumnos (al_descripcion, usuario_usu_id)
VALUES ('Estudiante Regular 2026', v_usu_alum_id)
    RETURNING al_id INTO v_al_id;

-- 4. ESTRUCTURA CURRICULAR Y MATRÍCULA
INSERT INTO public.curso (cur_nombre, cur_descripcion)
VALUES ('1º Medio A', 'Primer año de enseñanza media - Grupo A')
    RETURNING cur_id INTO v_cur_1a_id;

INSERT INTO public.asignatura (asig_nombre, asig_descripcion)
VALUES ('Matemáticas I', 'Álgebra, Geometría y Estadística básica')
    RETURNING asig_id INTO v_asig_mat_id;

INSERT INTO public.curso_asignatura (cas_nombre, cas_codigo, cas_tipo_asignatura, cas_horas_semanales, profesor_prof_id, curso_cur_id, asignatura_asig_id)
VALUES ('Matemática 1ºA', 'MAT-1A-2026', 'Obligatoria', 6, v_prof_id, v_cur_1a_id, v_asig_mat_id)
    RETURNING cas_id INTO v_cas_id;

INSERT INTO public.matricula (mat_nombre, mat_descripcion, alumnos_al_id, periodo_academico_peac_id, curso_cur_id)
VALUES ('MAT-2026-001', 'Matrícula activa 2026', v_al_id, v_peac_id, v_cur_1a_id)
    RETURNING mat_id INTO v_mat_id;

-- 5. REGISTRO DE PRUEBA: ASISTENCIA Y CALIFICACIÓN
INSERT INTO public.asistencia (asis_fecha, asis_justificado, asis_descripcion, curso_asignatura_cas_id, matricula_mat_id, estado_asistencia_esis_id)
VALUES ('2026-03-02', FALSE, 'Clase inaugural', v_cas_id, v_mat_id, 1);

INSERT INTO public.calificacion (cal_nombre, cal_ponderacion, cal_nota, cal_fecha, cal_descripcion, matricula_mat_id, curso_asignatura_cas_id)
VALUES ('Prueba 1: ÁLGEBRA', 25.00, 6.5, '2026-03-15', 'Evaluación parcial de ecuaciones', v_mat_id, v_cas_id);

END $$;
