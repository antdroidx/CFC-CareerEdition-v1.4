package staff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import simulation.PlaybookDefense;
import simulation.PlaybookOffense;
import simulation.Team;


public class Staff {
    public Team team;
    public String name;
    public String position;

    public int[] stats;
    public ArrayList<String> history;
    public int[] awards;

    private int statsCount = 10;
    private int awardCount = 7;

    public int[] overallWt = {1, 1, 1, 1};
    public String[] offPlaybook = {"Pro-Style", "Smash Mouth", "West Coast", "Spread", "Read-Option", "Run-Pass Option"};
    public String[] defPlaybook = {"4-3 Man", "4-6 Bear", "Cover-0", "Cover-2", "Cover-3"};

    public int age;
    public int year;
    public int contractYear;
    public int contractLength;
    public int baselinePrestige;
    public int ratOvr;

    public int ratOff;
    public int ratDef;
    public int ratTalent;
    public int ratDiscipline;
    public int ratImprovement;
    public int offStrat;
    public int defStrat;

    private final Random rand = new Random();
    private final int max = 4;
    private final int min = 0;


    public boolean promotionCandidate;
    public boolean retirement;
    public boolean wonConfHC;
    public boolean wonTopHC;
    public boolean retired;
    public boolean user;

    ///////////////////////////////////////////////////


    //SAVE DATA MAKER


    public String saveStaffData() {
        StringBuilder save = new StringBuilder();

        save.append(position + "," + name + "," + age + "," + year + "," + ratOff + "," + ratDef + "," + ratTalent + "," + ratDiscipline + "," + offStrat + "," + defStrat + "," + contractYear + ","
                + contractLength + "," + baselinePrestige + "," + retired + "," + ratOvr + "," + ratImprovement + "," + user + ",&");

        save.append(Arrays.toString(stats).replace("[", "").replace("]", "").replace(" ", "") + "&");

        save.append(Arrays.toString(awards).replace("[", "").replace("]", "").replace(" ", ""));

        return save.toString();
    }


    /////////////////////////////////////////////////////////////////

    //LOAD DATA

    public void loadAttributes(String data, int[] wt) {
        String[] a = data.split(",");
        position = a[0];
        name = a[1];
        age = Integer.parseInt(a[2]);
        year = Integer.parseInt(a[3]);
        ratOff = Integer.parseInt(a[4]);
        ratDef = Integer.parseInt(a[5]);
        ratTalent = Integer.parseInt(a[6]);
        ratDiscipline = Integer.parseInt(a[7]);
        offStrat = Integer.parseInt(a[8]);
        defStrat = Integer.parseInt(a[9]);
        contractYear = Integer.parseInt(a[10]);
        contractLength = Integer.parseInt(a[11]);
        baselinePrestige = Integer.parseInt(a[12]);
        retired = Boolean.parseBoolean(a[13]);
        if(a.length > 16) user = Boolean.parseBoolean(a[16]);
        ratOvr = getStaffOverall(wt);
    }


    public void loadSeasonStats(String s) {
        stats = new int[statsCount];
        for (int j = 0; j < statsCount; j++) {
            stats[j] = Integer.parseInt(s.split(",")[j]);
        }
    }


    public void loadAwards(String awd) {
        String[] a = awd.split(",");
        awards = new int[awardCount];
        for (int i = 0; i < awards.length; i++) {
            awards[i] = Integer.parseInt(a[i]);
        }
    }

    /////////////////////////////////////////////////////



    public void createStaff(int stars) {
        age = 30 + (int) (Math.random() * 28);
        year = 0;
        contractYear = (int) (6 * Math.random());
        contractLength = 6;

        if(position.equals("OC") || position.equals("DC")) {
            contractYear = (int) (3 * Math.random());
            contractLength = 3;
        }

        ratOff = (int) (50 + stars * 5 - 15 * Math.random());
        ratDef = (int) (50 + stars * 5 - 15 * Math.random());

        if(position.equals("OC")) ratDef -= (int)Math.random()*25;
        if(position.equals("DC")) ratOff -= (int)Math.random()*25;

        ratTalent = (int) (45 + 50 * Math.random());
        ratDiscipline = (int) (45 + 45 * Math.random());

        offStrat = (int) (Math.random()*6);
        if (offStrat > 5) offStrat = 5;

        defStrat = (int) (Math.random()*5);
        if (defStrat > 4) defStrat = 4;
    }


    public void createStaffStats() {
        awards = new int[awardCount];
        stats = new int[statsCount];

        for (int i = 0; i < statsCount; i++) {
            stats[i] = 0;
        }

        for (int i = 0; i < awardCount; i++) {
            awards[i] = 0;
        }
    }

    //////////////////////////////////////////

