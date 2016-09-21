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

import es.esy.varto_novomyrgorod.varto.database.Database;
import es.esy.varto_novomyrgorod.varto.database.dao.CatalogsDAO;
import es.esy.varto_novomyrgorod.varto.network.APIUrl;
import es.esy.varto_novomyrgorod.varto.network.parsers.CatalogParser;
import es.esy.varto_novomyrgorod.varto.pojo.Catalog;
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
public class CatalogsDAOTest {
    private CatalogsDAO catalogsDAO;
    private NetworkRequest network;

    @Before
    public void setUp() throws Exception {
        network = new NetworkRequest();
        catalogsDAO = new CatalogsDAO(RuntimeEnvironment.application);
    }

    @Test
    public void testUpdate() throws Exception {
        List<Catalog> catalogs = new CatalogParser()
                .parse(JsonUtility.toJSONObject(network.call(APIUrl.URL_CATALOGS)));
        assertThat("List of catalogs is null", catalogs, notNullValue());
        assertThat("Quantity of catalogs is zero", catalogs, not(empty()));
        catalogsDAO.update(catalogs);
        int expectedQuantity = catalogsDAO.getAll(Shop.PLUS).size()
                + catalogsDAO.getAll(Shop.DISHES).size();
        assertThat(catalogs.size(), is(equalTo(expectedQuantity)));
    }

    @After
    public void tearDown() throws Exception {
        Database.close();
    }
}
