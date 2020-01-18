package comparator;

import java.util.Comparator;

import staff.Staff;

public class CompCoachWinPCT implements Comparator<Staff> {
    @Override
    public int compare(Staff a, Staff b) {
        return a.getWinPCT() > b.getWinPCT() ? -1 : a.getWinPCT() == b.getWinPCT() ? 0 : 1;
    }
}