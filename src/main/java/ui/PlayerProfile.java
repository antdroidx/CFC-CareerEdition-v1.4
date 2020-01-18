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

import antdroid.cfbcoach.R;

public class PlayerProfile extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public PlayerProfile(Context context, String[] values) {
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
        if (detailSplit.length == 2) {
            TextView itemL = rowView.findViewById(R.id.textPlayerStatsLeftChild);
            TextView itemR = rowView.findViewById(R.id.textPlayerStatsRightChild);

            if (values[position].substring(0, 3).equals("[B]")) {
                // Bold it
                itemL.setText(values[position].substring(3));
                itemR.setTypeface(null, Typeface.BOLD);
            } else {
                itemL.setText(detailSplit[0]);
                itemR.setText(detailSplit[1]);
                //colorizeRatings(itemL, detailSplit[0]);
                //colorizeRatings(itemR, detailSplit[1]);
            }

            TextView itemC = rowView.findViewById(R.id.textPlayerStatsCenter);
            itemC.setText("");
        } else {
            // Only one, center it
            TextView itemC = rowView.findViewById(R.id.textPlayerStatsCenter);
            if (values[position].substring(0, 3).equals("[B]")) {
                // Bold it
                itemC.setText(values[position].substring(3));
                itemC.setTypeface(null, Typeface.BOLD);
                itemC.setTextColor(Color.parseColor("#5994de"));
            } else {
                itemC.setText(values[position]);
            }

            TextView itemL = rowView.findViewById(R.id.textPlayerStatsLeftChild);
            itemL.setVisibility(View.GONE);
            TextView itemR = rowView.findViewById(R.id.textPlayerStatsRightChild);
            itemR.setVisibility(View.GONE);
        }

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
