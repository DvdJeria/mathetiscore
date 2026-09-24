## Índice de Documentación Técnica

La arquitectura y los componentes del sistema se encuentran documentados en los siguientes apartados:

* **Arquitectura y Endpoints Generales:** [`docs/backend/architecture_and_endpoints.md`](docs/backend/architecture_and_endpoints.md)  
  *Descripción general de la estructura de capas, diseño de controladores y contratos de API.*
  
* **Integración con Supabase Auth:** [`docs/backend/supabase-auth-integration.md`](docs/backend/supabase-auth-integration.md)  
  *Detalle del flujo transaccional, puertos de salida y adaptador de infraestructura para la gestión de credenciales (Server-to-Server).*

* **Registro de Profesores (`createProfesor`):** [`docs/backend/create-profesor.md`](docs/backend/create-profesor.md)  
  *Detalle del endpoint especializado de registro bajo arquitectura hexagonal, integración con Supabase Auth y persistencia dual en cascada.*

* **Registro de Alumnos (`createAlumno`):** [`docs/backend/create-alumno.md`](docs/backend/create-alumno.md)  
  *Detalle del flujo específico de registro de alumnos, desacoplamiento del módulo monolítico y persistencia bajo arquitectura hexagonal.*