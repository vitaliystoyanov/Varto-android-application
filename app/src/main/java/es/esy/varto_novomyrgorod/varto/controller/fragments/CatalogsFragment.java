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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.adapters.CatalogsListviewAdapter;
import es.esy.varto_novomyrgorod.varto.model.database.DBCatalogsProvider;
import es.esy.varto_novomyrgorod.varto.model.database.DBHelper;
import es.esy.varto_novomyrgorod.varto.model.pojo.CatalogObject;

public class CatalogsFragment extends Fragment {
    private ListView list;
    private String fromFragment;
    private LinearLayout backLayout;
    private static final String TAG_LOG = "DBG";
    private ImageView logoImage;

    public static CatalogsFragment newInstance(String source){
        CatalogsFragment fragment = new CatalogsFragment();
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
        backLayout = (LinearLayout) getActivity().findViewById(R.id.back_layout);
        logoImage = (ImageView) getActivity().findViewById(R.id.imageview_catalog_logo);

        fromFragment = getStringFromBundle("FROM");
        FragmentManager manager = getActivity().getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        list = (ListView) getActivity().findViewById(R.id.listview_catalog);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String catalog = parent.getAdapter().getItem(position).toString();
                Log.i(TAG_LOG, "[CatalogsFragment]  Selected a catalog: " + catalog);
                if (catalog != null) {
                    GoodsFragment goodsFragment = GoodsFragment.newInstance(fromFragment, catalog);
                    transaction.replace(R.id.container, goodsFragment).commit();
                }
            }
        });
        new LoadCatalogsAsyncTask().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView textViewStatus = (TextView) getActivity().findViewById(R.id.textview_status);
        textViewStatus.setText(R.string.status_bar_catalogs);
        backLayout.setVisibility(View.VISIBLE);
        switch (fromFragment) {
            case "plus" : {
                logoImage.setImageResource(R.mipmap.logo_vartoplus);
            }
            break;
            case "dishes" : {
                logoImage.setImageResource(R.mipmap.logo_vartodishes);
            }
            break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        backLayout.setVisibility(View.INVISIBLE);
    }

    class LoadCatalogsAsyncTask extends AsyncTask<Void, Integer, List<CatalogObject>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<CatalogObject> doInBackground(Void... params) {
            DBCatalogsProvider dbCatalogsProvider = new DBCatalogsProvider(new DBHelper(getActivity()));

            if (fromFragment != null) {
                Log.i("DBG", "fromFragment = " + fromFragment);
                return dbCatalogsProvider.getCatalogsFromSQLDatabase(fromFragment);
            } else {
                return Collections.emptyList();
            }
        }

        @Override
        protected void onPostExecute(final List<CatalogObject> result) {
            if (!result.isEmpty()) {
                CatalogsListviewAdapter adapter = new CatalogsListviewAdapter(getActivity(), result);
                list.setAdapter(adapter);
            }
        }
    }
}
