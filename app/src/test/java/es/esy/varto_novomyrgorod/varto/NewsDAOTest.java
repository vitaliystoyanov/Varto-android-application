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
import es.esy.varto_novomyrgorod.varto.database.dao.NewsDAO;
import es.esy.varto_novomyrgorod.varto.network.APIUrl;
import es.esy.varto_novomyrgorod.varto.network.parsers.NewsParser;
import es.esy.varto_novomyrgorod.varto.pojo.News;
import es.esy.varto_novomyrgorod.varto.pojo.Shop;
import es.esy.varto_novomyrgorod.varto.utility.JsonUtility;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.M)
public class NewsDAOTest {
    private NewsDAO newsDAO;
    private NetworkRequest network;

    @Before
    public void setUp() throws Exception {
        network = new NetworkRequest();
        newsDAO = new NewsDAO(RuntimeEnvironment.application);
    }

    @Test
    public void testUpdate() throws Exception {
        List<News> news = new NewsParser()
                .parse(JsonUtility.toJSONObject(network.call(APIUrl.URL_NEWS)));
        assertThat("List of news is null", news, notNullValue());
        assertThat("Quantity of news is zero", news, not(empty()));
        newsDAO.update(news);
        int expectedQuantity = newsDAO.getAll(Shop.PLUS).size()
                + newsDAO.getAll(Shop.DISHES).size();
        assertThat(news.size(), is(equalTo(expectedQuantity)));
    }

    @After
    public void tearDown() throws Exception {
        DatabaseProvider.close();
    }
}
