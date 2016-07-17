package es.esy.varto_novomyrgorod.varto.database.dao.interfaces;

import java.util.List;

import es.esy.varto_novomyrgorod.varto.pojo.Good;
import es.esy.varto_novomyrgorod.varto.pojo.Shop;

public interface GoodsDAOInterface extends DAOInterface<Good> {

    List<Integer> getAllID(Shop shop);

    List<Good> getAll(Shop shop, String catalog);

}
