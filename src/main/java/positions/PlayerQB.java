package positions;

import java.util.ArrayList;
import java.util.Arrays;

import simulation.Team;

public class PlayerQB extends Player {

    //Size Config
    private final int hAvg = 75;
    private final int hMax = 3;
    private final int hMin = -4;
    private final int wAvg = 223;
    private final int wMax = 30;
    private final int wMin = -35;
    private final int attrDropper1 = 5;
    private final int attDropper2 = 15;
    private final int attDropper3 = 15;
    private final int attDropper4 = 20;
    private final int[] overallWt = {4,4,2,1};

    //New Player
    public PlayerQB(String nm, int yr, int stars, Team t) {
        position = "QB";
        height = hAvg + (int) (Math.random() * ((hMax - hMin) + 1)) + hMin;
        weight = wAvg + (int) (Math.random() * ((wMax - wMin) + 1)) + wMin;
        name = nm;
        year = yr;
        team = t;

        wasRedshirt = getWasRedshirtStatus();

        createGenericAttributes(stars, attrDropper1, attDropper2, attDropper3, attDropper4, overallWt);
        ratAttr4 = (int)(Math.random()*45)+45;
        ratOvr = getOverall();

        recruitRating = getScoutingGrade();

        recruitTolerance = (int) ((60 - team.teamPrestige) / qbImportance);
        cost = getInitialCost();
        cost = (int) (cost / qbImportance);

        cost = getLocationCost();
        if (cost < 0) cost = (int) Math.random() * 5 + 1;
    }

    //Custom Player
    public PlayerQB(String nm, int yr, int stars, Team t, Boolean custom) {
        position = "QB";
        height = hAvg + (int) (Math.random() * ((hMax - hMin) + 1)) + hMin;
        weight = wAvg + (int) (Math.random() * ((wMax - wMin) + 1)) + wMin;
        name = nm;
        year = yr;
        team = t;

        wasRedshirt = getWasRedshirtStatus();

        createGenericAttributes(stars, attrDropper1, attDropper2, attDropper3, attDropper4, overallWt);

        createImportedSkills(stars, attrDropper1, attDropper2, attDropper3, attDropper4, overallWt);
        ratAttr4 = (int)(Math.random()*45)+45;

        ratOvr = getOverall();

        if (custom) isWalkOn = true;
        recruitRating = getScoutingGrade();

    }

    //New Loading Feature
    public PlayerQB(Team t, String data) {
        position = "QB";
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
    public PlayerQB(Player p, Team t) {
        position = "QB";
        team = t;
        transferPlayer(p);
    }

    public int getRatPassPow() {
        return ratAttr1;
    }

    public int getRatPassAcc() {
        return ratAttr2;
    }

    public int getRatEvasion() {
        return ratAttr3;
    }

    public int getRatSpeed() {
        return ratAttr4;
    }

    @Override
    public int getOverall() {
        return (overallWt[0]*ratAttr1 + overallWt[1]*ratAttr2 + overallWt[2]*ratAttr3 + overallWt[3]*ratAttr4) / Arrays.stream(overallWt).sum();
    }

    @Override
    public int getHeismanScore() {
        return getPassTD() * 150 - getPassInt() * 200 + getPassYards() + getRushTDs() * 140 + 3 * getRushYards() + ratOvr * 10 + getConfPrestigeBonus();
    }

    @Override
    public String getAwardStats() {
        return "QB " + name + ": " + getHeismanScore() + " votes\n\t\t"
                + team.strRankTeamRecord() + " - " + getYrStr() + "\n\t\t"
                + getPassTD() + " TDs, " + getPassInt() + " Int, " + getPassYards() + " Yds, " + df2.format(getPasserRating()) + " QBR\n\n";
    }

    @Override
    public String getAwardDescription() {
        return team.name +
                " QB " + name + " [" + getYrStr() + "], who had " +
                getPassTD() + " TDs, just " + getPassInt() + " interceptions, and " +
                getPassYards() + " passing yards. In addition, he ran for " + getRushYards() + " rushing yards and scored " + getRushTDs() + " touchdowns. He led " + team.name +
                " to a " + team.wins + "-" + team.losses + " record and a #" + team.rankTeamPollScore +
                " poll ranking.";
    }

    @Override
    public String getAllTeamStats() {
        return "QB " + name + " [" + getYrStr() + "]\n \t\t" +
                getPassTD() + " TDs, " + getPassInt() + " Int, " + getPassYards() + " Yds\n";
    }


    //Career score for HoF :: target - 35000?
    @Override
    public int getCareerScore() {
        return getCareerPassTD() * 150 - getCareerPassInt() * 200 + getCareerPassYards() + getCareerRushTDs() * 150 + 3 * getCareerRushYards() + ratOvr * 10 * year;
    }


    //PLAYER RATINGS FOR PROFILE
    @Override
    public String getPlayerRatings() {

        return "Pass Strength," + getRatPassPow() + ",Pass Accuracy," + getRatPassAcc() + ",Evasion," + getRatEvasion() + ",Speed," + getRatSpeed();
        
    }



    @Override
    public ArrayList<String> getCareerStatsList() {
        ArrayList<String> pStats = new ArrayList<>();
        pStats.add("Passer Rating " + df2.format(getCareerPasserRating()) + ">Comp Percent: " + df2.format(getCareerPassPCT()) + "%");
        pStats.add("Touchdowns: " + getCareerPassTD() + ">Interceptions: " + getCareerPassInt());
        pStats.add("Pass Yards: " + (getCareerPassYards()) + " yds>Yards/Att: " + df2.format(getCareerYardsPerAttempt()) + " yds");
        pStats.add("Yds/Game: " + df2.format(getCareerPassYardsPerGame()) + " yds/g>Sacks: " + getCareerSacked());
        pStats.add("Rush Yards: " + getCareerRushYards() + ">Rush TDs: " + getCareerRushTDs());
        pStats.addAll(super.getCareerStatsList());
        return pStats;
    }

    @Override
    public String getInfoForLineup() {
        if (injury != null)
            return getInitialName() + " [" + getYrStr() + "] " + ratOvr + "/" + getPotRating(team.HC.ratTalent) + " " + injury.toString();
        return getInitialName() + " [" + getYrStr() + "] " + ratOvr + "/" + getPotRating(team.HC.ratTalent) + " (" +
                getRatPassPow() + ", " + getRatPassAcc() + ", " + getRatEvasion() + ", " + getRatSpeed() + ")";
    }
}
