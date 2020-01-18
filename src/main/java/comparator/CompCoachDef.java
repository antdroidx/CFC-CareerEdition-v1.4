package comparator;

import java.util.Comparator;

import staff.Staff;

public class CompCoachDef implements Comparator<Staff> {
    @Override
    public int compare(Staff a, Staff b) {
        return a.ratDef > b.ratDef ? -1 : a.ratDef == b.ratDef ? 0 : 1;
    }
}
