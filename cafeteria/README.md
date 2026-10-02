# Cafetería · Patrón Decorator (Spring Boot + Vercel)

- backend/  → Spring Boot 3 (Java 17), POST /api/pedido/preview
- frontend/ → HTML + CSS + JS vanilla (nginx)
- vercel.json → services + rewrites (Vercel Container Images, Beta)

## Patrones implementados

- **Decorator:** `Bebida` es el componente base; `ExtraShot`, `ExtraJarabe` y `Tamano` agregan extras y costo dinámicamente.
- **Prototype:** `BeverageRecipe` representa una receta reutilizable. `copy()` y `clone()` crean una copia independiente para derivar pedidos sin cambiar la receta original.
- **Builder:** `BeverageBuilder` recibe una receta y construye la bebida aplicando los extras en su orden y el tamaño como capa externa.
- **Abstract Factory:** `BeverageFactory` define la familia de creación. `CoffeeBeverageFactory` crea bebidas de espresso y `TeaBeverageFactory` crea bebidas de té; `BeverageFactoryProvider` selecciona la fábrica según la receta.

El endpoint se mantiene sin cambios: recibe `base`, `tamano` y `extras`, y responde con la descripción y el costo final. `PedidoService` convierte la solicitud en una receta, la clona y delega la construcción al Builder y a la fábrica correspondiente.

Local:
  cd backend && mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8080
  curl -X POST localhost:8080/api/pedido/preview -H "Content-Type: application/json" \
    -d '{"base":"espresso","tamano":"GRANDE","extras":["SHOT","JARABE:vainilla"]}'

Deploy:
  npm i -g vercel && vercel login && vercel --prod
