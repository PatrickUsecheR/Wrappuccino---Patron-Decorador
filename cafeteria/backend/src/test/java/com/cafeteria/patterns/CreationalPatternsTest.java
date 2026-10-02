package com.cafeteria.patterns;

import com.cafeteria.modelo.Bebida;
import com.cafeteria.modelo.Tamano;
import com.cafeteria.patterns.builder.BeverageBuilder;
import com.cafeteria.patterns.factory.BeverageFactoryProvider;
import com.cafeteria.patterns.prototype.BeverageRecipe;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class CreationalPatternsTest {
    @Test
    void prototypeCreatesIndependentRecipeCopies() {
        BeverageRecipe original = BeverageRecipe.forBeverage(BeverageRecipe.BeverageKind.ESPRESSO)
                .withExtraShot();
        BeverageRecipe copy = original.copy().withSyrup("vanilla");

        assertNotSame(original, copy);
        assertEquals(1, original.getExtras().size());
        assertEquals(2, copy.getExtras().size());
    }

    @Test
    void builderUsesFactoryAndDecoratorLayers() {
        BeverageRecipe recipe = BeverageRecipe.forBeverage(BeverageRecipe.BeverageKind.ESPRESSO)
                .withExtraShot()
                .withSyrup("vanilla")
                .withSize(Tamano.Medida.GRANDE);

        Bebida beverage = new BeverageBuilder(BeverageFactoryProvider.forKind(recipe.getBeverageKind()))
                .withRecipe(recipe)
                .build();

        assertEquals(new BigDecimal("4.88"), beverage.getCosto());
        assertEquals("Espresso + Extra shot + Jarabe de vanilla (Grande)", beverage.getDescripcion());
    }

    @Test
    void abstractFactoryCreatesTeaFamily() {
        BeverageRecipe recipe = BeverageRecipe.forBeverage(BeverageRecipe.BeverageKind.TEA)
                .withSize(Tamano.Medida.MEDIANO);

        Bebida beverage = new BeverageBuilder(BeverageFactoryProvider.forKind(recipe.getBeverageKind()))
                .withRecipe(recipe)
                .build();

        assertEquals(new BigDecimal("1.88"), beverage.getCosto());
        assertEquals("Té (Mediano)", beverage.getDescripcion());
    }
}
