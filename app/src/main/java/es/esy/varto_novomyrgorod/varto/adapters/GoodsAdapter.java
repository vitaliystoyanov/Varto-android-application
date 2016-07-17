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
import es.esy.varto_novomyrgorod.varto.pojo.Good;

public class GoodsAdapter extends ArrayAdapter<String> {
    private static final int DURATION_MILLIS_ANIMATION = 400;
    private List<Good> data;
    private Context context;

    public GoodsAdapter(Context context, List<Good> data) {
        super(context, R.layout.item_good_listview);
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

        View view = inflater.inflate(R.layout.item_good_listview, parent, false);

        ImageView image = (ImageView) view.findViewById(R.id.image_item_good);
        TextView title = (TextView) view.findViewById(R.id.text_item_good_title);
        TextView description = (TextView) view.findViewById(R.id.text_item_good_description);
        TextView time = (TextView) view.findViewById(R.id.text_good_time);
        TextView old_price = (TextView) view.findViewById(R.id.text_item_good_old_price);
        TextView new_price = (TextView) view.findViewById(R.id.text_item_good_new_price);

        Good objectItem = data.get(position);
        title.setText(objectItem.getTitle());
        description.setText(objectItem.getDescription());

        String resultFormat = getFormattedTimeDate(objectItem);
        time.setText(resultFormat);

        old_price.setText(objectItem.getOld_price() + " грн.");
        new_price.setText(objectItem.getNew_price() + " грн.");

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(DURATION_MILLIS_ANIMATION, true, true, true))
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(objectItem.getImage(), image, options);

        return view;
    }

    private String getFormattedTimeDate(Good objectItem) {
        String resultFormat = null;
        try {
            String oldFormat = "yyyy-MM-dd HH:mm:ss";
            String newFormat = "dd MMMM HH:mm";
            SimpleDateFormat oldDateFormat = new SimpleDateFormat(oldFormat, Locale.getDefault());
            SimpleDateFormat newDateFormat = new SimpleDateFormat(newFormat, Locale.getDefault());
            resultFormat = newDateFormat.format(oldDateFormat.parse(objectItem.getCreated_at()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultFormat;
    }
}
