package es.esy.varto_novomyrgorod.varto;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import es.esy.varto_novomyrgorod.varto.network.APIUrl;
import es.esy.varto_novomyrgorod.varto.network.parsers.CatalogParser;
import es.esy.varto_novomyrgorod.varto.network.parsers.GoodsParser;
import es.esy.varto_novomyrgorod.varto.network.parsers.NewsParser;
import es.esy.varto_novomyrgorod.varto.network.parsers.ScheduleParser;
import es.esy.varto_novomyrgorod.varto.pojo.Catalog;
import es.esy.varto_novomyrgorod.varto.pojo.Good;
import es.esy.varto_novomyrgorod.varto.pojo.News;
import es.esy.varto_novomyrgorod.varto.pojo.Schedule;
import es.esy.varto_novomyrgorod.varto.utility.JsonUtility;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.M)
public class ParsersTest {
    private NetworkRequest network;

    @Before
    public void init() {
        network = new NetworkRequest();
    }

    @Test
    public void testNewsParser() throws Exception {
        List<News> news = new NewsParser().parse(
                JsonUtility.toJSONObject(network.call(APIUrl.URL_NEWS)));
        assertThat("List of news is null", news, notNullValue());
        assertThat("Quantity of news is zero", news, not(empty()));
    }

    @Test
    public void testGoodsParser() throws Exception {
        List<Good> goods = new GoodsParser().parse(
                JsonUtility.toJSONObject(network.call(APIUrl.URL_GOODS)));
        assertThat("List of goods is null", goods, notNullValue());
        assertThat("Quantity of goods is zero", goods, not(empty()));
    }

    @Test
    public void testCatalogsParser() throws Exception {
        List<Catalog> catalogs = new CatalogParser()
                .parse(JsonUtility.toJSONObject(network.call(APIUrl.URL_CATALOGS)));
        assertThat("List of catalogs is null", catalogs, notNullValue());
        assertThat("Quantity of catalogs is zero", catalogs, not(empty()));
    }

    @Test
    public void testScheduleParser() throws Exception {
        List<Schedule> schedules = new ScheduleParser()
                .parse(JsonUtility.toJSONObject(network.call(APIUrl.URL_SCHEDULE)));
        assertThat("List of schedules is null", schedules, notNullValue());
        assertThat("Quantity of schedules is zero", schedules, not(empty()));
    }
}
