package comparator;

import java.util.Comparator;

import staff.Staff;

public class CompCoachAllConference implements Comparator<Staff> {
    @Override
    public int compare(Staff a, Staff b) {
        return a.getAllConference() > b.getAllConference() ? -1 : a.getAllConference() == b.getAllConference() ? 0 : 1;
    }
}