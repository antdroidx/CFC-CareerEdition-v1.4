package comparator;

import java.util.Comparator;

import staff.Staff;

public class CompCoachOff implements Comparator<Staff> {
    @Override
    public int compare(Staff a, Staff b) {
        return a.ratOff > b.ratOff ? -1 : a.ratOff == b.ratOff ? 0 : 1;
    }
}
