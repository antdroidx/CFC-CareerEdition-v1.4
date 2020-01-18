package staff;

import java.util.ArrayList;
import java.util.Random;

import simulation.Team;

public class HeadCoach extends Staff {

    public final int[] overallWt = {1,1,1,1};

    //New Coach
    public HeadCoach(String nm, int stars, Team t) {
        position = "HC";
        name = nm;
        team = t;

        createStaff(stars);
        ratOvr = getStaffOverall(overallWt);

        createStaffStats();
        history = new ArrayList<>();
        baselinePrestige = team.teamPrestige;
    }

    //New User Coach
    public HeadCoach(String nm, Team t) {
        position = "HC";
        name = nm;
        team = t;
        createStaff(5);
        ratOvr = getStaffOverall(overallWt);

        createStaffStats();
        history = new ArrayList<>();
        baselinePrestige = team.teamPrestige;
    }

    //New Loading Feature
    public HeadCoach(Team t, String data) {
        team = t;

        String x = data.split("&")[0];
        String y = data.split("&")[1];
        String z = data.split("&")[2];

        loadAttributes(x, overallWt);
        loadSeasonStats(y);
        loadAwards(z);
        history = new ArrayList<>();

    }

    //New Loading Feature (Retired/Unemployed)
    public HeadCoach(String data) {
        team = null;

        String x = data.split("&")[0];
        String y = data.split("&")[1];
        String z = data.split("&")[2];

        loadAttributes(x, overallWt);
        loadSeasonStats(y);
        loadAwards(z);
        history = new ArrayList<>();

    }
    
    public HeadCoach(Staff staff, Team t) {
        team = t;
        name = staff.name;
        position = "HC";
        age = staff.age;
        year = staff.year;
        contractYear = staff.contractYear;
        contractLength = staff.contractLength;
        baselinePrestige = team.teamPrestige;
        ratOff = staff.ratOff;
        ratDef = staff.ratDef;
        ratTalent = staff.ratTalent;
        ratDiscipline = staff.ratDiscipline;

        offStrat = staff.offStrat;
        defStrat = staff.defStrat;

        ratImprovement = staff.ratImprovement;

        ratOvr = getStaffOverall(overallWt);

        stats = staff.stats;
        history = staff.history;
        awards = staff.awards;
        retired = staff.retired;
        user = staff.user;

    }

    public float getHCHiring(int[] wt) {
        float value = 0;

        if(!retired) {
            value = ((float)(getStaffOverall(wt) + getCoachCareerScore() - (age/4)) / 2);
        }
        return value;
    }

}