package comparator;

import java.util.Comparator;

import staff.Staff;

public class CompCoachCareerPrestige implements Comparator<Staff> {
    @Override
    public int compare(Staff a, Staff b) {
        return a.getCumulativePrestige() > b.getCumulativePrestige() ? -1 : a.getCumulativePrestige() == b.getCumulativePrestige() ? 0 : 1;
    }
}

