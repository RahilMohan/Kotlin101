# Design: JSON Schema Generator (Project 3)

**Goal:** Master Kotlin Generics and Reflection by building a tool that inspects Kotlin classes and generates JSON Schema representations.

## Architecture
- **Reflection Engine:** Uses `kotlin-reflect` to inspect properties of a class.
- **Generics:** A generic function `generateSchema<T>()` that works for any data class.
- **Mapping:** Maps Kotlin types (`String`, `Int`, `Boolean`, `Double`) to JSON types.

## Logic Decisions
- **Generics:** Use `reified` type parameters to access class information at runtime.
- **Reflection:** Iterate over `memberProperties` of the KClass.
- **Nullability:** Mark properties as required/optional in the schema based on Kotlin nullability.

## Features
1. **Type Mapping:** Automatic conversion of primitive types.
2. **Nested Objects:** Handle nested data classes recursively.
3. **Nullability Support:** Reflect optional fields in the schema.
