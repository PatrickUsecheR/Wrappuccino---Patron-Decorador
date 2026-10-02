package com.cafeteria.patterns.builder;

import com.cafeteria.modelo.Bebida;
import com.cafeteria.patterns.factory.BeverageFactory;
import com.cafeteria.patterns.prototype.BeverageRecipe;

import java.util.Objects;

public class BeverageBuilder {
    private final BeverageFactory factory;
    private BeverageRecipe recipe;

    public BeverageBuilder(BeverageFactory factory) {
        this.factory = Objects.requireNonNull(factory, "beverage factory cannot be null");
    }

    public BeverageBuilder withRecipe(BeverageRecipe recipe) {
        this.recipe = Objects.requireNonNull(recipe, "recipe cannot be null").copy();
        return this;
    }

    public Bebida build() {
        if (recipe == null) {
            throw new IllegalStateException("a recipe is required before building a beverage");
        }

        Bebida beverage = factory.createBaseBeverage();
        for (BeverageRecipe.Extra extra : recipe.getExtras()) {
            beverage = switch (extra.kind()) {
                case SHOT -> factory.createExtraShot(beverage);
                case SYRUP -> factory.createSyrup(beverage, extra.flavor());
            };
        }

        return factory.createSizedBeverage(beverage, recipe.getSize());
    }
}
