package staff;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import simulation.Team;

public class OC extends Staff {
    public final int[] overallWt = {0,4,3,1};

    //New Coach
    public OC(String nm, int stars, Team t) {
        position = "OC";
        name = nm;
        team = t;
        createStaff(stars);
        ratOvr = getStaffOverall(overallWt);
        createStaffStats();
        history = new ArrayList<>();
        baselinePrestige = 0;

        if (team != null && team.HC != null  ) {
            offStrat = team.HC.offStrat;
            defStrat = team.HC.defStrat;
        }

    }


    //New Loading Feature
    public OC(Team t, String data) {
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
    public OC(String data) {
        team = null;

        String x = data.split("&")[0];
        String y = data.split("&")[1];
        String z = data.split("&")[2];

        loadAttributes(x, overallWt);
        loadSeasonStats(y);
        loadAwards(z);
        history = new ArrayList<>();

    }


    public OC(Staff staff, Team t) {
        team = t;
        name = staff.name;
        position = "OC";
        age = staff.age;
        year = staff.year;
        contractYear = staff.contractYear;
        contractLength = staff.contractLength;
        baselinePrestige = 0;
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

    }

    //creating  free agent OC
    public OC(String nm, int stars) {
        position = "OC";
        name = nm;
        createStaff(stars);
        ratOvr = getStaffOverall(overallWt);
        createStaffStats();
        history = new ArrayList<>();
        baselinePrestige = 0;
    }

    public float getHCHiring(int[] wt) {
        float value = 0;

        if(!retired) {
            value = ((float)(getStaffOverall(wt) + getCoachCareerScore() - (age/4)) / 2);
        }
        return value;
    }

    @Override
    public void advanceSeason(double offpts, double defpts) {

        int oldOvr = getStaffOverall(overallWt);
        age++;
        year++;
        contractYear++;

        ratOff += (int)Math.round(offpts);
        if (ratOff > 95) ratOff = 95;
        if (ratOff < 20) ratOff = 20;

        ratTalent += (int)Math.round(offpts);
        if (ratTalent > 95) ratTalent = 95;
        if (ratTalent < 20) ratTalent = 20;

        if (ratDiscipline > 90) ratDiscipline = 90;
        if (ratDiscipline < 15) ratDiscipline = 15;


        if (age > 60 && !team.userControlled) {
            ratOff -= (int) (Math.random() * (age / 10));
            ratOff -= (int) (Math.random() * (age / 10));
            ratTalent -= (int)(Math.random() * (age / 10));
            ratDiscipline -= (int) (Math.random() * (age / 10));
        }

        ratOvr = getStaffOverall(overallWt);
        ratImprovement = ratOvr - oldOvr;

        baselinePrestige += (int)Math.round(offpts);
        recordCumulativeCoord( (int)Math.round(offpts));
    }
    
}