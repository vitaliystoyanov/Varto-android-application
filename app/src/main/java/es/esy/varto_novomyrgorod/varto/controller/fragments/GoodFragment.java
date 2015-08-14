package es.esy.varto_novomyrgorod.varto.controller.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.Collections;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.model.database.DBHelper;
import es.esy.varto_novomyrgorod.varto.model.database.DBSalesProvider;
import es.esy.varto_novomyrgorod.varto.model.pojo.GoodObject;

public class GoodFragment extends Fragment {
    public static final String FROM = "FROM";
    public static final String CATALOG = "CATALOG";
    private static final String TAG_LOG = "DBG";
    private ListView goodsList;
    private String fromFragment;
    private String catalog;

    public static GoodFragment newInstance(String source, String catalog){
        GoodFragment fragment = new GoodFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FROM, source);
        bundle.putString(CATALOG, catalog);
        fragment.setArguments(bundle);

        return fragment;
    }

    private String getStringFromBundle(String key){
        return this.getArguments().getString(key);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shares, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        goodsList = (ListView) getActivity().findViewById(R.id.list_shares);
        fromFragment = getStringFromBundle(FROM);
        catalog = getStringFromBundle(CATALOG);

        new LoadGoodsAsyncTask().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public class LoadGoodsAsyncTask extends AsyncTask<Void, Void, List<GoodObject>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected List<GoodObject> doInBackground(Void... args) {
            List<GoodObject> goodObjectsList = Collections.emptyList();
            Log.i("DBG", "[GoodsFragment]  LoadGoodsAsyncTask working! ");
            if (catalog != null) {
                Log.i("DBG", "[GoodsFragment]  Selected a catalog: " + catalog);
                DBSalesProvider dbSalesProvider = new DBSalesProvider(new DBHelper(getActivity()));
                goodObjectsList = dbSalesProvider.getSalesObjectsFromSQLDatabase(fromFragment, catalog);
                Log.i("DBG", "[GoodsFragment]  List<GoodObject> return: " + goodObjectsList.size());
            }

            return goodObjectsList;
        }

        @Override
        protected void onPostExecute(final List<GoodObject> result) {
            if (!result.isEmpty()) {
//                NewsListviewAdapter adapter = new NewsListviewAdapter(getActivity(), result);
//                goodsList.setAdapter(adapter);
            }
        }
    }
}
