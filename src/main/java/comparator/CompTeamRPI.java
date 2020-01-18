package comparator;

import java.util.Comparator;

import simulation.Team;

/**
 * Created by ahngu on 11/13/2017.
 */

public class CompTeamRPI implements Comparator<Team> {
    @Override

    public int compare(Team a, Team b) {
        return a.teamRPI > b.teamRPI ? -1 : a.teamRPI == b.teamRPI ? 0 : 1;
    }
}
