package positions;

import java.util.ArrayList;
import java.util.Arrays;

import simulation.Team;

/**
 * Class for the RB player.
 *
 * @author Achi
 */
public class PlayerRB extends Player {

    //Size Config
    private final int hAvg = 71;
    private final int hMax = 4;
    private final int hMin = -4;
    private final int wAvg = 215;
    private final int wMax = 30;
    private final int wMin = -20;

    private final int attrDropper1 = 10;
    private final int attDropper2 = 5;
    private final int attDropper3 = 5;
    private final int attDropper4 = 20;

    private final int[] overallWt = {3,3,3,1};

    //New Player
    public PlayerRB(String nm, int yr, int stars, Team t) {
        position = "RB";
        height = hAvg + (int) (Math.random() * ((hMax - hMin) + 1)) + hMin;
        weight = wAvg + (int) (Math.random() * ((wMax - wMin) + 1)) + wMin;
        name = nm;
        year = yr;
        team = t;

        wasRedshirt = getWasRedshirtStatus();

        createGenericAttributes(stars, attrDropper1, attDropper2, attDropper3, attDropper4, overallWt);

        recruitRating = getScoutingGrade();

        recruitTolerance = (int) ((60 - team.teamPrestige) / rbImportance);
        cost = getInitialCost();
        cost = (int) (cost / rbImportance);

        cost = getLocationCost();
        if (cost < 0) cost = (int) Math.random() * 5 + 1;
    }

    //Custom Player
    public PlayerRB(String nm, int yr, int stars, Team t, Boolean custom) {
        position = "RB";
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
    public PlayerRB(Team t, String data) {
        position = "RB";
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
    public PlayerRB(Player p, Team t) {
        position = "RB";
        team = t;
        transferPlayer(p);
    }


    public int getRatSpeed() {
        return ratAttr1;
    }

    public int getRatEvasion() {
        return ratAttr2;
    }

    public int getRatRushPower() {
        return ratAttr3;
    }

    public int getRatCatch() {
        return ratAttr4;
    }

    public int getOverall() {
        return (overallWt[0]*ratAttr1 + overallWt[1]*ratAttr2 + overallWt[2]*ratAttr3 + overallWt[3]*ratAttr4) / Arrays.stream(overallWt).sum();
    }

    public int get3DRBOverall() {

        return (2*ratAttr1 + 2* ratAttr2 + 2*ratAttr4) / 6;
    }

    @Override
    public int getHeismanScore() {
        return getRushTDs() * 150 - getFumbles() * 100 + (int) (getRushYards() * 2.65) + 2 * getReceptions() + (int) (getRecYards() * 2.5) + getRecTDs() * 150 + getKOYards() + getKOTDs() * 150 + getPuntYards() + getPuntTDs() * 150 + ratOvr * 10 + getConfPrestigeBonus();
    }
    
    @Override
    public String getAwardStats() {
        return "RB " + name + ": " + getHeismanScore() + " votes\n\t\t"
                + team.strRankTeamRecord() + " - " + getYrStr() + "\n\t\t"
                + getRushTDs() + " TDs, " + getFumbles() + " Fum, " + getRushYards() + " Yds\n\n";
    }
    
    @Override
    public String getAwardDescription() {
        return team.name + " RB " + name + " [" + getYrStr() + "], who had " +
                getRushTDs() + " TDs, just " + getFumbles() + " fumbles, and " +
                getRushYards() + " rushing yards. He led " + team.name +
                " to a " + team.wins + "-" + team.losses + " record and a #" + team.rankTeamPollScore +
                " poll ranking.";
    }

    @Override
    public String getAllTeamStats() {
        return "RB " + name + " [" + getYrStr() + "]\n \t\t" +
                getRushTDs() + " TDs, " + getFumbles() + " Fum, " + getRushYards() + " Yds\n";
    }
    
    @Override
    public int getCareerScore() {
        return getCareerRushTDs() * 150 - getCareerFumbles() * 75 + (int) (getCareerRushYards() * 2.65) + 2 * getCareerReceptions() + (int) (getCareerRecYards() * 2.5) + getCareerRecTDs() * 150 + getKOYards() + getKOTDs() * 150
                + getPuntYards() + getPuntTDs() * 150 + getCareerKOYards() + getCareerKOTDs() * 150 + getCareerPuntYards() + getCareerPuntTDs() * 150 + ratOvr * 10 * year;
    }

    //PLAYER RATINGS FOR PROFILE
    @Override
    public String getPlayerRatings() {

        return "Speed," + getRatSpeed() + ",Evasion," + getRatEvasion() + ",Power," + getRatRushPower() + ",Catch," + getRatCatch();

    }


    @Override
    public ArrayList<String> getCareerStatsList() {
        ArrayList<String> pStats = new ArrayList<>();
        pStats.add("TDs: " + getCareerRushTDs() + ">Fumbles: " + getCareerFumbles());
        pStats.add("Rush Yards: " + getCareerRushYards() + " yds>Rush Att: " + getCareerRushAtt());
        pStats.add("Yds/Game: " + df2.format((double) getCareerRushYardsPerGame()) + " yds/g>Yards/Att: " + df2.format(getCareerYardsperCarry()) + " yds");
        pStats.add("Rec Yards: " + getCareerRecYards() + " yds>Receptions: " + getCareerReceptions() + " ");
        pStats.add("Rec TDs: " + getCareerRecTDs() + "> ");
        if (getKORets() + getCareerKORets() > 0) {
            pStats.add("Kick Rets: " + getCareerKORets() + ">Kick Ret Yards: " + (getKOYards() + getCareerKOYards()) + " yrds");
            pStats.add("Kick Ret TDs: " + getCareerKOTDs() + ">Ret Avg: " + (double) (getCareerKOYards()/ getCareerKORets()));
        }
        if (getPuntRets() + getCareerPuntRets() > 0) {
            pStats.add("Punt Rets: " + getCareerPuntRets() + ">Punt Ret Yards: " + getCareerPuntYards() + " yrds");
            pStats.add("Punt Ret TDs: " + getCareerPuntTDs() + ">Ret Avg: " + (double) (getCareerPuntYards() / getCareerPuntRets()));
        }
        pStats.addAll(super.getCareerStatsList());
        return pStats;
    }

    @Override
    public String getInfoForLineup() {
        if (injury != null)
            return getInitialName() + " [" + getYrStr() + "] " + ratOvr + "/" + getPotRating(team.HC.ratTalent) + " " + injury.toString();
        return getInitialName() + " [" + getYrStr() + "] " + ratOvr + "/" + getPotRating(team.HC.ratTalent) + " (" +
                getRatRushPower() + ", " + getRatSpeed() + ", " + getRatEvasion() + ", " + getRatCatch() + ")";
    }

}
