package comparator;

import java.util.Comparator;

import staff.Staff;

public class CompCoachWins implements Comparator<Staff> {
    @Override
    public int compare(Staff a, Staff b) {
        return a.getWins() > b.getWins() ? -1 : a.getWins() == b.getWins() ? 0 : 1;
    }
}
