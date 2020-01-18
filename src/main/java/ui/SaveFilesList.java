package ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import antdroid.cfbcoach.R;

public class SaveFilesList extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public SaveFilesList(Context context, String[] values) {
        super(context, R.layout.save_list, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.save_list, parent, false);

        String[] detailSplit = values[position].split(">");
        TextView itemL = rowView.findViewById(R.id.textPlayerStatsLeftChild);
        itemL.setPadding(5, 0, 2, 0);
        itemL.setText(detailSplit[0]);

        return rowView;
    }
}
