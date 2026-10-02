## Patrones de diseño

| Patrón | Dónde | Para qué sirve |
|---|---|---|
| **Decorator** | `modelo/` (`BebidaDecorator`, `ExtraShot`, `ExtraJarabe`, `Tamano`) | Agrega extras y tamaño a una bebida sin crear una subclase por cada combinación |
| **Abstract Factory** | `fabrica/` (`BebidaFactory`, `CafeFactory`, `TeFactory`, `FabricaBebidas`) | Crea la familia de productos (base + extras) de cada línea de bebidas |
| **Builder** | `constructor/` (`BebidaBuilder`) | Arma la bebida paso a paso y aplica siempre el tamaño al final |
| **Prototype** | `prototipo/` (`CatalogoBebidas`, `Bebida.copiar()`) | Entrega copias profundas de bebidas predefinidas ("plantillas") |

### Ejemplo
```java
Bebida b = new BebidaBuilder(new CafeFactory())
        .conShot()
        .conJarabe("vainilla")
        .tamano(Tamano.Medida.GRANDE)
        .construir();
// Espresso + Extra shot + Jarabe de vainilla (Grande) -> 4.88

Bebida plantilla = new CatalogoBebidas().obtener("espresso-doble"); // copia nueva
```

### Flujo de un pedido
`PedidoController` -> `PedidoService` -> `FabricaBebidas` (elige fábrica) -> `BebidaBuilder` (arma con decoradores) -> `PedidoResponse`
