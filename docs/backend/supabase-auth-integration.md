# Integración con Supabase Auth bajo Arquitectura Hexagonal

## 1. Visión General
Este módulo implementa la integración de autenticación y gestión de usuarios utilizando **Supabase Auth** combinada con triggers automáticos a nivel de base de datos PostgreSQL, respetando los principios de la **Arquitectura Hexagonal**.

El objetivo es desacoplar la lógica de negocio de la infraestructura de autenticación externa mediante el uso de **Puertos y Adaptadores**.

---

## 2. Flujo de Registro Orquestado
Cuando se solicita la creación de un perfil especializado (ej. Profesor), el servicio de aplicación ejecuta la siguiente transacción:

1. **Autenticación Externa:** El backend invoca a la API de administración de Supabase (`/auth/v1/admin/users`) enviando credenciales (`email` y `password`).
2. **Generación de Credencial y Trigger:** Supabase Auth registra al usuario, encripta la contraseña y genera un **`UUID`** único. Un trigger en la base de datos captura este evento y crea automáticamente un registro base en la tabla pública `usuario` con dicho `UUID` y correo.
3. **Sincronización y Actualización Local:** El backend recibe el `UUID` devuelto por Supabase, busca el registro en la tabla `usuario` y realiza un `UPDATE` completando los datos personales (RUT, nombres, apellidos, descripción, rol).
4. **Persistencia de Perfil:** Se procede a insertar los datos específicos en la tabla `profesor` y sus respectivos antecedentes en `titulos_profesor`.

---

## 3. Componentes Arquitectónicos

### Capa de Dominio (Puerto de Salida)
* **Interfaz:** `com.mathetiscore.api.domain.port.outbound.AuthPort`
* **Método:** `UUID registerUserInAuth(String email, String password);`
* **Propósito:** Define el contrato abstracto que el dominio necesita para registrar credenciales sin conocer los detalles del proveedor HTTP.

### Capa de Infraestructura (Adaptador)
* **Clase:** `com.mathetis.api.infraestructure.repository.adapter.SupabaseAuthRepositoryAdapter`
* **Propósito:** Implementa `AuthPort` utilizando `RestTemplate` y se comunica con la API Admin de Supabase inyectando las cabeceras de seguridad (`apikey` y `Authorization: Bearer <service_role_key>`).

---

## 4. Configuración y Credenciales
Las credenciales sensibles de Supabase se configuran de manera segura en el archivo `application.yml` mediante variables de entorno del sistema operativo:

```yaml
supabase:
  url: ${SUPABASE_URL:[https://tu-proyecto.supabase.co](https://tu-proyecto.supabase.co)}
  service-role-key: ${SUPABASE_SERVICE_ROLE_KEY}