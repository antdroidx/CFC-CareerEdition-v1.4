package comparator;

import java.util.Comparator;

import positions.PlayerQB;

/**
 * Created by ahngu on 11/13/2017.
 */

public class CompPlayerPassInts implements Comparator<PlayerQB> {
    @Override
    public int compare(PlayerQB a, PlayerQB b) {
        return a.getPassInt() > b.getPassInt() ? -1 : a.getPassInt() == b.getPassInt() ? 0 : 1;
    }
}
