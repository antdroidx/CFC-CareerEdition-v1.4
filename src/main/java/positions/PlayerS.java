/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package positions;

import java.util.ArrayList;
import java.util.Arrays;

import simulation.Team;

public class PlayerS extends Player {

    //Size Config
    private final int hAvg = 72;
    private final int hMax = 4;
    private final int hMin = -4;
    private final int wAvg = 207;
    private final int wMax = 33;
    private final int wMin = -30;

    private final int attrDropper1 = 5;
    private final int attDropper2 = 10;
    private final int attDropper3 = 15;
    private final int attDropper4 = 10;

    private final int[] overallWt = {3,1,1,1};

    //New Player
    public PlayerS(String nm, int yr, int stars, Team t) {
        position = "S";
        height = hAvg + (int) (Math.random() * ((hMax - hMin) + 1)) + hMin;
        weight = wAvg + (int) (Math.random() * ((wMax - wMin) + 1)) + wMin;
        name = nm;
        year = yr;
        team = t;

        wasRedshirt = getWasRedshirtStatus();

        createGenericAttributes(stars, attrDropper1, attDropper2, attDropper3, attDropper4, overallWt);
        recruitRating = getScoutingGrade();

        recruitTolerance = (int) ((60 - team.teamPrestige) / sImportance);
        cost = getInitialCost();
        cost = (int) (cost / sImportance);

        cost = getLocationCost();
        if (cost < 0) cost = (int) Math.random() * 5 + 1;
    }

    //Custom Player
    public PlayerS(String nm, int yr, int stars, Team t, Boolean custom) {
        position = "S";
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
    public PlayerS(Team t, String data) {
        position = "S";
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
    public PlayerS(Player p, Team t) {
        position = "S";
        team = t;
        transferPlayer(p);
    }


    public int getRatTackle() {
        return ratAttr1;
    }

    public int getRatCoverage() {
        return ratAttr2;
    }

    public int getRatSpeed() {
        return ratAttr3;
    }

    public int getRatRunStop() {
        return ratAttr4;
    }

    @Override
    public int getOverall() {
        return (overallWt[0]*ratAttr1 + overallWt[1]*ratAttr2 + overallWt[2]*ratAttr3 + overallWt[3]*ratAttr4) / Arrays.stream(overallWt).sum();
    }

    @Override
    public int getHeismanScore() {
        return getTackles() * 30 + getSacks() * 425 + getFumblesRec() * 425 + getInterceptions() * 425 + ratOvr * 10 + getConfPrestigeBonus();
    }

    @Override
    public String getAwardStats() {
        return "S " + name + ": " + getHeismanScore() + " votes\n\t\t"
                + team.strRankTeamRecord() + " - " + getYrStr() + "\n\t\t"
                + getTackles() + " Tkl, " + getSacks() + " Sacks, " + getFumblesRec() + " Fum\n\n";
    }

    @Override
    public String getAwardDescription() {
        return team.name +
                " S " + name + " [" + getYrStr() + "], who had " +
                getTackles() + " tackles, " + getSacks() + " sacks, and forced " + getFumblesRec() + " fumbles. He led " + team.name +
                " to a " + team.wins + "-" + team.losses + " record and a #" + team.rankTeamPollScore +
                " poll ranking.";
    }

    @Override
    public String getAllTeamStats() {
        return ("S " + name + " [" + getYrStr() + "]\n \t\t" +
                getTackles() + " Tkl, " + getSacks() + " Sacks, " + getFumblesRec() + " Fum\n");
    }


    @Override
    public int getCareerScore() {
        return getCareerTackles() * 35 + getCareerSacks() * 425 + getCareerFumblesRec() * 425 + getCareerInterceptions() * 425 + ratOvr * year * 10;
    }


    //PLAYER RATINGS FOR PROFILE
    @Override
    public String getPlayerRatings() {

        return "Tackle," + getRatTackle() + ",Coverage," + getRatCoverage() + ",Run Stop," + getRatRunStop() + ",Speed," + getRatSpeed();

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
                getRatCoverage() + ", " + getRatSpeed() + ", " + getRatTackle() + ", " + getRatRunStop() + ")";
    }
    
}
