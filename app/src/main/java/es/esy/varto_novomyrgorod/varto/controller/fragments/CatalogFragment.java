package es.esy.varto_novomyrgorod.varto.controller.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.model.database.DBCatalogProvider;
import es.esy.varto_novomyrgorod.varto.model.database.DBHelper;
import es.esy.varto_novomyrgorod.varto.model.database.DBSalesProvider;
import es.esy.varto_novomyrgorod.varto.model.pojo.CatalogObject;
import es.esy.varto_novomyrgorod.varto.model.pojo.SaleObject;

public class CatalogFragment extends Fragment {
    private ListView list;
    private String fromFragment;
    private static final String TAG_LOG = "DBG";

    public static CatalogFragment newInstance(String source){
        CatalogFragment fragment = new CatalogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("FROM", source);
        fragment.setArguments(bundle);

        return fragment;
    }

    private String getStringFromBundle(String key){
        return this.getArguments().getString(key);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_catalog, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        fromFragment = getStringFromBundle("FROM");
        FragmentManager manager = getActivity().getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        list = (ListView) getActivity().findViewById(R.id.list_catalog);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String catalog = parent.getAdapter().getItem(position).toString();
                SharesFragment shares = SharesFragment.newInstance(fromFragment, catalog);
                transaction.replace(R.id.container, shares).commit();
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();

        new LoadCatalogsAsyncTask().execute();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    class LoadCatalogsAsyncTask extends AsyncTask<Void, Integer, List<CatalogObject>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<CatalogObject> doInBackground(Void... params) {
            DBCatalogProvider dbCatalogProvider = new DBCatalogProvider(new DBHelper(getActivity()));

            if (fromFragment != null) {
                return dbCatalogProvider.getCatalogsFromSQLDatabase(fromFragment);
            } else {
                return Collections.emptyList();
            }
        }

        @Override
        protected void onPostExecute(final List<CatalogObject> result) {
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Log.i(TAG_LOG, result.get(i).getName());
                }
                DBSalesProvider dbSalesProvider = new DBSalesProvider(new DBHelper(getActivity()));
                List<SaleObject> saleObjects = dbSalesProvider.getSalesObjectsFromSQLDatabase(fromFragment, "sale");
                for (int i = 0; i < saleObjects.size(); i++) {
                    Log.i(TAG_LOG, saleObjects.get(i).getTitle());
                }
            }
        }
    }
}