package com.cafeteria.servicio;

import com.cafeteria.dto.PedidoRequest;
import com.cafeteria.dto.PedidoResponse;
import com.cafeteria.modelo.Bebida;
import com.cafeteria.modelo.Tamano;
import com.cafeteria.patterns.builder.BeverageBuilder;
import com.cafeteria.patterns.factory.BeverageFactory;
import com.cafeteria.patterns.factory.BeverageFactoryProvider;
import com.cafeteria.patterns.prototype.BeverageRecipe;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;

@Service
public class PedidoService {

    public PedidoResponse preview(PedidoRequest req) {
        if (req == null || req.base() == null) {
            throw error("Falta la bebida base");
        }

        BeverageRecipe recipe = BeverageRecipe.forBeverage(parseBeverageKind(req.base()));
        List<String> extras = req.extras() == null ? List.of() : req.extras();
        for (String extra : extras) {
            if (extra == null || extra.isBlank()) {
                throw error("Extra inválido");
            }

            String[] partes = extra.split(":", 2);
            switch (partes[0].toLowerCase(Locale.ROOT)) {
                case "shot"   -> recipe.withExtraShot();
                case "jarabe" -> {
                    if (partes.length < 2 || partes[1].isBlank()) throw error("Jarabe sin sabor");
                    recipe.withSyrup(partes[1].trim());
                }
                default -> throw error("Extra desconocido: " + extra);
            }
        }

        recipe.withSize(parseSize(req.tamano()));
        BeverageRecipe orderRecipe = recipe.copy();
        BeverageFactory factory = BeverageFactoryProvider.forKind(orderRecipe.getBeverageKind());
        Bebida bebida = new BeverageBuilder(factory).withRecipe(orderRecipe).build();

        return new PedidoResponse(bebida.getDescripcion(), bebida.getCosto());
    }

    private BeverageRecipe.BeverageKind parseBeverageKind(String base) {
        return switch (base.toLowerCase(Locale.ROOT)) {
            case "espresso" -> BeverageRecipe.BeverageKind.ESPRESSO;
            case "te" -> BeverageRecipe.BeverageKind.TEA;
            default -> throw error("Base desconocida: " + base);
        };
    }

    private Tamano.Medida parseSize(String size) {
        try {
            return Tamano.Medida.valueOf((size == null ? "PEQUENO" : size).toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw error("Tamaño desconocido: " + size);
        }
    }

    private ResponseStatusException error(String msg) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }
}
