package com.cafeteria.patterns.prototype;

import com.cafeteria.modelo.Tamano;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class BeverageRecipe implements Cloneable {
    public enum BeverageKind {
        ESPRESSO,
        TEA
    }

    public enum ExtraKind {
        SHOT,
        SYRUP
    }

    public record Extra(ExtraKind kind, String flavor) {
        public Extra {
            Objects.requireNonNull(kind, "extra kind cannot be null");
            if (kind == ExtraKind.SYRUP && (flavor == null || flavor.isBlank())) {
                throw new IllegalArgumentException("syrup flavor cannot be blank");
            }
        }
    }

    private final BeverageKind beverageKind;
    private Tamano.Medida size;
    private final List<Extra> extras;

    private BeverageRecipe(BeverageKind beverageKind, Tamano.Medida size, List<Extra> extras) {
        this.beverageKind = Objects.requireNonNull(beverageKind, "beverage kind cannot be null");
        this.size = Objects.requireNonNull(size, "size cannot be null");
        this.extras = new ArrayList<>(extras);
    }

    public static BeverageRecipe forBeverage(BeverageKind beverageKind) {
        return new BeverageRecipe(beverageKind, Tamano.Medida.PEQUENO, List.of());
    }

    public BeverageRecipe withSize(Tamano.Medida size) {
        this.size = Objects.requireNonNull(size, "size cannot be null");
        return this;
    }

    public BeverageRecipe withExtraShot() {
        extras.add(new Extra(ExtraKind.SHOT, null));
        return this;
    }

    public BeverageRecipe withSyrup(String flavor) {
        extras.add(new Extra(ExtraKind.SYRUP, flavor));
        return this;
    }

    public BeverageKind getBeverageKind() {
        return beverageKind;
    }

    public Tamano.Medida getSize() {
        return size;
    }

    public List<Extra> getExtras() {
        return List.copyOf(extras);
    }

    public BeverageRecipe copy() {
        return new BeverageRecipe(beverageKind, size, extras);
    }

    @Override
    public BeverageRecipe clone() {
        return copy();
    }
}
