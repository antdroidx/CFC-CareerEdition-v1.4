package positions;

import java.util.ArrayList;
import java.util.Arrays;

import simulation.Team;

/**
 * Created by ahngu on 10/9/2017.
 * <p>
 * I imagine the DL will be a 1:1 swap of the F7 category
 */

public class PlayerDL extends Player {
    
    //Size Config
    private final int hAvg = 75;
    private final int hMax = 5;
    private final int hMin = -5;
    private final int wAvg = 290;
    private final int wMax = 60;
    private final int wMin = -50;

    private final int attrDropper1 = 0;
    private final int attDropper2 = 0;
    private final int attDropper3 = 10;
    private final int attDropper4 = 15;

    private final int[] overallWt = {1,1,1,1};

    //New Player
    public PlayerDL(String nm, int yr, int stars, Team t) {
        position = "DL";
        height = hAvg + (int) (Math.random() * ((hMax - hMin) + 1)) + hMin;
        weight = wAvg + (int) (Math.random() * ((wMax - wMin) + 1)) + wMin;
        name = nm;
        year = yr;
        team = t;

        wasRedshirt = getWasRedshirtStatus();

        createGenericAttributes(stars, attrDropper1, attDropper2, attDropper3, attDropper4, overallWt);
        recruitRating = getScoutingGrade();

        recruitTolerance = (int) ((60 - team.teamPrestige) / dlImportance);
        cost = getInitialCost();
        cost = (int) (cost / dlImportance);

        cost = getLocationCost();
        if (cost < 0) cost = (int) Math.random() * 5 + 1;
    }

    //Custom Player
    public PlayerDL(String nm, int yr, int stars, Team t, Boolean custom) {
        position = "DL";
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
    public PlayerDL(Team t, String data) {
        position = "DL";
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
    public PlayerDL(Player p, Team t) {
        position = "DL";
        team = t;
        transferPlayer(p);
    }


    public int getRatRunStop() {
        return ratAttr1;
    }

    public int getRatTackle() {
        return ratAttr2;
    }

    public int getRatPassRush() {
        return ratAttr3;
    }

    public int getRatStrength() {
        return ratAttr4;
    }


    @Override
    public int getOverall() {
        return (overallWt[0]*ratAttr1 + overallWt[1]*ratAttr2 + overallWt[2]*ratAttr3 + overallWt[3]*ratAttr4) / Arrays.stream(overallWt).sum();
    }


    @Override
    public int getHeismanScore() {
        return getTackles() * 35 + getSacks() * 425 + getFumblesRec() * 425 + getInterceptions() * 425 + ratOvr * 10 + getConfPrestigeBonus();
    }
    
    @Override
    public String getAwardStats() {
        return "DL " + name + ": " + getHeismanScore() + " votes\n\t\t"
                + team.strRankTeamRecord() + " - " + getYrStr() + "\n\t\t"
                + getTackles() + " Tkl, " + getSacks() + " Sacks, " + getFumblesRec() + " Fum\n\n";
    }

    @Override
    public String getAwardDescription() {
        return team.name +
                " DL " + name + " [" + getYrStr() + "], who had " +
                getTackles() + " tackles, " + getSacks() + " sacks, and forced " + getFumblesRec() + " fumbles. He led " + team.name +
                " to a " + team.wins + "-" + team.losses + " record and a #" + team.rankTeamPollScore +
                " poll ranking.";
    }

    @Override
    public String getAllTeamStats() {
        return ("DL " + name + " [" + getYrStr() + "]\n \t\t" +
                getTackles() + " Tkl, " + getSacks() + " Sacks, " + getFumblesRec() + " Fum\n");
    }
    
    @Override
    public int getCareerScore() {
        return getCareerTackles() * 35 + getCareerSacks() * 425 + getCareerFumblesRec() * 425 + getCareerInterceptions() * 425 + ratOvr * year * 10;
    }

    //PLAYER RATINGS FOR PROFILE
    @Override
    public String getPlayerRatings() {

        return "Tackle," + getRatTackle() + ",Strength," + getRatStrength() + ",Run Stop," + getRatRunStop() + ",Pass Rush," + getRatPassRush();

    }


    @Override
    public ArrayList<String> getCareerStatsList() {
        ArrayList<String> pStats = new ArrayList<>();
        pStats.add("Tackles: " + getCareerTackles() + " >Sacks: " + getCareerSacks());
        pStats.add("Fumbles: " + getCareerFumblesRec() + " >Interceptions: " + getCareerInterceptions());
        pStats.addAll(super.getCareerStatsList());
        return pStats;
    }


    @Override
    public String getInfoForLineup() {
        if (injury != null)
            return getInitialName() + " [" + getYrStr() + "] " + ratOvr + "/" + getPotRating(team.HC.ratTalent) + " " + injury.toString();
        return getInitialName() + " [" + getYrStr() + "] " + ratOvr + "/" + getPotRating(team.HC.ratTalent) + " (" +
                getRatStrength() + ", " + getRatRunStop() + ", " + getRatPassRush() + ", " + getRatTackle() + ")";
    }
    
}
