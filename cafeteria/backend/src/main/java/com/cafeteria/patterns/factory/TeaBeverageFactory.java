package com.cafeteria.patterns.factory;

import com.cafeteria.modelo.Bebida;
import com.cafeteria.modelo.ExtraJarabe;
import com.cafeteria.modelo.ExtraShot;
import com.cafeteria.modelo.Tamano;
import com.cafeteria.modelo.Te;

import java.util.Objects;

public class TeaBeverageFactory implements BeverageFactory {
    @Override
    public Bebida createBaseBeverage() {
        return new Te();
    }

    @Override
    public Bebida createExtraShot(Bebida beverage) {
        return new ExtraShot(Objects.requireNonNull(beverage, "beverage cannot be null"));
    }

    @Override
    public Bebida createSyrup(Bebida beverage, String flavor) {
        return new ExtraJarabe(Objects.requireNonNull(beverage, "beverage cannot be null"), flavor);
    }

    @Override
    public Bebida createSizedBeverage(Bebida beverage, Tamano.Medida size) {
        return new Tamano(Objects.requireNonNull(beverage, "beverage cannot be null"), size);
    }
}
