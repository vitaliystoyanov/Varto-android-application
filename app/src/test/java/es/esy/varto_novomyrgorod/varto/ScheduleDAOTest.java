package es.esy.varto_novomyrgorod.varto;

import android.os.Build;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import es.esy.varto_novomyrgorod.varto.database.DatabaseProvider;
import es.esy.varto_novomyrgorod.varto.database.dao.ScheduleDAO;
import es.esy.varto_novomyrgorod.varto.network.APIUrl;
import es.esy.varto_novomyrgorod.varto.network.parsers.ScheduleParser;
import es.esy.varto_novomyrgorod.varto.pojo.Schedule;
import es.esy.varto_novomyrgorod.varto.pojo.Shop;
import es.esy.varto_novomyrgorod.varto.utility.JsonUtility;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.M)
public class ScheduleDAOTest {

    private ScheduleDAO scheduleDAO;
    private NetworkRequest network;

    @Before
    public void setUp() throws Exception {
        network = new NetworkRequest();
        scheduleDAO = new ScheduleDAO(RuntimeEnvironment.application);
    }

    @Test
    public void testUpdate() throws Exception {
        List<Schedule> schedules = new ScheduleParser()
                .parse(JsonUtility.toJSONObject(network.call(APIUrl.URL_SCHEDULE)));
        assertThat("List of schedules is null", schedules, notNullValue());
        assertThat("Quantity of schedules is zero", schedules, not(empty()));
        scheduleDAO.update(schedules);
        assertThat("Schedule equal null", scheduleDAO.get(Shop.PLUS), is(notNullValue()));
        assertThat("Schedule equal null", scheduleDAO.get(Shop.DISHES), is(notNullValue()));
    }

    @After
    public void tearDown() throws Exception {
        DatabaseProvider.close();
    }
}
