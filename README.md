# MathetisCore

Plataforma SaaS Multi-tenant para la gestión académica, administrativa y operativa de instituciones educativas.

## Visión del Proyecto
MathetisCore está diseñado para optimizar los procesos de establecimientos escolares mediante una arquitectura escalable, segura y modular. El nombre del proyecto deriva del griego clásico *Mathetes* (estudiante, aprendiz), reflejando un enfoque centrado en la formación integral de los alumnos.

## Arquitectura General
El repositorio se organiza bajo una estructura modular monorepo:

* `database/`: Scripts de definición de datos (DDL), migraciones y configuraciones para PostgreSQL/Supabase.
* `backend/`: API RESTful y lógica de negocio construida en Java.
* `frontend/`: Interfaz de usuario web para administradores, docentes y estudiantes.
* `docs/`: Documentación técnica de arquitectura, procesos e integraciones.

## Requisitos Previos
* Git 2.x o superior
* Cliente PostgreSQL / Supabase CLI
* Java JDK (Versión a definir en la fase de desarrollo)

## Configuración del Entorno Local
1. Clonar el repositorio:
   git clone git@github.com:DvdJeria/mathetiscore.git

2. Navegar al directorio del proyecto:
   cd mathetiscore

3. Revisar la documentación técnica en el directorio `docs/` antes de iniciar contribuciones.

## Licencia
Propiedad privada. Todos los derechos reservados.