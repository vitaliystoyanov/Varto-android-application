package es.esy.varto_novomyrgorod.varto.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.model.pojo.CatalogObject;

public class CatalogsListviewAdapter extends ArrayAdapter<String> {

    private List<CatalogObject> data;
    private Context context;

    public CatalogsListviewAdapter(Context context, List<CatalogObject> data) {
        super(context, R.layout.item_catalog_listview);
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_catalog_listview, parent, false);

        TextView catalogTextview = (TextView)view.findViewById(R.id.textview_catalog_item);

        CatalogObject objectItem = data.get(position);

        catalogTextview.setText(objectItem.getName());

        return view;
    }
}
