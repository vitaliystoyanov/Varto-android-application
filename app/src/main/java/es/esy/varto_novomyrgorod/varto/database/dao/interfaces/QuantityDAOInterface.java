package es.esy.varto_novomyrgorod.varto.database.dao.interfaces;

import es.esy.varto_novomyrgorod.varto.pojo.Quantity;

public interface QuantityDAOInterface {
    void add(Quantity quantity);

    Quantity get();
}
