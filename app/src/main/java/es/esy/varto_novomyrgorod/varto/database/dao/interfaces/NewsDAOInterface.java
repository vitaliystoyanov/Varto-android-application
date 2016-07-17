package es.esy.varto_novomyrgorod.varto.database.dao.interfaces;

import java.util.List;

import es.esy.varto_novomyrgorod.varto.pojo.News;
import es.esy.varto_novomyrgorod.varto.pojo.Shop;

public interface NewsDAOInterface extends DAOInterface<News> {

    List<Integer> getAllNewsID(Shop shop);

}
