package ui;

/*
  Created by Achi Jones on 3/29/2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import antdroid.cfbcoach.MainActivity;
import antdroid.cfbcoach.R;

public class HallofFameList extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final String userTeam;
    private final MainActivity mainAct;
    private final boolean team;

    public HallofFameList(Context context, String[] values, String userTeam, boolean team, MainActivity mainAct) {
        super(context, R.layout.hall_fame_list_item, values);
        this.context = context;
        this.values = values;
        this.userTeam = userTeam;
        this.team = team;
        this.mainAct = mainAct;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.hall_fame_list_item, parent, false);
        TextView textTop = rowView.findViewById(R.id.textViewHallFameName);

        String[] hof = values[position].split("&");
        final String stats = values[position];

        String[] HOFentry = hof[0].split(":");
        String[] HOFline = HOFentry[1].split(" ");
        String[] HOFyear= values[position].split("Yrs:");
        String[] HOFyear2= HOFyear[1].split("&");
        String[] HOFawards= values[position].split("Awards:");
        String[] HOFawards2= HOFawards[1].split(">");
        String entry = HOFline[1] + " " + HOFline[2] + " " + HOFline[3] + ", " + HOFentry[0] + ", " + HOFyear2[0] + "\n" + HOFawards2[0];
        String entryTeam = HOFline[1] + " " + HOFline[2] + " " + HOFline[3] + ", " + HOFyear2[0] + "\n" + HOFawards2[0];

        if (hof.length > 1) {

            if(team) textTop.setText(entryTeam);
            else textTop.setText(entry);

            if (hof[0].split(":")[0].equals(userTeam)) {
                textTop.setTextColor(Color.parseColor("#5994de"));
            }

        }

        textTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainAct.examineHOF(stats);
            }
        });


        return rowView;
    }
}
