# Documento de Diseño de Arquitectura y Componentes
**Proyecto:** Explorador de Productos y Mercado Global (Grupo 1)  
**Materia:** Programación 3 - Examen Práctico  

---

## 1. Arquitectura del Sistema (MVVM Limpia)
El proyecto implementa una arquitectura limpia **MVVM (Model-View-ViewModel)** estructurada estrictamente en los siguientes paquetes:

- **`model/`**: Modelos puros de dominio (`ProductModel`, `RateModel`, `PostModel`).
- **`data/local/`**: Persistencia local con Room (`ProductEntity`, `ProductDao`, `AppDatabase`).
- **`data/remote/`**: Servicios Retrofit (`DummyJsonApiService`, `FrankfurterApiService`, `JsonPlaceholderApiService`) y envolvente `ResultState`.
- **`data/remote/dto/`**: Clases DTO de red (`DummyJsonResponseDto`, `ProductDto`, `FrankfurterDto`, `JsonPlaceholderDto`).
- **`data/mapper/`**: Mappers combinados (`CombinedProductRateMapper`, `CombinedRatePostMapper`) y conversiones a entidades (`EntityMapper`).
- **`data/repository/`**: Repositorios independientes por API (`DummyJsonRepository`, `FrankfurterRepository`, `JsonPlaceholderRepository`, `ProductRepository`, `FavoritesRepository`).
- **`ui/viewmodel/`**: ViewModels exponiendo el estado mediante `StateFlow` (`ProductViewModel`, `FavoritesViewModel`, `MarketPostViewModel`).
- **`ui/screens/`**: Pantallas nativas desarrolladas con Jetpack Compose (`ProductListScreen`, `ProductDetailScreen`, `FavoritesScreen`, `MarketPostsScreen`).

---

## 2. Estrategia de Mapeo Combinado
1. **Mapper 1 (`CombinedProductRateMapper`)**:
   Combina los productos de la API A (`DummyJSON`) con las tasas de cambio de la API B (`Frankfurter`). Calcula en tiempo de mapeo los precios equivalentes en Euros (`priceEur`) y Bolivianos (`priceBob`).
2. **Mapper 2 (`CombinedRatePostMapper`)**:
   Combina la información de la API B (`Frankfurter`) con las publicaciones de la API C (`JSONPlaceholder`), inyectando un resumen actualizado del mercado cambiario en cada post.

---

## 3. Persistencia con Room
- **Entidad `ProductEntity`**: Representa la tabla `favorite_products` con `id` como Clave Primaria, título, precio, categoría, thumbnail y precios convertidos.
- **`ProductDao`**: Utiliza `Flow` para la observación reactiva de favoritos y funciones `suspend` con la anotación `@Query("DELETE FROM favorite_products WHERE id = :id")` para eliminar elementos.
- **`AppDatabase`**: Base de datos Room siguiendo el patrón Singleton con control de concurrencia `@Volatile`.