    public void advanceSeason(double offpts, double defpts) {
        int prestigeDiff = team.teamPrestige - team.teamPrestigeStart - team.disciplinePts;

        int oldOvr = getStaffOverall(overallWt);
        age++;
        year++;
        contractYear++;

        double coachScore = (getCoachScore() - team.confPrestige)/10;
        if (coachScore < -4) coachScore = -4;


        ratOff += (2*prestigeDiff + offpts + coachScore)/4;
        if (ratOff > 95) ratOff = 95;
        if (ratOff < 20) ratOff = 20;

        ratDef += (2*prestigeDiff + defpts + coachScore)/4;
        if (ratDef > 95) ratDef = 95;
        if (ratDef < 20) ratDef = 20;

        ratTalent += (2*prestigeDiff  + coachScore)/3;
        if (ratTalent > 95) ratTalent = 95;
        if (ratTalent < 20) ratTalent = 20;

        if (ratDiscipline > 95) ratDiscipline = 95;
        if (ratDiscipline < 15) ratDiscipline = 15;


        if (age > 60 && !team.userControlled) {
            ratOff -= (int) (Math.random() * (age / 10));
            ratDef -= (int) (Math.random() * (age / 10));
            ratTalent -= (int)(Math.random() * (age / 10));
            ratDiscipline -= (int) (Math.random() * (age / 10));
        }

        if (age > 60 && team.userControlled && team.league.isCareerMode() && !team.league.neverRetire ) {
            ratOff -= (int) (Math.random() * (age / 10));
            ratDef -= (int) (Math.random() * (age / 10));
            ratTalent -= (int)(Math.random() * (age / 10));
            ratDiscipline -= (int) (Math.random() * (age / 10));
        }

        ratOvr = getStaffOverall(overallWt);
        ratImprovement = ratOvr - oldOvr;

        recordCumulativePrestige(prestigeDiff);
    }


    public int getCoachScore() {
        int prestigeDiff;
        if (team.league.currentWeek < 15) {
            int[] newPrestige = team.calcSeasonPrestige();
            prestigeDiff = newPrestige[0] - team.teamPrestige;
        } else {
            prestigeDiff = team.teamPrestige - team.teamPrestigeStart;
        }

        return prestigeDiff * 10 + (team.teamStrengthOfWins / 20) + 3 * team.wins - 1 * team.losses + team.confPrestige;
    }

    //For future implementation: tally up the total prestige change over the years for scoring
    public int getCoachCareerScore() {
        if (year < 1) return 0;
        else
            return (5 * (getWins()) - 2 * (getLosses()) + 10 * getNCWins() + 3 * getConfWins() + 10 * getCOTY() + 3 * getConfCOTY() + getAllConference() + 2 * getAllAmericans() + 8*getCumulativePrestige()) / year;
    }


//////////////////////////////////////////////////////////////
    // PROFILE

    public String getInitialName() {
        String[] names = name.split(" ");
        if (names.length > 1) {
            return names[0].substring(0, 1) + ". " + names[1];
        } else {
            return name;
        }
    }

    //@Override
    public String getHCProfileBasics() {
        String status = getCoachStatus();
        String tm = "none";
        if(getCoachStatus().contains("Active")) {
            status = coachStatus();
            tm = team.name;
        }

        return position + ",Age: " + age + "," + tm + "," + ratOvr + "," + getWins() + "," + getLosses() + "," + status + ",Year " + (contractYear+1) + " of " + contractLength;
    }

    public String getHCRatings() {
        String off = Integer.toString(ratOff);
        String def = Integer.toString(ratDef);

        if(getCoachStatus().contains("Active")) {
            off = offPlaybook[offStrat] + "\n" + ratOff;
            def = defPlaybook[defStrat] + "\n" + ratDef;
        }

        return "Offense," + off + ",Talent," + ratTalent + ",Defense," + def + ",Discipline," + ratDiscipline;
    }

    public int getStaffOverall(int[] wt) {
        return (wt[0] * ratOff + wt[1] * ratDef + wt[2] * ratTalent + wt[3] * ratDiscipline) / Arrays.stream(wt).sum();
    }


    //@Override
    public ArrayList<String> getHCFeaturedStats() {
        ArrayList<String> pStats = new ArrayList<>();

        pStats.add("Prestige Change");
        StringBuilder sb = new StringBuilder();

        if(team != null && position.equals("HC")) sb.append(" Current: " + (team.teamPrestige - baselinePrestige) + " (" + (baselinePrestige) + ")");
        else if(team != null && position.equals("OC")) sb.append(" Current: " + baselinePrestige);
        else if(team != null && position.equals("DC")) sb.append(" Current: " + baselinePrestige);

        pStats.add(" HC Total: " + getCumulativePrestige() +"\n" + " Coord Total: " + getCumulativeCoord() + "\n" + sb.toString());

        pStats.add("Coach Awards");
        sb = new StringBuilder();
        if (getCOTY() > 0) sb.append(getCOTY() + " COTY\n");
        if (getConfCOTY() > 0) sb.append(getConfCOTY() + " All-Conf\n");
        if (sb.toString().length() == 0) sb.append("none");
        pStats.add(sb.toString());


        pStats.add("Post-Season");
        pStats.add("Nat Titles: " + getNCWins() + " - " + getNCLosses() + "\nConf Titles: " + getConfWins() + " - " + getConfLosses() + "\nBowls: " + getBowlWins() + " - " + getBowlLosses());

        //AWARDS
        pStats.add("Player Awards");
        sb = new StringBuilder();
        if (getHeismans() > 0) sb.append(getHeismans() + " POTY\n");
        if (getAllAmericans() > 0) sb.append(getAllAmericans() + " All-Amer\n");
        if (getAllConference()> 0) sb.append(getAllConference() + " All-Conf\n");
        if (getTopFreshman() > 0) sb.append(getTopFreshman() + " Top Fresh\n");
        if (getAllFreshman() > 0) sb.append(getAllFreshman() + " All-Fresh\n");
        if (sb.toString().length() == 0) sb.append("none");
        pStats.add(sb.toString());

        return pStats;
    }


