package comparator;

import java.util.Comparator;

import positions.Player;

/**
 * Created by ahngu on 11/13/2017.
 */
//League

public class CompPlayerOVR implements Comparator<Player> {
    @Override
    public int compare(Player a, Player b) {
        return a.getOverall() > b.getOverall() ? -1 : a.getOverall() == b.getOverall() ? 0 : 1;
    }
}