package comparator;

import java.util.Comparator;

import staff.Staff;

/**
 * Created by Anthony on 12/30/2017.
 */

public class CompCoachCareer implements Comparator<Staff> {
    @Override
    public int compare(Staff a, Staff b) {
        return a.getCoachCareerScore() > b.getCoachCareerScore() ? -1 : a.getCoachCareerScore() == b.getCoachCareerScore() ? 0 : 1;
    }
}
