package ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import antdroid.cfbcoach.MainActivity;
import antdroid.cfbcoach.R;
import simulation.Game;

public class TeamHome extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final MainActivity mainAct;
    private final Game[] games;
    private int week;

    public TeamHome(Context context, String[] values, MainActivity mainAct,  Game[] games, int week) {
        super(context, R.layout.team_home, values);
        this.context = context;
        this.values = values;
        this.mainAct = mainAct;
        this.games = games;
        this.week = week;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.team_home, parent, false);
        TextView textTeam = rowView.findViewById(R.id.home_teamname);
        TextView textRecord = rowView.findViewById(R.id.home_record);
        TextView textRank = rowView.findViewById(R.id.home_rank);
        TextView textTeamRatings = rowView.findViewById(R.id.home_teamRatings);
        TextView textInjuries = rowView.findViewById(R.id.home_injuries);
        TextView textSuspensions = rowView.findViewById(R.id.home_suspensions);
        TextView textNextGame = rowView.findViewById(R.id.home_nextgame);
        TextView textNews = rowView.findViewById(R.id.home_news);
        TextView textLastGame = rowView.findViewById(R.id.home_lastgame);

        String[] teamStat = values[position].split("&");
        textTeam.setText(teamStat[0]);
        textRank.setText(teamStat[1]);
        textRecord.setText(teamStat[2]);
        textTeamRatings.setText(teamStat[3]);
        textInjuries.setText(teamStat[4]);
        textSuspensions.setText(teamStat[5]);
        textNextGame.setText(teamStat[6]);
        textNews.setText(teamStat[7]);
        textLastGame.setText(teamStat[8]);

        textRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainAct.currPage = 5;
                mainAct.updateRankings();
            }
        });

        textRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainAct.currPage = 5;
                mainAct.updateStandings();
            }
        });

        textTeamRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainAct.currPage = 3;
                mainAct.updateTeamStats();
            }
        });

        textInjuries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainAct.currPage = 1;
                mainAct.viewRoster();
            }
        });

        textSuspensions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainAct.currPage = 1;
                mainAct.viewRoster();
            }
        });

        if(!teamStat[6].contains("Bye") && !teamStat[6].contains("End of Season") ) {
            textNextGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (week >= games.length) week = games.length - 1;
                    mainAct.showGameDialog(games[week]);

                }
            });
        }

        if(!teamStat[8].contains("Bye") && !teamStat[8].contains("No Game") ) {
            textLastGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (week > games.length) week = games.length;
                    mainAct.showGameDialog(games[week - 1]);
                }
            });
        }

        textNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainAct.showNewsStoriesDialog();
            }
        });


        return rowView;
    }
}
