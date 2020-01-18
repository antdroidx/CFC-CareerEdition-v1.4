package ui;

/*
  Created by ahngu on 9/29/2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import antdroid.cfbcoach.MainActivity;
import antdroid.cfbcoach.R;

public class IndividualStats extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;
    private final MainActivity mainact;

    public IndividualStats(Context context, ArrayList<String> values, MainActivity mainact) {
        super(context, R.layout.team_stats_list_item, values);
        this.context = context;
        this.values = values;
        this.mainact = mainact;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.invididual_stats, parent, false);


        TextView textPlayer = rowView.findViewById(R.id.textName);
        TextView textStat0 = rowView.findViewById(R.id.textStat0);
        TextView textStat1 = rowView.findViewById(R.id.textStat1);
        TextView textStat2 = rowView.findViewById(R.id.textStat2);
        TextView textStat3 = rowView.findViewById(R.id.textStat3);
        TextView textStat4 = rowView.findViewById(R.id.textStat4);
        TextView textStat5 = rowView.findViewById(R.id.textStat5);


        final String[] teamStat = values.get(position).split(",");
        textPlayer.setText(teamStat[0]);
        textStat0.setText(teamStat[1]);
        textStat1.setText(teamStat[2]);
        textStat2.setText(teamStat[3]);
        textStat3.setText(teamStat[4]);
        textStat4.setText(teamStat[5]);
        textStat5.setText(teamStat[6]);

        textPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainact.examinePlayer(teamStat[0]);
            }
        });


        return rowView;
    }
}
