package es.esy.varto_novomyrgorod.varto.controller.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import es.esy.varto_novomyrgorod.varto.R;

public class SharesFragment extends Fragment {
    public static final String FROM = "FROM";
    public static final String CATALOG = "CATALOG";
    private ListView sharesList;
    private String fromActivity;
    private String catalog;

    public static SharesFragment newInstance(String source, String catalog){
        SharesFragment fragment = new SharesFragment();
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
        sharesList = (ListView) getActivity().findViewById(R.id.list_shares);
        fromActivity = getStringFromBundle(FROM);
        catalog = getStringFromBundle(CATALOG);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

//    public class LoadSharesTask extends AsyncTask<Void, Void, ArrayList<ItemObject>> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        protected ArrayList<ItemObject> doInBackground(Void... args) {
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(final ArrayList<ItemObject> result) {
//
//            getActivity().runOnUiThread(new Runnable() {
//                public void run() {
//                    NewsListviewAdapter adapter = new NewsListviewAdapter(getActivity(), result);
//                    sharesList.setAdapter(adapter);
//                }
//            });
//        }
//    }
}
