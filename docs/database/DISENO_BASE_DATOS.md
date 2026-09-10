# Documentación del Diseño de Base de Datos - MathetisCore

## 1. Contexto y Objetivos del Modelo
El modelo de datos de MathetisCore responde a la necesidad de gestionar múltiples instituciones educativas (SaaS Multi-tenant) garantizando el aislamiento de datos entre clientes, la persistencia histórica de información académica y la unificación de identidades de usuarios.

El motor de base de datos objetivo es PostgreSQL, utilizando las capacidades de Supabase para la gestión de autenticación y seguridad por nivel de fila (Row Level Security - RLS).

## 2. Principios de Diseño
* Identidad Única Centralizada: Las personas naturales se registran una sola vez en la plataforma mediante un identificador civil único (RUT/DNI). La relación con perfiles (docente, estudiante) se realiza mediante extensiones 1:1.
* Multi-tenancy por Sede: La infraestructura contempla colegios con una o múltiples sedes. Los accesos operativos de los docentes se controlan mediante asignaciones por sede y periodo académico.
* Integridad en el Motor de Base de Datos: Las restricciones de negocio operativas (como duplicación de calificaciones o marcas de asistencia en la misma clase y fecha) se gestionan directamente mediante restricciones UNIQUE en el motor.
* Preservación del Histórico Académico: El ciclo de datos se vincula explícitamente a un periodo académico activo para evitar sobreescritura de información de años anteriores.

## 3. Estructura Dominios del Sistema

### Bloque 1: Accesos, Identidad y Roles Globales
Centraliza la información de personas naturales y autenticación.

* Roles: Catálogo de perfiles globales de la aplicación (Ej: Docente, Estudiante, Administrador).
* Usuario: Credenciales y datos civiles (RUT, email, nombre completo). Garantiza cero duplicados mediante índices únicos.
* Profesor: Ficha técnica y datos laborales. Relación 1:1 con la tabla Usuario.
* Alumno: Antecedentes del estudiante (datos médicos, ficha de ingreso). Relación 1:1 con la tabla Usuario.

### Bloque 2: Estructura Multi-Cliente (Tenancy) y Contratos
Modela la estructura organizativa de los clientes.

* Establecimiento: Entidad legal que contrata la plataforma (identificada por su registro oficial/ministerial).
* Sede: Infraestructura física vinculada a un establecimiento.
* Periodo_Academico: División temporal (año lectivo) para el aislamiento de datos históricos.
* Asignacion_Sede: Tabla intermedia que vincula Profesor, Sede, Periodo Académico y Rol. Permite que un profesor opere en distintas sedes o colegios sin cruce de datos.

### Bloque 3: Estructura Académica
Define el plan de estudios y las agrupaciones de estudiantes.

* Curso: Grupo o nivel físico dentro de una sede (Ej: 1° Medio A).
* Asignatura: Catálogo general de materias o ramos.
* Curso_Asignatura: Carga académica que relaciona un Curso, una Asignatura y el Profesor responsable.
* Matricula: Registro histórico anual de la incorporación de un Alumno a un Curso y Periodo Académico específico.

### Bloque 4: Operaciones Diarias
Tablas transaccionales de alta frecuencia.

* Calificacion: Registro de evaluaciones con nota y ponderación.
    - Restricción: UNIQUE (id_matricula, id_curso_asignatura, descripcion_evaluacion)
* Estado_Asistencia: Catálogo de estados de lista (Presente, Ausente, Atraso, etc.).
* Asistencia: Registro de asistencia diaria o por bloque de clase.
    - Restricción: UNIQUE (id_matricula, id_curso_asignatura, fecha)
* Anotacion: Bitácora de conducta, incidencias y reconocimientos del estudiante.

## 4. Consideraciones para la Implementación DDL (PostgreSQL / Supabase)
1. Tipos de Claves Primarias: Se usará IDENTITY (GENERATED ALWAYS AS IDENTITY) o UUID para garantizar compatibilidad con auth.users de Supabase.
2. Integración de Autenticación: La tabla Usuario se enlazará con la tabla de autenticación nativa de Supabase mediante triggers de base de datos.
3. Indexación: Se definirán índices explícitos sobre las llaves foráneas id_sede, id_periodo, id_curso_asignatura e id_matricula para optimizar las consultas de lectura.