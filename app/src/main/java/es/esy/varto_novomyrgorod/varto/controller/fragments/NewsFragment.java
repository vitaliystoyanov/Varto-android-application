package es.esy.varto_novomyrgorod.varto.controller.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.components.NewsListviewAdapter;
import es.esy.varto_novomyrgorod.varto.model.database.DBHelper;
import es.esy.varto_novomyrgorod.varto.model.database.DBNewsProvider;
import es.esy.varto_novomyrgorod.varto.model.pojo.NewsObject;

public class NewsFragment extends Fragment {
    private ListView newsList;
    private String fromFragment;
    private LinearLayout backLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, null);
    }

    public static NewsFragment newInstance(String source) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("FROM", source);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        newsList = (ListView) getActivity().findViewById(R.id.listNews);
        fromFragment = this.getArguments().getString("FROM");
        backLayout = (LinearLayout) getActivity().findViewById(R.id.back_layout);
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView textViewStatus = (TextView) getActivity().findViewById(R.id.textview_status);
        textViewStatus.setText(R.string.status_bar_news);
        backLayout.setVisibility(View.VISIBLE);
        new loadNewsAsycnTask().execute();
    }

    @Override
    public void onPause() {
        super.onPause();
        backLayout.setVisibility(View.INVISIBLE);
    }

    class loadNewsAsycnTask extends AsyncTask<Void, Void, List<NewsObject>> {
        @Override
        protected List<NewsObject> doInBackground(Void... params) {
            DBNewsProvider dbNewsProvider = new DBNewsProvider(new DBHelper(getActivity()));

            if (fromFragment != null)
                return dbNewsProvider.getNewsFromSQLDatabase(fromFragment);
            else
                return Collections.emptyList();
        }

        @Override
        protected void onPostExecute(final List<NewsObject> objectItems) {
            if (!objectItems.isEmpty()) {
                NewsListviewAdapter adapter = new NewsListviewAdapter(getActivity(), objectItems);
                newsList.setAdapter(adapter);
            }
        }
    }
}