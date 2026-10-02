package com.cafeteria.patterns.factory;

import com.cafeteria.patterns.prototype.BeverageRecipe.BeverageKind;

import java.util.Objects;

public final class BeverageFactoryProvider {
    private BeverageFactoryProvider() {
    }

    public static BeverageFactory forKind(BeverageKind beverageKind) {
        return switch (Objects.requireNonNull(beverageKind, "beverage kind cannot be null")) {
            case ESPRESSO -> new CoffeeBeverageFactory();
            case TEA -> new TeaBeverageFactory();
        };
    }
}
