package comparator;

import java.util.Comparator;

import positions.PlayerK;

/**
 * Created by ahngu on 11/13/2017.
 */

public class CompPlayerFGMade implements Comparator<PlayerK> {
    @Override
    public int compare(PlayerK a, PlayerK b) {
        return a.getFGMade() > b.getFGMade() ? -1 : a.getFGMade() == b.getFGMade() ? 0 : 1;
    }
}
