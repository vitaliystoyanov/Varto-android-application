package es.esy.varto_novomyrgorod.varto.controller.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import es.esy.varto_novomyrgorod.varto.R;

public class CatalogFragment extends Fragment {
    private ListView list;
    private String fromActivity;

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
        fromActivity = getStringFromBundle("FROM");
        FragmentManager manager = getActivity().getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        list = (ListView) getActivity().findViewById(R.id.list_catalog);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String catalog = parent.getAdapter().getItem(position).toString();
                SharesFragment shares = SharesFragment.newInstance(fromActivity, catalog);
                transaction.replace(R.id.container, shares).commit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    class LoadCatalogsTask extends AsyncTask<Void, Integer, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            return new String[0];
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(final String[] result) {
            if (result != null) {
               getActivity().runOnUiThread(new Runnable() {
                   public void run() {
                       ArrayAdapter<String> adapter = new ArrayAdapter<String>
                               (getActivity(), android.R.layout.simple_list_item_1, result);
                       list.setAdapter(adapter);
                   }
               });
            }
        }
    }
}
