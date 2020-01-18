package comparator;

import java.util.Comparator;

import staff.Staff;

public class CompCoachCOTY implements Comparator<Staff> {
    @Override
    public int compare(Staff a, Staff b) {
        return a.getCOTY() > b.getCOTY() ? -1 : a.getCOTY() == b.getCOTY() ? 0 : 1;
    }
}