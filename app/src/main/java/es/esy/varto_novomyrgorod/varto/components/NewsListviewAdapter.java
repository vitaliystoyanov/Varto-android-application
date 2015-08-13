package es.esy.varto_novomyrgorod.varto.components;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.model.pojo.NewsObject;

public class NewsListviewAdapter extends ArrayAdapter<String> {

    private List<NewsObject> data;
    private Context context;

    public NewsListviewAdapter(Context context, List<NewsObject> data) {
        super(context, R.layout.item_listview);
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position).getTitle();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_listview, parent, false);

        TextView article = (TextView)view.findViewById(R.id.article);
        TextView title = (TextView)view.findViewById(R.id.title);
        TextView time = (TextView)view.findViewById(R.id.time);

        NewsObject objectItem = data.get(position);

        title.setText(objectItem.getTitle() +"");
        article.setText(objectItem.getArticle());
        time.setText(objectItem.getCreated_at());
        return view;
    }
}
