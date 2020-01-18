/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package positions;

import java.util.ArrayList;
import java.util.Arrays;

import simulation.Team;

public class PlayerWR extends Player {
    
    //Size Config
    private final int hAvg = 73;
    private final int hMax = 4;
    private final int hMin = -5;
    private final int wAvg = 206;
    private final int wMax = 30;
    private final int wMin = -40;

    private final int attrDropper1 = 10;
    private final int attDropper2 = 10;
    private final int attDropper3 = 15;
    private final int attDropper4 = 10;

    private final int[] overallWt = {2,3,2,1};

    //New Player
    public PlayerWR(String nm, int yr, int stars, Team t) {
        position = "WR";
        height = hAvg + (int) (Math.random() * ((hMax - hMin) + 1)) + hMin;
        weight = wAvg + (int) (Math.random() * ((wMax - wMin) + 1)) + wMin;
        name = nm;
        year = yr;
        team = t;

        wasRedshirt = getWasRedshirtStatus();

        createGenericAttributes(stars, attrDropper1, attDropper2, attDropper3, attDropper4, overallWt);
        recruitRating = getScoutingGrade();

        recruitTolerance = (int) ((60 - team.teamPrestige) / wrImportance);
        cost = getInitialCost();
        cost = (int) (cost / wrImportance);

        cost = getLocationCost();
        if (cost < 0) cost = (int) Math.random() * 5 + 1;
    }

    //Custom Player
    public PlayerWR(String nm, int yr, int stars, Team t, Boolean custom) {
        position = "WR";
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
    public PlayerWR(Team t, String data) {
        position = "WR";
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
    public PlayerWR(Player p, Team t) {
        position = "WR";
        team = t;
        transferPlayer(p);
    }

    public int getRatSpeed() {
        return ratAttr1;
    }

    public int getRatCatch() {
        return ratAttr2;
    }

    public int getRatEvasion() {
        return ratAttr3;
    }

    public int getRatJump() {
        return ratAttr4;
    }

    public int getOverall() {
        return (overallWt[0]*ratAttr1 + overallWt[1]*ratAttr2 + overallWt[2]*ratAttr3 + overallWt[3]*ratAttr4) / Arrays.stream(overallWt).sum();
    }

    public int getSlotOverall() {
        return (ratAttr2 + ratAttr3) / 2;
    }

    @Override
    public int getHeismanScore() {
        return getRecTDs() * 150 - getFumbles() * 75 + getReceptions() * 2 - getDrops() * 25 + (int) (getRecYards() * 2.65) + getKOYards() + getKOTDs() * 120 + getPuntYards() + getPuntTDs() * 120 + ratOvr * 10 + getConfPrestigeBonus();
    }
    
    @Override
    public String getAwardStats() {
        return "WR " + name + ": " + getHeismanScore() + " votes\n\t\t"
                + team.strRankTeamRecord() + " - " + getYrStr() + "\n\t\t"
                + getRecTDs() + " TDs, " + getFumbles() + " Fum, " + getRecYards() + " Yds\n\n";
    }
    
    @Override
    public String getAwardDescription() {
        return team.name + " WR " + name + " [" + getYrStr() + "], who had " +
                getRecTDs() + " TDs, just " + getFumbles() + " fumbles, and " +
                getRecYards() + " receiving yards. He led " + team.name +
                " to a " + team.wins + "-" + team.losses + " record and a #" + team.rankTeamPollScore +
                " poll ranking.";
    }

    @Override
    public String getAllTeamStats() {
        return "WR " + name + " [" + getYrStr() + "]\n \t\t" +
                getRecTDs() + " TDs, " + getReceptions() + " Rec, " + getRecYards() + " Yds\n";
    }

    @Override
    public int getCareerScore() {
        return getRecTDs() * 150 - getFumbles() * 75 + getReceptions() * 2 - getDrops() * 25 + (int) (getRecYards() * 2.65) + ratOvr * 10
                + getCareerRecTDs() * 150 - getCareerFumbles() * 75 + getCareerReceptions() * 2 - getCareerDrops() * 25 + (int) (getCareerRecYards() * 2.65)
                + getPuntYards() + getPuntTDs() * 150 + getCareerKOYards() + getCareerKOTDs() * 150 + getCareerPuntYards() + getCareerPuntTDs() * 150 + ratOvr * 10 * year;

    }

    //PLAYER RATINGS FOR PROFILE
    @Override
    public String getPlayerRatings() {

        return  "Catch," + getRatCatch() + ",Speed," + getRatSpeed() + ",Evasion," + getRatEvasion() + ",Jump," + getRatJump();

    }


    @Override
    public ArrayList<String> getCareerStatsList() {
        ArrayList<String> pStats = new ArrayList<>();
        pStats.add("TDs: " + getCareerRecTDs() + ">Fumbles: " + getCareerFumbles());
        pStats.add("Rec Yards: " + getCareerRecYards() + " yds>Receptions: " + getCareerReceptions());
        pStats.add("Targets: " + getCareerTargets()+ ">Drops: " + getCareerDrops());
        pStats.add("Catch Percent: " + df2.format(getCareerCatchPCT()) + "%>Yards/Tgt: " + df2.format(getCareerYardsperTGT()) + " yds");
        pStats.add("Yds/Game: " + df2.format(getCareerRecYardsPerGame()) + " yds/g>WR/QB Rating: " + (df2.format(getWRPassRatingCareer())));
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
                getRatCatch() + ", " + getRatSpeed() + ", " + getRatEvasion() + ", " + getRatJump() + ")";
    }

}
