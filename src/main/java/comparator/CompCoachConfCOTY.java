package comparator;

import java.util.Comparator;

import staff.Staff;

public class CompCoachConfCOTY implements Comparator<Staff> {
    @Override
    public int compare(Staff a, Staff b) {
        return a.getConfCOTY() > b.getConfCOTY() ? -1 : a.getConfCOTY() == b.getConfCOTY() ? 0 : 1;
    }
}
