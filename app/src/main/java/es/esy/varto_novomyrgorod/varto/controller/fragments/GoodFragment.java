package es.esy.varto_novomyrgorod.varto.controller.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.adapters.GoodsListviewAdapter;
import es.esy.varto_novomyrgorod.varto.model.database.DBHelper;
import es.esy.varto_novomyrgorod.varto.model.database.DBSalesProvider;
import es.esy.varto_novomyrgorod.varto.model.pojo.GoodObject;

public class GoodFragment extends Fragment {
    private LinearLayout backLayout;
    private static final String FROM = "FROM";
    private static final String CATALOG = "CATALOG";
    private static final String TAG_LOG = "DBG";
    private ListView goodsList;
    private String fromFragment;
    private String catalog;
    private ImageView logoImage;

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
        return inflater.inflate(R.layout.fragment_goods, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        backLayout = (LinearLayout) getActivity().findViewById(R.id.back_layout);
        goodsList = (ListView) getActivity().findViewById(R.id.list_shares);
        logoImage = (ImageView) getActivity().findViewById(R.id.imageview_goods_logo);
        fromFragment = getStringFromBundle(FROM);
        catalog = getStringFromBundle(CATALOG);

        new LoadGoodsAsyncTask().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView textViewStatus = (TextView) getActivity().findViewById(R.id.textview_status);
        textViewStatus.setText(R.string.status_bar_goods);
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
                GoodsListviewAdapter adapter = new GoodsListviewAdapter(getActivity(), result);
                goodsList.setAdapter(adapter);
            }
        }
    }
}
