package positions;

import java.util.ArrayList;
import java.util.Arrays;

import simulation.Team;

/**
 * Class for the OL player. 5 on field at a time.
 *
 * @author Achi
 */
public class PlayerOL extends Player {

    //Size Config
    private final int hAvg = 76;
    private final int hMax = 5;
    private final int hMin = -3;
    private final int wAvg = 310;
    private final int wMax = 40;
    private final int wMin = -35;

    private final int attrDropper1 = 5;
    private final int attDropper2 = 5;
    private final int attDropper3 = 15;
    private final int attDropper4 = 15;

    private final int[] overallWt = {2,2,1,1};

    //New Player
    public PlayerOL(String nm, int yr, int stars, Team t) {
        position = "OL";
        height = hAvg + (int) (Math.random() * ((hMax - hMin) + 1)) + hMin;
        weight = wAvg + (int) (Math.random() * ((wMax - wMin) + 1)) + wMin;
        name = nm;
        year = yr;
        team = t;

        wasRedshirt = getWasRedshirtStatus();

        createGenericAttributes(stars, attrDropper1, attDropper2, attDropper3, attDropper4, overallWt);
        recruitRating = getScoutingGrade();

        recruitTolerance = (int) ((60 - team.teamPrestige) / olImportance);
        cost = getInitialCost();
        cost = (int) (cost / olImportance);

        cost = getLocationCost();
        if (cost < 0) cost = (int) Math.random() * 5 + 1;
    }

    //Custom Player
    public PlayerOL(String nm, int yr, int stars, Team t, Boolean custom) {
        position = "OL";
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
    public PlayerOL(Team t, String data) {
        position = "OL";
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
    public PlayerOL(Player p, Team t) {
        position = "OL";
        team = t;
        transferPlayer(p);
    }

    public int getRatRunBlock() {
        return ratAttr1;
    }

    public int getRatPassBlock() {
        return ratAttr2;
    }

    public int getRatStrength() {
        return ratAttr3;
    }

    public int getRatVision() {
        return ratAttr4;
    }


    @Override
    public int getOverall() {
        return (overallWt[0]*ratAttr1 + overallWt[1]*ratAttr2 + overallWt[2]*ratAttr3 + overallWt[3]*ratAttr4) / Arrays.stream(overallWt).sum();
    }


    @Override
    public int getHeismanScore() {
        int teamFactor = 0;
        if(getGames() < 0) return 0;
        teamFactor = (getGames()*50 + getGamesStarted()*250) + (+100*getOLPassYards() +200*getOLRunYards() -75*getOLSacksAllowed());
        return ratOvr * 100 + teamFactor + getConfPrestigeBonus();
    }

    @Override
    public String getAllTeamStats() {
        return "OL " + name + " [" + getYrStr() + "]\n \t\t" +
                df2.format(getOLYardsPerRush()) + " YPR, " + df2.format(getOLYardsPerPass()) + " YPP, " + getOLSacksAllowed()+ " Sacks\n";
    }

    @Override
    public int getCareerScore() {
        return ratOvr * (year) * 50;
    }


    //PLAYER RATINGS FOR PROFILE
    @Override
    public String getPlayerRatings() {

        return  "Run Block," + getRatRunBlock() + ",Pass Block," + getRatPassBlock() + ",Strength," + getRatStrength() + ",Vision," + getRatVision();

    }

    @Override
    public String getInfoForLineup() {
        if (injury != null)
            return getInitialName() + " [" + getYrStr() + "] " + ratOvr + "/" + getPotRating(team.HC.ratTalent) + " " + injury.toString();
        return getInitialName() + " [" + getYrStr() + "] " + ratOvr + "/" + getPotRating(team.HC.ratTalent) + " (" +
                getRatStrength() + ", " + getRatRunBlock() + ", " + getRatPassBlock() + ", " + getRatVision() + ")";
    }

}
