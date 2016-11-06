package cnin0770.memo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by cnin0770 on 08/10/2016.
 */
public class GridViewAdapter extends ArrayAdapter<GridItem> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<GridItem> data = new ArrayList<GridItem>();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<GridItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder viewHolder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageTitle = (TextView) row.findViewById(R.id.text);
            viewHolder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) row.getTag();
        }

        GridItem gridItem = data.get(position);
        viewHolder.imageTitle.setText(gridItem.getTitle());
        viewHolder.image.setImageBitmap(gridItem.getImage());
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }


}
