package comparator;

import java.util.Comparator;

import staff.Staff;

public class CompCoachBowlWins implements Comparator<Staff> {
    @Override
    public int compare(Staff a, Staff b) {
        return a.getBowlWins() > b.getBowlWins() ? -1 : a.getBowlWins() == b.getBowlWins() ? 0 : 1;
    }
}
