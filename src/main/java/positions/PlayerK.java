/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package positions;

import java.util.ArrayList;
import java.util.Arrays;

import simulation.Team;

public class PlayerK extends Player {

    //Size Config
    private final int hAvg = 69;
    private final int hMax = 4;
    private final int hMin = -5;
    private final int wAvg = 150;
    private final int wMax = 35;
    private final int wMin = 15;

    private final int attrDropper1 = 0;
    private final int attDropper2 = 0;
    private final int attDropper3 = 10;
    private final int attDropper4 = 10;
    private final int[] overallWt = {2,2,1,1};
    //New Player
    public PlayerK(String nm, int yr, int stars, Team t) {
        position = "K";
        height = hAvg + (int) (Math.random() * ((hMax - hMin) + 1)) + hMin;
        weight = wAvg + (int) (Math.random() * ((wMax - wMin) + 1)) + wMin;
        name = nm;
        year = yr;
        team = t;

        wasRedshirt = getWasRedshirtStatus();

        createGenericAttributes(stars, attrDropper1, attDropper2, attDropper3, attDropper4, overallWt);
        recruitRating = getScoutingGrade();

        recruitTolerance = (int) ((60 - team.teamPrestige) / kImportance);
        cost = getInitialCost();
        cost = (int) (cost / kImportance);

        cost = getLocationCost();
        if (cost < 0) cost = (int) Math.random() * 5 + 1;
    }

    //Custom Player
    public PlayerK(String nm, int yr, int stars, Team t, Boolean custom) {
        position = "K";
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
    public PlayerK(Team t, String data) {
        position = "K";
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
    public PlayerK(Player p, Team t) {
        position = "K";
        team = t;
        transferPlayer(p);
    }


    public int getRatKickPow() {
        return ratAttr1;
    }

    public int getRatKickAcc() {
        return ratAttr2;
    }

    public int getRatKickPressure() {
        return ratAttr3;
    }

    public int getRatKickFum() {
        return ratAttr4;
    }

    @Override
    public int getOverall() {
        return (overallWt[0]*ratAttr1 + overallWt[1]*ratAttr2 + overallWt[2]*ratAttr3 + overallWt[3]*ratAttr4) / Arrays.stream(overallWt).sum();
    }

    @Override
    public int getHeismanScore() {
        return (getFGMade() * 20 + getXPMade() * 5) * (int) getFGpct() / 100 + ratOvr * 10;
    }

    @Override
    public String getAllTeamStats() {
        return ("K " + name + " [" + getYrStr() + "]\n \t\t" +
                "FGs: " + getFGMade() + "/" + getFGAtt() + ", XPs: " + getXPMade() + "/" + getXPAtt() + "\n");
    }

    @Override
    public int getCareerScore() {
        return (getFGMade() * 20 + getXPMade() * 5) * (int) getFGpct() / 100 + ratOvr * 10 + (getCareerFGMade() * 25 + getCareerXPMade() * 5) * (int) (getCareerFGpct() / 100) + ratOvr * 10 * year;
    }

    //PLAYER RATINGS FOR PROFILE
    @Override
    public String getPlayerRatings() {

        return "Kick Power," + getRatKickPow() + ",Kick Accuracy," + getRatKickAcc() + ",Pressure," + getRatKickPressure() + ",Form," + getRatKickFum();

    }


    @Override
    public ArrayList<String> getCareerStatsList() {
        ArrayList<String> pStats = new ArrayList<>();
        if ((getXPAtt() + getCareerXPAtt()) > 0) {
            pStats.add("XP Made/Att: " + getCareerXPMade() + "/" + getCareerXPAtt() +
                    ">XP Percentage: " + df2.format (100 * (float) (getCareerXPMade() / getCareerXPAtt())) + "%");
        } else {
            pStats.add("XP Made/Att: 0/0>XP Percentage: 0%");
        }

        if ((getFGAtt() + getCareerFGAtt()) > 0) {
            pStats.add("FG Made/Att: " + getCareerFGMade() + "/" + getCareerFGAtt() +
                    ">FG Percentage: " + df2.format(getCareerFGpct()) + "%");
        } else {
            pStats.add("FG Made/Att: 0/0>FG Percentage: 0%");
        }
        pStats.addAll(super.getCareerStatsList());
        return pStats;
    }

    @Override
    public String getInfoForLineup() {
        if (injury != null)
            return getInitialName() + " [" + getYrStr() + "] " + ratOvr + "/" + getPotRating(team.HC.ratTalent) + " " + injury.toString();
        return getInitialName() + " [" + getYrStr() + "] " + ratOvr + "/" + getPotRating(team.HC.ratTalent) + " (" +
                getRatKickPow() + ", " + getRatKickAcc() + ", " + getRatKickFum() + ", " + getRatKickPressure() + ")";
    }

}
