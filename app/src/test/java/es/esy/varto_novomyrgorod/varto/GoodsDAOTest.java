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
import es.esy.varto_novomyrgorod.varto.database.dao.GoodsDAO;
import es.esy.varto_novomyrgorod.varto.network.APIUrl;
import es.esy.varto_novomyrgorod.varto.network.parsers.GoodsParser;
import es.esy.varto_novomyrgorod.varto.pojo.Good;
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
public class GoodsDAOTest {
    private GoodsDAO goodsDAO;
    private NetworkRequest network;

    @Before
    public void setUp() throws Exception {
        network = new NetworkRequest();
        goodsDAO = new GoodsDAO(RuntimeEnvironment.application);
    }

    @Test
    public void testUpdate() throws Exception {
        List<Good> goods = new GoodsParser()
                .parse(JsonUtility.toJSONObject(network.call(APIUrl.URL_GOODS)));
        assertThat("List of goods is null", goods, notNullValue());
        assertThat("Quantity of goods is zero", goods, not(empty()));
        goodsDAO.update(goods);
        int expectedQuantity = goodsDAO.getAll(Shop.PLUS).size()
                + goodsDAO.getAll(Shop.DISHES).size();
        assertThat(goods.size(), is(equalTo(expectedQuantity)));
    }

    @After
    public void tearDown() throws Exception {
        DatabaseProvider.close();
    }
}
