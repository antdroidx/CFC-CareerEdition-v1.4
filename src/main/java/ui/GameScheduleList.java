package ui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import antdroid.cfbcoach.MainActivity;
import antdroid.cfbcoach.R;
import simulation.Game;
import simulation.Team;

public class GameScheduleList extends ArrayAdapter<Game> {
    private final Context context;
    private final Game[] games;
    private final Team team;
    private final MainActivity mainAct;

    public GameScheduleList(Context context, MainActivity mainAct, Team team, Game[] games) {
        super(context, R.layout.game_schedule_list_item, games);
        this.context = context;
        this.mainAct = mainAct;
        this.games = games;
        this.team = team;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.game_schedule_list_item, parent, false);
        TextView textLeft = rowView.findViewById(R.id.gameScheduleLeft);
        Button gameButton = rowView.findViewById(R.id.gameScheduleButtonList);
        Button textRight = rowView.findViewById(R.id.gameScheduleRight);

        String[] gameSummary = team.getGameSummaryStr(position);
        textLeft.setText(gameSummary[0]);
        gameButton.setText(gameSummary[1]);
        textRight.setText(gameSummary[2]);

        if (team.gameWLSchedule.size() > position) {
            if (team.gameWLSchedule.get(position).equals("W")) {
                gameButton.setBackgroundColor(Color.parseColor("#FF5994DE"));
            } else if (team.gameWLSchedule.get(position).equals("L")) {
                gameButton.setBackgroundColor(Color.RED);
            } else if (team.gameWLSchedule.get(position).equals("BYE")) {

            }
        }

        gameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if (!games[position].gameName.equals("BYE WEEK")) {
                    mainAct.showGameDialog(games[position]);
                }
            }
        });

        textRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something on click
                if (!games[position].gameName.equals("BYE WEEK")) {
                    if (games[position].awayTeam == team)
                        mainAct.examineTeam(games[position].homeTeam.name);
                    else mainAct.examineTeam(games[position].awayTeam.name);
                }
            }
        });

        return rowView;
    }
}
