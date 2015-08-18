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

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.model.pojo.GoodObject;

public class GoodsListviewAdapter extends ArrayAdapter<String> {
    private static final int DURATION_MILLIS_ANIMATION = 700;
    private List<GoodObject> data;
    private Context context;

    public GoodsListviewAdapter(Context context, List<GoodObject> data) {
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

        ImageView image = (ImageView) view.findViewById(R.id.imageview_good_image_item);
        TextView title = (TextView) view.findViewById(R.id.textview_good_title_item);
        TextView description = (TextView) view.findViewById(R.id.textview_good_description_item);
        TextView time = (TextView) view.findViewById(R.id.textview_good_time_item);
        TextView old_price = (TextView) view.findViewById(R.id.textview_good_old_price_item);
        TextView new_price = (TextView) view.findViewById(R.id.textview_good_new_price_item);

        GoodObject objectItem = data.get(position);
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

    private String getFormattedTimeDate(GoodObject objectItem) {
        String resultFormat = null;
        try {
            String oldFormat = "yyyy-MM-dd HH:mm:ss";
            String newFormat = "dd MMMM HH:mm";
            SimpleDateFormat oldDateFormat = new SimpleDateFormat(oldFormat);
            SimpleDateFormat newDateFormat = new SimpleDateFormat(newFormat);
            resultFormat = newDateFormat.format(oldDateFormat.parse(objectItem.getCreated_at()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultFormat;
    }
}
