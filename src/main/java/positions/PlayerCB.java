package positions;

import java.util.ArrayList;
import java.util.Arrays;

import simulation.Team;

public class PlayerCB extends Player {

    //Size Config
    private final int hAvg = 71;
    private final int hMax = 4;
    private final int hMin = -4;
    private final int wAvg = 193;
    private final int wMax = 30;
    private final int wMin = -40;

    private final int attrDropper1 = 5;
    private final int attDropper2 = 5;
    private final int attDropper3 = 10;
    private final int attDropper4 = 10;
    private final int[] overallWt = {3,2,1,2};

    //New Player
    public PlayerCB(String nm, int yr, int stars, Team t) {
        position = "CB";
        height = hAvg + (int) (Math.random() * ((hMax - hMin) + 1)) + hMin;
        weight = wAvg + (int) (Math.random() * ((wMax - wMin) + 1)) + wMin;
        name = nm;
        year = yr;
        team = t;

        wasRedshirt = getWasRedshirtStatus();

        createGenericAttributes(stars, attrDropper1, attDropper2, attDropper3, attDropper4, overallWt);
        recruitRating = getScoutingGrade();

        recruitTolerance = (int) ((60 - team.teamPrestige) / cbImportance);
        cost = getInitialCost();
        cost = (int) (cost / cbImportance);

        cost = getLocationCost();
        if (cost < 0) cost = (int) Math.random() * 5 + 1;
    }

    //Custom Player
    public PlayerCB(String nm, int yr, int stars, Team t, Boolean custom) {
        position = "CB";
        height = hAvg + (int) (Math.random() * ((hMax - hMin) + 1)) + hMin;
        weight = wAvg + (int) (Math.random() * ((wMax - wMin) + 1)) + wMin;
        name = nm;
        year = yr;
        team = t;

        wasRedshirt = getWasRedshirtStatus();

        createGenericAttributes(stars, attrDropper1, attDropper2, attDropper3, attDropper4, overallWt);
        createImportedSkills(stars, attrDropper1, attDropper2, attDropper3, attDropper4, overallWt);

        ratOvr = getOverall();

        if (custom) isWalkOn = true;
        recruitRating = getScoutingGrade();

    }

    //New Loading Feature
    public PlayerCB(Team t, String data) {
        position = "CB";
        team = t;

        if(data.split("&").length > 1) {
            String x = data.split("&")[0];
            String y = data.split("&")[1];
            String z = data.split("&")[2];
            String w = data.split("&")[3];

            loadAttributes(x, overallWt);
            loadSeasonStats(y);
            loadCareerStats(z);
            loadAwards(w);

        } else {

            loadRecruit(data, overallWt);
            createNewStats();

        }

    }


    //Transfer Player to PlayerQB
    public PlayerCB(Player p, Team t) {
        position = "CB";
        team = t;
        transferPlayer(p);
    }


    public int getRatCoverage() {
        return ratAttr1;
    }

    public int getRatSpeed() {
        return ratAttr2;
    }

    public int getRatTackle() {
        return ratAttr3;
    }

    public int getRatJump() {
        return ratAttr4;
    }


    @Override
    public int getOverall() {
        return (overallWt[0]*ratAttr1 + overallWt[1]*ratAttr2 + overallWt[2]*ratAttr3 + overallWt[3]*ratAttr4) / Arrays.stream(overallWt).sum();
    }

    @Override
    public int getHeismanScore() {
        return getTackles() * 25 + getSacks() * 425 + getFumblesRec() * 425 + getInterceptions() * 425 + getDefended() * 100 + getKOYards() + getKOTDs() * 150 + getPuntYards() + getPuntTDs() * 150 + ratOvr * 10 + getConfPrestigeBonus();
    }

    @Override
    public String getAwardStats() {
        return "CB " + name + ": " + getHeismanScore() + " votes\n\t\t"
                + team.strRankTeamRecord() + " - " + getYrStr() + "\n\t\t"
                + getTackles() + " Tkl, " + getSacks() + " Sacks, " + getFumblesRec() + " Fum\n\n";
    }

    @Override
    public String getAwardDescription() {
        return team.name +
                " CB " + name + " [" + getYrStr() + "], who had " +
                getTackles() + " tackles, " + getSacks() + " sacks, and forced " + getFumblesRec() + " fumbles. He led " + team.name +
                " to a " + team.wins + "-" + team.losses + " record and a #" + team.rankTeamPollScore +
                " poll ranking.";
    }

    @Override
    public String getAllTeamStats() {
        return ("CB " + name + " [" + getYrStr() + "]\n \t\t" +
                getTackles() + " Tkl, " + getSacks() + " Sacks, " + getFumblesRec() + " Fum\n");
    }


    @Override
    public int getCareerScore() {
        return  getCareerTackles() * 25 + getCareerSacks() * 425 + getCareerFumblesRec() * 425 + getCareerInterceptions() * 425 + getCareerDefended() * 100
                + getCareerKOYards() + getCareerKOTDs() * 150 + getCareerPuntYards() + getCareerPuntTDs() * 150 + ratOvr * 10 * year;

    }

    //PLAYER RATINGS FOR PROFILE
    @Override
    public String getPlayerRatings() {

        return "Tackle," + getRatTackle() + ",Coverage," + getRatCoverage() + ",Jump," + getRatJump() + ",Speed," + getRatSpeed();

    }



    @Override
    public ArrayList<String> getCareerStatsList() {
        ArrayList<String> pStats = new ArrayList<>();
        pStats.add("Tackles: " + getCareerTackles() + " >Sacks: " + getCareerSacks());
        pStats.add("Fumbles: " + getCareerFumblesRec() + " >Interceptions: " + getCareerInterceptions());
        pStats.addAll(super.getCareerStatsList());
        
        pStats.add("Defended: " +getCareerDefended()+ ">Shutdown Pct: " + df2.format(getCareerShutdownPCT()) + "%");
        if (getCareerKORets() > 0) {
            pStats.add("Kick Rets: " + getCareerKORets() + ">Kick Ret Yards: " + getCareerKOYards() + " yrds");
            pStats.add("Kick Ret TDs: " + getCareerKOTDs() + ">Ret Avg: " + df2.format((float) (getCareerKOYards() / getCareerKORets())));
        }
        if (getCareerPuntRets() > 0) {
            pStats.add("Punt Rets: " + getCareerPuntRets() + ">Punt Ret Yards: " + getCareerPuntYards() + " yrds");
            pStats.add("Punt Ret TDs: " + getCareerPuntTDs() + ">Ret Avg: " + df2.format((float) (getCareerPuntYards() / getCareerPuntRets())));
        }
        pStats.addAll(super.getCareerStatsList());
        return pStats;
    }


    @Override
    public String getInfoForLineup() {
        if (injury != null)
            return getInitialName() + " [" + getYrStr() + "] " + ratOvr + "/" + getPotRating(team.HC.ratTalent) + " " + injury.toString();
        return getInitialName() + " [" + getYrStr() + "] " + ratOvr + "/" + getPotRating(team.HC.ratTalent) + " (" +
                getRatCoverage() + ", " + getRatSpeed() + ", " + getRatTackle() + ", " + getRatJump() + ")";
    }
    
}
