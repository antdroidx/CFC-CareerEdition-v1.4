package comparator;

import java.util.Comparator;

import staff.Staff;

public class CompCoachAllAmericans implements Comparator<Staff> {
    @Override
    public int compare(Staff a, Staff b) {
        return a.getAllAmericans() > b.getAllAmericans() ? -1 : a.getAllAmericans() == b.getAllAmericans() ? 0 : 1;
    }
}
