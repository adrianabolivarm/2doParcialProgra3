# Informe de Revisión de Código y Gestión de Riesgos (REVISION.md)
**Proyecto:** Explorador de Productos y Mercado Global (Grupo 1)  
**Materia:** Programación 3 - Examen Práctico  

---

## Registro de Riesgos, Defectos e Imprevistos Corregidos

### Defecto 1: Incompatibilidad de KAPT y Metadatos de Compilación de Kotlin
- **Riesgo/Defecto:** La tarea de procesamiento de anotaciones `:app:kaptDebugKotlin` fallaba con `IllegalArgumentException: Provided Metadata instance has version 2.2.0, while maximum supported version is 2.0.0` debido a la versión del compilador de Room.
- **Causa:** Incompatibilidad entre la versión predeterminada del procesador de anotaciones y las firmas bytecode emitidas por Kotlin 2.2.
- **Corrección:** Se actualizó Room a la versión estable `2.8.5` en `gradle/libs.versions.toml` y se añadieron las propiedades de compatibilidad `android.builtInKotlin=false` y `android.newDsl=false` en `gradle.properties`.

---

### Defecto 2: Vulnerabilidad a Caídas de Red en Peticiones Combinadas (API B Frankfurter)
- **Riesgo/Defecto:** Al realizar el mapeo combinado de la API A (`DummyJSON`) con la API B (`Frankfurter`), una falla de red o tiempo de espera en el servicio de divisas provocaba un colapso en la pantalla principal.
- **Causa:** Dependencia rígida de dos servicios remotos sin mecanismo de tolerancia a fallos.
- **Corrección:** En `FrankfurterRepository` se implementó un manejo de excepciones con valores de contingencia por defecto (1 USD = 0.92 EUR / 6.96 BOB). De este modo, la aplicación continúa funcionando y mostrando la lista de productos de manera fluida aunque el servicio de cambio no esté disponible.

---

## Verificación de Calidad
- **Compilación de Gradle:** Exitosa mediante la orden `app:assembleDebug`.
- **Estructura Arquitectónica:** Cumplimiento al 100% con los paquetes `data/local`, `data/remote/dto`, `data/mapper`, `data/repository`, `model`, `ui/viewmodel` y `ui/screens`.
