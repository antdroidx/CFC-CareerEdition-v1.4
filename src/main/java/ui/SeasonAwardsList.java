package ui;

/*
  Created by Achi Jones on 4/18/2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import antdroid.cfbcoach.R;

public class SeasonAwardsList extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final String userTeamAbbr;

    public SeasonAwardsList(Context context, String[] values, String userTeamAbbr) {
        super(context, R.layout.league_history_list_item, values);
        this.context = context;
        this.values = values;
        this.userTeamAbbr = userTeamAbbr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.league_history_list_item, parent, false);
        TextView textTop = rowView.findViewById(R.id.textViewLeagueHistoryTop);
        TextView textMiddle = rowView.findViewById(R.id.textViewLeagueHistoryMiddle);
        TextView textBottom = rowView.findViewById(R.id.textViewLeagueHistoryBottom);

        String[] player = values[position].split("\n");
        if (player.length == 3) {
            textTop.setText(player[0]);
            textMiddle.setText(player[1]);
            textBottom.setText(player[2]);
            if (player[0].split(" ")[0].equals(userTeamAbbr)) {
                // highlight user team players
                textTop.setTextColor(Color.parseColor("#5994de"));
            }
        } else if (player.length == 2) {
            textTop.setText(player[0]);
            textMiddle.setText(player[1]);
            textBottom.setVisibility(View.GONE);
            if (player[0].split(" ")[0].equals(userTeamAbbr)) {
                // highlight user team players
                textTop.setTextColor(Color.parseColor("#5994de"));
            }
        } else {
            textMiddle.setText(values[position]);
        }

        return rowView;
    }
}
