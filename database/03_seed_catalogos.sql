-- 1. ROLES
INSERT INTO public.roles (rol_nombre, rol_descripcion) VALUES
                                                           ('SuperAdmin', 'Administrador global de la plataforma SaaS'),
                                                           ('Administrador_Sede', 'Gestor administrativo de una sede específica'),
                                                           ('Docente', 'Profesor con acceso a gestión académica'),
                                                           ('Estudiante', 'Alumno con acceso a portal de notas y asistencia'),
                                                           ('Apoderado', 'Tutor legal del estudiante');

-- 2. ESTADOS DE ASISTENCIA
INSERT INTO public.estado_asistencia (esis_nombre, esis_descripcion) VALUES
                                                                         ('Presente', 'El alumno asistió a la jornada o clase'),
                                                                         ('Ausente', 'El alumno no asistió'),
                                                                         ('Atraso', 'El alumno ingresó tarde a la sesión'),
                                                                         ('Justificado', 'Inasistencia o atraso respaldado por certificado');

-- 3. TÍTULOS MAESTROS
INSERT INTO public.titulo_maestros (tma_nombre, tma_tipo, tma_descripcion) VALUES
                                                                               ('Pedagogía en Educación Básica', 'Pregrado', 'Título docente general básico'),
                                                                               ('Pedagogía en Matemática', 'Pregrado', 'Título docente para educación media'),
                                                                               ('Licenciatura en Educación', 'Grado Académico', 'Grado universitario en educación'),
                                                                               ('Magíster en Liderazgo Escolar', 'Postgrado', 'Especialización en dirección educativa');