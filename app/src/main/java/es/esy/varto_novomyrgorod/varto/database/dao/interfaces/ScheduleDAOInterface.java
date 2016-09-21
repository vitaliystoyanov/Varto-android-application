package es.esy.varto_novomyrgorod.varto.database.dao.interfaces;

import java.util.List;

import es.esy.varto_novomyrgorod.varto.pojo.Schedule;
import es.esy.varto_novomyrgorod.varto.pojo.Shop;

public interface ScheduleDAOInterface {
    void update(List<Schedule> listOfItems);

    Schedule get(Shop shop);
}
