package com.cafeteria.patterns.factory;

import com.cafeteria.modelo.Bebida;
import com.cafeteria.modelo.Tamano;

public interface BeverageFactory {
    Bebida createBaseBeverage();

    Bebida createExtraShot(Bebida beverage);

    Bebida createSyrup(Bebida beverage, String flavor);

    Bebida createSizedBeverage(Bebida beverage, Tamano.Medida size);
}
