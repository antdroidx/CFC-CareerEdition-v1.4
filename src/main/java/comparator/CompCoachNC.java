package comparator;

import java.util.Comparator;

import staff.Staff;

public class CompCoachNC  implements Comparator<Staff> {
    @Override
    public int compare(Staff a, Staff b) {
        return a.getNCWins() > b.getNCWins() ? -1 : a.getNCWins() == b.getNCWins() ? 0 : 1;
    }
}
