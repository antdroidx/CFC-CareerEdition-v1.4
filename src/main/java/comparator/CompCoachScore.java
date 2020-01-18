package comparator;

import java.util.Comparator;

import staff.Staff;

/**
 * Created by ahngu on 11/13/2017.
 */
//LEAGUE
public class CompCoachScore implements Comparator<Staff> {
    @Override
    public int compare(Staff a, Staff b) {
        return a.getCoachScore() > b.getCoachScore() ? -1 : a.getCoachScore() == b.getCoachScore() ? 0 : 1;
    }
}

