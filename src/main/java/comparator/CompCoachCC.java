package comparator;

import java.util.Comparator;

import staff.Staff;

public class CompCoachCC  implements Comparator<Staff> {
    @Override
    public int compare(Staff a, Staff b) {
        return a.getConfWins() > b.getConfWins() ? -1 : a.getConfWins() == b.getConfWins() ? 0 : 1;
    }
}