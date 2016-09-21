package es.esy.varto_novomyrgorod.varto.database.dao.interfaces;

import java.util.HashMap;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.pojo.Shop;

public interface DAOInterface<T> {

    HashMap<Shop, Integer> update(List<T> listOfItems);

    List<T> getAll(Shop shop);

}
