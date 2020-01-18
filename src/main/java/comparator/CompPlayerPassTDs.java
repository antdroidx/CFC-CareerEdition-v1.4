package comparator;

import java.util.Comparator;

import positions.PlayerQB;

/**
 * Created by ahngu on 11/13/2017.
 */

public class CompPlayerPassTDs implements Comparator<PlayerQB> {
    @Override
    public int compare(PlayerQB a, PlayerQB b) {
        return a.getPassTD() > b.getPassTD() ? -1 : a.getPassTD() == b.getPassTD() ? 0 : 1;
    }
}