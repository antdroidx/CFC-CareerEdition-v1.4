package comparator;

import java.util.Comparator;

import staff.Staff;

public class CompCoachOvr implements Comparator<Staff> {
    @Override
    public int compare(Staff a, Staff b) {
        return a.getStaffOverall(a.overallWt) > b.getStaffOverall(b.overallWt) ? -1 : a.getStaffOverall(a.overallWt) == b.getStaffOverall(b.overallWt) ? 0 : 1;
    }
}
