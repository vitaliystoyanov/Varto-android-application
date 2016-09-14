package es.esy.varto_novomyrgorod.varto.adapters;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.pojo.News;

public class NewsAdapter extends ArrayAdapter<String> {
    private static final int DURATION_MILLIS_ANIMATION = 400;
    private List<News> data;
    private Context context;

    public NewsAdapter(Context context, List<News> data) {
        super(context, R.layout.item_news_listview);
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
    @SuppressWarnings("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_news_listview, parent, false);

        ImageView image = (ImageView)view.findViewById(R.id.image_item_news);
        TextView article = (TextView)view.findViewById(R.id.article_item_news);
        TextView title = (TextView)view.findViewById(R.id.title_item_news);
        TextView time = (TextView)view.findViewById(R.id.time_item_news);

        News news = data.get(position);

        title.setText(news.getTitle());
        article.setText(news.getArticle());

        String resultFormat = getFormattedTimeDate(news);
        time.setText(resultFormat);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(DURATION_MILLIS_ANIMATION, true, true, true))
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(news.getImage(), image, options);
        return view;
    }

    private String getFormattedTimeDate(News news) {
        String resultFormat = null;
        try {
            String oldFormat = "yyyy-MM-dd HH:mm:ss";
            String newFormat = "dd MMMM HH:mm";
            SimpleDateFormat oldDateFormat = new SimpleDateFormat(oldFormat, Locale.getDefault());
            SimpleDateFormat newDateFormat = new SimpleDateFormat(newFormat, Locale.getDefault());
            resultFormat = newDateFormat.format(oldDateFormat.parse(news.getCreatedAt()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultFormat;
    }
}
