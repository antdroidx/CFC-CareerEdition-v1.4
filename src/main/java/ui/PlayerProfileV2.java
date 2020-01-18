package ui;

/*
  Created by Achi Jones on 2/20/2016.
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
import positions.Player;

public class PlayerProfileV2 extends ArrayAdapter<String> {
    private final Context context;
    String basics;
    String ratings;
    ArrayList<String> stats;

    public PlayerProfileV2(Context context, String basics, String ratings, ArrayList<String> stats) {
        super(context, R.layout.player_profile);
        this.context = context;
        this.basics = basics;
        this.ratings = ratings;
        this.stats = stats;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.player_profile, parent, false);

        TextView ppPosition = rowView.findViewById(R.id.ppPosition);
        TextView ppClass = rowView.findViewById(R.id.ppClass);
        TextView ppTeam = rowView.findViewById(R.id.ppTeam);
        TextView ppStars = rowView.findViewById(R.id.ppStars);
        TextView ppHome = rowView.findViewById(R.id.ppHome);
        TextView ppHeight = rowView.findViewById(R.id.ppHeight);
        TextView ppWeight = rowView.findViewById(R.id.ppWeight);
        TextView ppOverall = rowView.findViewById(R.id.ppOverall);

        TextView ppAwareness = rowView.findViewById(R.id.ppAwarness);
        TextView ppCharacter = rowView.findViewById(R.id.ppCharacter);
        TextView ppDurability = rowView.findViewById(R.id.ppDurability);
        TextView ppStatus = rowView.findViewById(R.id.ppStatus);

        TextView ppAttr1Name = rowView.findViewById(R.id.ppAttr1Name);
        TextView ppAttr1 = rowView.findViewById(R.id.ppAttr1);
        TextView ppAttr2Name = rowView.findViewById(R.id.ppAttr2Name);
        TextView ppAttr2 = rowView.findViewById(R.id.ppAttr2);
        TextView ppAttr3Name = rowView.findViewById(R.id.ppAttr3Name);
        TextView ppAttr3 = rowView.findViewById(R.id.ppAttr3);
        TextView ppAttr4Name = rowView.findViewById(R.id.ppAttr4Name);
        TextView ppAttr4 = rowView.findViewById(R.id.ppAttr4);
        

        TextView ppYear = rowView.findViewById(R.id.ppYear);
        TextView ppStat0 = rowView.findViewById(R.id.ppStat0);
        TextView ppStat1 = rowView.findViewById(R.id.ppStat1);
        TextView ppStat2 = rowView.findViewById(R.id.ppStat2);
        TextView ppStat3 = rowView.findViewById(R.id.ppStat3);
        TextView ppStat4 = rowView.findViewById(R.id.ppStat4);
        TextView ppStat5 = rowView.findViewById(R.id.ppStat5);
        TextView ppStat6 = rowView.findViewById(R.id.ppStat6);
        TextView ppStat7 = rowView.findViewById(R.id.ppStat7);
        
        String[] a = basics.split(",");

        ppPosition.setText(a[0]);
        ppClass.setText(a[1]);
        ppTeam.setText(a[2]);
        ppHome.setText(a[3]);
        ppStars.setText(a[4] + " Stars");
        ppHeight.setText(a[5]);
        ppWeight.setText(a[6]);
        ppOverall.setText(a[7]);
        ppCharacter.setText(a[8]);
        ppAwareness.setText(a[9]);
        ppStatus.setText(a[10]);
        ppDurability.setText(a[11]);

        String[] b = ratings.split(",");

        ppAttr1Name.setText(b[0]);
        ppAttr1.setText(b[1]);
        ppAttr2Name.setText(b[2]);
        ppAttr2.setText(b[3]);
        ppAttr3Name.setText(b[4]);
        ppAttr3.setText(b[5]);
        ppAttr4Name.setText(b[6]);
        ppAttr4.setText(b[7]);

        final String[] teamStat = stats.get(position).split(",");
        ppYear.setText(teamStat[0]);
        ppStat0.setText(teamStat[1]);
        ppStat1.setText(teamStat[2]);
        ppStat2.setText(teamStat[3]);
        ppStat3.setText(teamStat[4]);
        ppStat4.setText(teamStat[5]);
        ppStat5.setText(teamStat[6]);
        ppStat6.setText(teamStat[7]);
        ppStat7.setText(teamStat[8]);

        return rowView;
    }

/*    private void colorizeRatings(TextView textV, String rating) {
        String[] ratSplit = rating.split(" ");
        // The last index is always the rating: A+, C, etc
        if (ratSplit.length > 0 && rating.split(",").length == 1) {
            String letter = ratSplit[ratSplit.length - 1];

            if(isInteger(letter)) {
                int pRat = Integer.parseInt(letter);
                if (pRat >= 92) {
                    textV.setTextColor(Color.parseColor("#5994de"));
                } else if (pRat < 92 && pRat >= 84) {
                    textV.setTextColor(Color.parseColor("#00b300"));
                } else if (pRat < 94 && pRat >= 76) {
                    textV.setTextColor(Color.parseColor("#ffc34d"));
                } else if (pRat < 76 && pRat >= 68) {
                    textV.setTextColor(Color.parseColor("#e68a00"));
                } else {
                    textV.setTextColor(Color.RED);
                }
            } else {

                } if (letter.equals("Active")) {
                } else if (letter.equals("Redshirt") || letter.equals("Medical") || letter.equals("Transfer")) {
                    textV.setTextColor(Color.DKGRAY);
                } else if (letter.equals("Injured")) {
                    textV.setTextColor(Color.parseColor("#ffc34d"));
                } else if (letter.equals("Suspended")) {
                    textV.setTextColor(Color.RED);
                }
            }
        }*/

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
