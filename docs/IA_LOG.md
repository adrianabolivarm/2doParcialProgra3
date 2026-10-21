# Bitácora de Prompts e Interacción con IA (IA_LOG.md)
**Proyecto:** Explorador de Productos y Mercado Global (Grupo 1)  
**Materia:** Programación 3 - Examen Práctico  

---

## Registro de Prompts y Decisiones Técnicas

### Prompt 1: Configuración de Entorno y Arquitectura
- **Instrucción:** Establecer la arquitectura MVVM limpia y configurar Gradle con Retrofit, Room, KAPT y los permisos de red.
- **Decisión:** Se integraron las librerías en `libs.versions.toml`, se configuró la compatibilidad de KAPT con AGP 9.4 especificando `android.builtInKotlin=false` y se agregó la dependencia de `coil-compose` para la carga eficiente de imágenes.

### Prompt 2: Integración de las 3 APIs y Mappers Combinados
- **Instrucción:** Crear 3 servicios de Retrofit independientes (DummyJSON, Frankfurter, JSONPlaceholder) y 2 mappers combinados según el diagrama de clases del examen.
- **Decisión:** 
  - `CombinedProductRateMapper`: Une `DummyJsonDto` con `FrankfurterDto` para calcular `priceEur` y `priceBob`.
  - `CombinedRatePostMapper`: Enriquece los posts de `JSONPlaceholder` con las tasas cambiarias de `Frankfurter`.

### Prompt 3: Persistencia Reactiva y Requisitos de Favoritos
- **Instrucción:** Implementar Room con `ProductEntity` y `ProductDao` que utilice `Flow` y consulta de eliminación con `@Query`.
- **Decisión:** Se definió el método `deleteFavoriteById(@Query)` en `ProductDao`. Se condicionó la UI de `FavoritesScreen` para mostrar obligatoriamente `"Sin elementos guardados"` cuando el `Flow` de la base de datos emita una lista vacía.

### Prompt 4: Optimización de Búsqueda con `derivedStateOf`
- **Instrucción:** Implementar una barra de búsqueda en la lista de productos que realice el filtrado usando `derivedStateOf`.
- **Decisión:** Se implementó un estado derivado `remember(products, searchQuery) { derivedStateOf { ... } }` en `ProductListScreen` para optimizar el rendimiento y evitar recomposiciones costosas en Compose.
