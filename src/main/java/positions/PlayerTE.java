package positions;

import java.util.ArrayList;
import java.util.Arrays;

import simulation.Team;

/**
 * Created by ahngu on 10/9/2017.
 * <p>
 * TE should be able to catch the ball, and help block for rushes or passing game
 * Currently using the WR role as base
 */

public class PlayerTE extends Player {
    
    //Size Config
    private final int hAvg = 76;
    private final int hMax = 3;
    private final int hMin = -2;
    private final int wAvg = 254;
    private final int wMax = 30;
    private final int wMin = -20;

    private final int attrDropper1 = 10;
    private final int attDropper2 = 10;
    private final int attDropper3 = 10;
    private final int attDropper4 = 20;
    private final int[] overallWt = {3,2,1,1};


    //New Player
    public PlayerTE(String nm, int yr, int stars, Team t) {
        position = "TE";
        height = hAvg + (int) (Math.random() * ((hMax - hMin) + 1)) + hMin;
        weight = wAvg + (int) (Math.random() * ((wMax - wMin) + 1)) + wMin;
        name = nm;
        year = yr;
        team = t;

        wasRedshirt = getWasRedshirtStatus();

        createGenericAttributes(stars, attrDropper1, attDropper2, attDropper3, attDropper4, overallWt);

        recruitRating = getScoutingGrade();

        recruitTolerance = (int) ((60 - team.teamPrestige) / teImportance);
        cost = getInitialCost();
        cost = (int) (cost / teImportance);

        cost = getLocationCost();
        if (cost < 0) cost = (int) Math.random() * 5 + 1;
    }

    //Custom Player
    public PlayerTE(String nm, int yr, int stars, Team t, Boolean custom) {
        position = "TE";
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
    public PlayerTE(Team t, String data) {
        position = "TE";
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
    public PlayerTE(Player p, Team t) {
        position = "TE";
        team = t;
        transferPlayer(p);
    }

    public int getRatRunBlock() {
        return ratAttr1;
    }

    public int getRatCatch() {
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
        return getRecTDs() * 220 - getFumbles() * 75 + getReceptions() * 2 - getDrops() * 25 + getRecYards() * 3 + ratOvr * 10 + getConfPrestigeBonus();
    }

    @Override
    public String getAwardStats() {
        return "TE " + name + ": " + getHeismanScore() + " votes\n\t\t"
                + team.strRankTeamRecord() + " - " + getYrStr() + "\n\t\t"
                + getRecTDs() + " TDs, " + getFumbles() + " Fum, " + getRecYards() + " Yds\n\n";
    }

    @Override
    public String getAwardDescription() {
        return team.name + " TE " + name + " [" + getYrStr() + "], who had " +
                getRecTDs() + " TDs, just " + getFumbles() + " fumbles, and " +
                getRecYards() + " receiving yards. He led " + team.name +
                " to a " + team.wins + "-" + team.losses + " record and a #" + team.rankTeamPollScore +
                " poll ranking.";
    }

    @Override
    public String getAllTeamStats() {
        return "TE " + name + " [" + getYrStr() + "]\n \t\t" +
                getRecTDs() + " TDs, " + getReceptions() + " Rec, " + getRecYards() + " Yds\n";
    }

    @Override
    public int getCareerScore() {
        return getRecTDs() * 220 - getFumbles() * 75 + getReceptions() * 2 - getDrops() * 25 + getRecYards() * 3 + ratOvr * 10 +
                getCareerRecTDs() * 220 - getCareerFumbles() * 75 + getCareerReceptions() * 3 - getCareerDrops() * 25 + getCareerRecYards() * 3 + ratOvr * 10 * year;
    }

    //PLAYER RATINGS FOR PROFILE
    @Override
    public String getPlayerRatings() {

        return  "Catch," + getRatCatch() + ",Speed," + getRatSpeed() + ",Evasion," + getRatEvasion() + ",Blocking," + getRatRunBlock();

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
                getRatCatch() + ", " + getRatSpeed() + ", " + getRatEvasion() + ", " + getRatRunBlock() + ")";
    }

}