    //STRINGS

    public String getYrStr() {
        return " Seasons: " + year;
    }

    public String[] getCoachHistory() {
        String[] hist = new String[history.size()];

        for(int i = history.size(); i > 0; --i) {
            hist[history.size() - i] = history.get(i-1);
        }


        return hist;
    }

    public String coachStatus() {
        String status = "Normal";
        if(team == null) return "";
        if(baselinePrestige > (team.teamPrestige + 5)) status = "Hot Seat";
        else if(baselinePrestige + 7 < (team.teamPrestige)) status = "Secure";
        else if(baselinePrestige + 3 < (team.teamPrestige)) status = "Safe";
        else if (baselinePrestige > (team.teamPrestige + 3)) status = "Unsafe";
        else status = "OK";

        if(team.teamDisciplineScore < 35) status = "Losing Control";
        if(team.teamDisciplineScore < 15) status = "Dysfunctional";
        return status;
    }

    public String getCoachStatus() {
        String status = "Normal";
        if(retired) status = "Retired";
        else if(team == null) status = "Unemployed";
        else status = "Active";

        return status;
    }

    public int getSeasonAwards() {
        int award = 0;
        if(wonConfHC) award = 2;
        if(wonTopHC) award = 4;

        return award;
    }

    public double getWinPCT() {
       double pct = 0;
       if(getWins()+ getLosses() > 0) {
           pct = 100*(double)(getWins())/(getWins()+getLosses());
       }
       return pct;
    }

    public String getSeasonGrade() {
        String grade = "";
        int x = getCoachScore();

        if (x > 140) {
            grade = "A";
        } else if (x > 100) {
            grade = "B";
        } else if (x > 60) {
            grade = "C";
        } else if (x > 40) {
            grade = "D";
        } else {
            grade = "F";
        }

        return grade;
    }



    //STATS

    public int getWins() {
        return stats[0];
    }

    public void recordWins(int x) {
        stats[0] += x;
    }

    public int getLosses() {
        return stats[1];
    }

    public void recordLosses(int x) {
        stats[1] += x;
    }

    public int getConfWins() {
        return stats[2];
    }

    public void recordConfWins(int x) {
        stats[2] += x;
    }

    public int getConfLosses() {
        return stats[3];
    }

    public void recordConfLosses(int x) {
        stats[3] += x;
    }

    public int getBowlWins() {
        return stats[4];
    }

    public void recordBowlWins(int x) {
        stats[4] += x;
    }
    public int getBowlLosses() {
        return stats[5];
    }

    public void recordBowlLosses(int x) {
        stats[5] += x;
    }

    public int getNCWins() {
        return stats[6];
    }

    public void recordNCWins(int x) {
        stats[6] += x;
    }

    public int getNCLosses() {
        return stats[7];
    }

    public void recordNCLosses(int x) {
        stats[7] += x;
    }

    public int getCumulativePrestige() {
        return stats[8];
    }

    public void recordCumulativePrestige(int x) {
        stats[8] += x;
    }

    public int getCumulativeCoord() {
        return stats[9];
    }

    public void recordCumulativeCoord(int x) {
        stats[9] += x;
    }

    ///////////////////////////////////////////////////////////////////////////

    // AWARDS

    //Heismans
    public int getHeismans() {
        return awards[0];
    }

    public void recordHeismans(int x) {
        awards[0] = awards[0] + x;
    }

    //AllAmericans
    public int getAllAmericans() {
        return awards[1];
    }

    public void recordAllAmericans(int x) {
        awards[1] = awards[1] + x;
    }

    //AllConference
    public int getAllConference() {
        return awards[2];
    }

    public void recordAllConference(int x) {
        awards[2] = awards[2] + x;
    }

    //TopFreshman
    public int getTopFreshman() {
        return awards[3];
    }

    public void recordTopFreshman(int x) {
        awards[3] = awards[3] + x;
    }

    //AllFreshman
    public int getAllFreshman() {
        return awards[4];
    }

    public void recordAllFreshman(int x) {
        awards[4] = awards[4] + x;
    }

    //COTY
    public int getCOTY() {
        return awards[5];
    }

    public void recordCOTY(int x) {
        awards[5] = awards[5] + x;
    }

    //ConfCOTY
    public int getConfCOTY() {
        return awards[6];
    }

    public void recordConfCOTY(int x) {
        awards[6] = awards[6] + x;
    }


}