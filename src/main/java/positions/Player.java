package positions;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import simulation.Injury;
import simulation.Team;

/**
 * Base player class that others extend. Has name, overall, potential, and football IQ.
 *
 * @author Achi
 */
public class Player {

    public Team team;
    public String name;
    public String position;
    public int year;
    public int ratOvr;
    public int ratOvrStart;
    public int ratPot;
    public int ratIntelligence;
    public int ratDurability;
    public int ratImprovement;

    public int ratAttr1;
    public int ratAttr2;
    public int ratAttr3;
    public int ratAttr4;
    public int[] overallWt = {2, 2, 1, 1};

    public int cost;
    int progression;
    public int homeState;
    public int character;
    public int height;
    public int weight;
    public boolean isSuspended;
    public int weeksSuspended;
    public int troubledTimes;
    public double talentNFL;

    public int[] stats;
    public ArrayList<int[]> careerStats;
    public int[] awards;

    public boolean wonHeisman;
    public boolean wonAllAmerican;
    public boolean wonAllConference;
    public boolean wonTopFreshman;
    public boolean wonAllFreshman;

    public boolean isRedshirt;
    public boolean wasRedshirt;
    public boolean isMedicalRS;
    public boolean isTransfer;
    public boolean isGradTransfer;
    public boolean isWalkOn;

    public int recruitRating; // 0 - 5 0 = walk-on ; 1-5 = star scout rating

    public boolean isInjured;
    public Injury injury;

    final int attrBase = 50;
    final int ratBase = 60;
    final int yearFactor = 5;
    final double starFactor = 2.5;
    final double customFactor = 4.5;
    final int ratTolerance = 20;
    int recruitTolerance = 50;
    final int costBaseRating = 35;
    final int locationDiscount = 18;
    final int minGamesPot = 4;
    final int allConfPotBonus = 4;
    final int allAmericanBonus = 5;
    final int allFreshmanBonus = 4;
    final int topBonus = 3;

    final int endseason = 40;
    final int endseasonFactor = 15;
    final int endseasonBonus = 30;

    final double qbImportance = 1;
    final double rbImportance = 1.5;
    final double wrImportance = 2;
    final double teImportance = 4;
    final double olImportance = 2.5;
    final double kImportance = 5;
    final double dlImportance = 2.5;
    final double lbImportance = 2.5;
    final double cbImportance = 2.5;
    final double sImportance = 1.5;

    //game simulation data
    public int gameSnaps;
    public int gameFatigue;
    public double gameSim; //will be used for game sim calculations
    public int posDepth;
    public int gamePassAtempts;
    public int gamePassComplete;
    public int gamePassYards;
    public int gamePassTDs;
    public int gamePassInts;
    public int gameRushAttempts;
    public int gameRushYards;
    public int gameRushTDs;
    public int gameTargets;
    public int gameReceptions;
    public int gameRecYards;
    public int gameRecTDs;
    public int gameDrops;
    public int gameFumbles;
    public int gameTackles;
    public int gameSacks;
    public int gameInterceptions;
    public int gameDefended;
    public int gameIncomplete;
    public int gameFGAttempts;
    public int gameFGMade;
    public int gameXPAttempts;
    public int gameXPMade;

    public final DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    public final DecimalFormat df2 = new DecimalFormat("#.##", symbols);

    public final String[] offensePos = {"QB", "RB", "WR", "TE"};
    public final String[] defensePos = {"DE", "DT", "DL", "LB", "CB", "S"};
    public final String[] olPos = {"OL", "T", "G", "C"};
    public final String[] kickPos = {"K", "P"};

    private final int offStats = 24;
    private final int defStats = 16;
    private final int kickStats = 7;
    private final int olStats = 9;
    private final int awardCount = 5;


    //PLAYER CREATION

    public void createGenericAttributes(int stars, int attrDropper1, int attrDropper2, int attrDropper3, int attrDropper4, int[] wt) {
        ratPot = (int) (attrBase + 50 * Math.random());
        ratIntelligence = (int) (attrBase + 50 * Math.random());
        ratDurability = (int) (attrBase + 50 * Math.random());
        character = (int) (attrBase + 50 * Math.random());
        homeState = (int) (Math.random() * 50);

        ratAttr1 = (int) (ratBase + year * yearFactor + stars * starFactor - ratTolerance * Math.random()) - (int) (Math.random() * attrDropper1);
        ratAttr2 = (int) (ratBase + year * yearFactor + stars * starFactor - ratTolerance * Math.random()) - (int) (Math.random() * attrDropper2);
        ratAttr3 = (int) (ratBase + year * yearFactor + stars * starFactor - ratTolerance * Math.random()) - (int) (Math.random() * attrDropper3);
        ratAttr4 = (int) (ratBase + year * yearFactor + stars * starFactor - ratTolerance * Math.random()) - (int) (Math.random() * attrDropper4);

        ratOvr = getOverall();

        recruitRating = getScoutingGrade();

        recruitTolerance = (int) ((60 - team.teamPrestige) / qbImportance);
        cost = getInitialCost();
        cost = (int) (cost / qbImportance);

        cost = getLocationCost();
        if (cost < 0) cost = (int) Math.random() * 5 + 1;

        createNewStats();
    }

    public void createImportedSkills(int stars, int attrDropper1, int attrDropper2, int attrDropper3, int attrDropper4, int[] wt) {
        ratAttr1 = (int) (ratBase + stars * customFactor - ratTolerance * Math.random()) - (int) (Math.random() * attrDropper1);
        ratAttr2 = (int) (ratBase + stars * customFactor - ratTolerance * Math.random()) - (int) (Math.random() * attrDropper2);
        ratAttr3 = (int) (ratBase + stars * customFactor - ratTolerance * Math.random()) - (int) (Math.random() * attrDropper3);
        ratAttr4 = (int) (ratBase + stars * customFactor - ratTolerance * Math.random()) - (int) (Math.random() * attrDropper4);
        ratOvr = getOverall();
    }

    public int getOverall() {
        return (overallWt[0]*ratAttr1 + overallWt[1]*ratAttr2 + overallWt[2]*ratAttr3 + overallWt[3]*ratAttr4) / Arrays.stream(overallWt).sum();
    }

    public void createNewStats() {
        careerStats = new ArrayList<>();
        awards = new int[awardCount];

        if (Arrays.asList(offensePos).contains(position)) stats = new int[offStats];
        else if (Arrays.asList(defensePos).contains(position)) stats = new int[defStats];
        else if (Arrays.asList(olPos).contains(position)) stats = new int[olStats];
        else stats = new int[kickStats];

        for (int i = 0; i < stats.length; i++) {
            stats[i] = 0;
        }

        for (int i = 0; i < 5; i++) {
            careerStats.add(new int[stats.length]);
        }

        for (int i = 0; i < awards.length; i++) {
            awards[i] = 0;
        }

        recordSeason();
    }

    //RECRUITING STATE AND COST DATA

    public int getRegion() {
        int location;
        location = homeState / 10;
        return location;
    }

    int getInitialCost() {
        return (int) ((Math.pow((float) ratOvr - costBaseRating, 2) / 5) + (int) Math.random() * recruitTolerance);
    }

    int getLocationCost() {
        double locFactor = Math.abs(team.location - (homeState / 10)) - 2.5;
        cost = cost + (int) (Math.random() * (locFactor * locationDiscount));
        if (cost < 0) cost = (int) Math.random() * 5 + 1;
        return cost;
    }


    ///////////////////////////////////////////////////


    //SAVE DATA MAKER

    public String savePlayerData() {
        StringBuilder save = new StringBuilder();

        save.append(position + "," + name + "," + year + "," + ratPot + "," + ratIntelligence + "," + ratDurability + "," + ratAttr1 + "," + ratAttr2 + "," + ratAttr3 + "," + ratAttr4 + ","
                + isRedshirt + "," + wasRedshirt + "," + isTransfer + "," + isGradTransfer + "," + homeState + "," + character + "," + recruitRating + "," + height + "," + weight + "," + ratOvr + "," + ratImprovement + ",&");

        save.append(Arrays.toString(stats).replace("[", "").replace("]", "").replace(" ", "") + "&");


        for (int i = 0; i < careerStats.size(); i++) {
            save.append(Arrays.toString(careerStats.get(i)).replace("[", "").replace("]", "").replace(" ", "") + "!");
        }
        save.append("&");
        save.append(Arrays.toString(awards).replace("[", "").replace("]", "").replace(" ", ""));

        return save.toString();
    }


    /////////////////////////////////////////////////////////////////

    //LOAD DATA

    public void loadAttributes(String data, int[] wt) {
        String[] a = data.split(",");
        position = a[0];
        name = a[1];
        year = Integer.parseInt(a[2]);
        ratPot = Integer.parseInt(a[3]);
        ratIntelligence = Integer.parseInt(a[4]);
        ratDurability = Integer.parseInt(a[5]);
        ratAttr1 = Integer.parseInt(a[6]);
        ratAttr2 = Integer.parseInt(a[7]);
        ratAttr3 = Integer.parseInt(a[8]);
        ratAttr4 = Integer.parseInt(a[9]);
        isRedshirt = Boolean.parseBoolean(a[10]);
        wasRedshirt = Boolean.parseBoolean(a[11]);
        isTransfer = Boolean.parseBoolean(a[12]);
        isGradTransfer = Boolean.parseBoolean(a[13]);
        homeState = Integer.parseInt(a[14]);
        character = Integer.parseInt(a[15]);
        recruitRating = Integer.parseInt(a[16]);
        height = Integer.parseInt(a[17]);
        weight = Integer.parseInt(a[18]);
        ratOvr = getOverall();
    }

    public void loadRecruit(String data, int[] wt) {
        String[] a = data.split(",");
        position = a[0];
        name = a[1];
        year = Integer.parseInt(a[2]);
        homeState = Integer.parseInt(a[3]);
        character = Integer.parseInt(a[4]);
        ratIntelligence = Integer.parseInt(a[5]);
        recruitRating = Integer.parseInt(a[6]);
        isTransfer = Boolean.parseBoolean(a[7]);
        wasRedshirt = Boolean.parseBoolean(a[8]);
        ratPot = Integer.parseInt(a[9]);
        ratDurability = Integer.parseInt(a[10]);
        ratAttr1 = Integer.parseInt(a[13]);
        ratAttr2 = Integer.parseInt(a[14]);
        ratAttr3 = Integer.parseInt(a[15]);
        ratAttr4 = Integer.parseInt(a[16]);
        height = Integer.parseInt(a[17]);
        weight = Integer.parseInt(a[18]);
        ratOvr = getOverall();
    }

    public void loadSeasonStats(String s) {

        if (Arrays.asList(offensePos).contains(position)) stats = new int[offStats];
        else if (Arrays.asList(defensePos).contains(position)) stats = new int[defStats];
        else if (Arrays.asList(olPos).contains(position)) stats = new int[olStats];
        else stats = new int[kickStats];

        for (int j = 0; j < stats.length; j++) {
            stats[j] = Integer.parseInt(s.split(",")[j]);
        }
    }

    public void loadCareerStats(String s) {
        careerStats = new ArrayList<>();

        if (Arrays.asList(offensePos).contains(position)) stats = new int[offStats];
        else if (Arrays.asList(defensePos).contains(position)) stats = new int[defStats];
        else if (Arrays.asList(olPos).contains(position)) stats = new int[olStats];
        else stats = new int[kickStats];

        for (int i = 0; i < 5; i++) {
            careerStats.add(new int[stats.length]);
        }

        String[] x = s.split("!");

        for (int i = 0; i < x.length; i++) {
            String[] y = x[i].split(",");
            for (int j = 0; j < y.length; j++) {
                careerStats.get(i)[j] = Integer.parseInt(y[j]);
            }
        }
    }

    public void loadAwards(String awd) {
        String[] a = awd.split(",");
        awards = new int[awardCount];
        for (int i = 0; i < awards.length; i++) {
            awards[i] = Integer.parseInt(a[i]);
        }
        recordSeason();
    }


    ///////////////////////////////////////////////////////////////

    public void transferPlayer(Player p) {
        name = p.name;
        year = p.year;
        height = p.height;
        weight = p.weight;
        ratPot = p.ratPot;
        ratIntelligence = p.ratIntelligence;
        ratDurability = p.ratDurability;
        character =  p.character;
        homeState = p.homeState;
        isRedshirt = p.isRedshirt;
        wasRedshirt = p.wasRedshirt;
        recruitRating = p.recruitRating;

        ratAttr1 = p.ratAttr1;
        ratAttr2 = p.ratAttr2;
        ratAttr3 = p.ratAttr3;
        ratAttr4 = p.ratAttr4;

        ratOvr = getOverall();

        stats = p.stats;
        careerStats = p.careerStats;
        awards = p.awards;

        isTransfer = p.isTransfer;
        isGradTransfer = p.isGradTransfer;
    }



    ///////////////////////////////////////////////////

    //MIDSEASON UPGRADES

    public void midSeasonProgression() {
        final int ratOvrStart = ratOvr;

        if (Arrays.asList(offensePos).contains(position)) progression = getProgressionOff();
        else if (Arrays.asList(defensePos).contains(position)) progression = getProgressionDef();
        else progression = getProgression();

        double games = getMidSeasonBonus();

        ratIntelligence += (int) (Math.random() * games)/1.5;
        ratAttr1 += (int) (Math.random() * games);
        ratAttr2 += (int) (Math.random() * games);
        ratAttr3 += (int) (Math.random() * games);
        ratAttr4 += (int) (Math.random() * games)/1.5;

        ratOvr = getOverall();
        ratImprovement = ratOvr - ratOvrStart;
    }

    //ADVANCE SEASON UPGRADES

    public void advanceSeason() {

        genericAdvanceSeason();
        addSeasonAwards();
        checkRedshirt();
    }

    public void genericAdvanceSeason() {
        double games = getGamesBonus();
        if(ratOvrStart == 0) ratOvrStart = ratOvr;

        if (!isMedicalRS) {
            if (wonAllConference) ratPot += (int) Math.random() * allConfPotBonus;
            if (wonAllAmerican) ratPot += (int) Math.random() * allAmericanBonus;
            if (wonAllFreshman) ratPot += (int) Math.random() * allFreshmanBonus;
            if (wonTopFreshman) ratPot += (int) Math.random() * topBonus;
            if (wonHeisman) ratPot += (int) Math.random() * topBonus;

            if (Arrays.asList(offensePos).contains(position)) progression = getProgressionOff();
            else if (Arrays.asList(defensePos).contains(position)) progression = getProgressionDef();
            else progression = getProgression();

            if (year > 2 && games < minGamesPot) ratPot -= (int) (Math.random() * 15);

            ratIntelligence += ((int) (Math.random() * (progression + games - endseason)) / endseasonFactor) / 1.5;
            ratAttr1 += (int) (Math.random() * (progression + games - endseason)) / endseasonFactor;
            ratAttr2 += (int) (Math.random() * (progression + games - endseason)) / endseasonFactor;
            ratAttr3 += (int) (Math.random() * (progression + games - endseason)) / endseasonFactor;
            ratAttr4 += ((int) (Math.random() * (progression + games - endseason)) / endseasonFactor) / 1.5;

            if (Math.random() * 100 < progression) {
                //breakthrough
                ratAttr1 += ((int) (Math.random() * (progression + games - endseasonBonus)) / endseasonFactor) / 1.5;
                ratAttr2 += ((int) (Math.random() * (progression + games - endseasonBonus)) / endseasonFactor) / 1.5;
                ratAttr3 += ((int) (Math.random() * (progression + games - endseasonBonus)) / endseasonFactor) / 1.5;
                ratAttr4 += ((int) (Math.random() * (progression + games - endseasonBonus)) / endseasonFactor) / 1.5;
            }

            if(getChemistryProgression() > 0) character += getChemistryProgression()/2;
            if(character > 100) character = 100;

            durabilityProgression();
        }

        ratOvr = getOverall();
        ratImprovement = ratOvr - ratOvrStart;
    }

    public void addSeasonAwards() {
        if (wonHeisman) recordHeismans(1);
        if (wonAllAmerican) recordAllAmericans(1);
        if (wonAllConference) recordAllConference(1);
        if (wonAllFreshman) recordTopFreshman(1);
        if (wonTopFreshman) recordAllFreshman(1);
    }

    public void checkRedshirt() {

        if(isRedshirt || isMedicalRS) {
            team.redshirtList.add(position + " " + name + "  [" + ratOvr + "]");
        }

        if (isTransfer || isRedshirt || isMedicalRS) {
            isTransfer = false;
            isRedshirt = false;
            isMedicalRS = false;
            wasRedshirt = true;
        } else if (getGames() <= 4 && !wasRedshirt) {
            team.redshirtList.add(position + " " + name + "  [" + ratOvr + "]");
            wasRedshirt = true;
        } else {
            seasonStatstoCareer();
            year++;
        }
    }

    public void seasonStatstoCareer() {
        for (int i = 0; i < stats.length; i++) {
            careerStats.get(year)[i] = stats[i];
            stats[i] = 0;
        }
    }

    public boolean getWasRedshirtStatus() {
        wasRedshirt = false;
        int rs = (int) (Math.random() * 5);
        if (year > 1 && rs > 1) wasRedshirt = true;

        return wasRedshirt;
    }

    public int getSeasonAwards() {
        int awd = 0;
        if (wonAllFreshman) awd = 1;
        if (wonAllConference) awd = 2;
        if (wonAllAmerican) awd = 3;
        if (wonHeisman) awd = 4;
        return awd;
    }


    //PLAYER SCORING

    public int getHeismanScore() {
        int adjGames = getGamesStarted();
        if (adjGames > 11) adjGames = 11;
        return ratOvr * adjGames + team.confPrestige * 5;
    }

    public int getCareerScore() {
        int adjGames = getCareerGames();
        return ratOvr * adjGames + team.confPrestige * 5;
    }

    int getConfPrestigeBonus() {
        return team.teamPrestige * 3 + team.confPrestige * 7 + ((120 - team.rankTeamPollScore) * 3);
    }

    int getScoutingGrade() {
        int pRat;
        int scout = (4 * ratOvr + ratPot) / 5;
        if (year < 2) {
            if (scout > team.five) pRat = 5;
            else if (scout > team.four) pRat = 4;
            else if (scout > team.three) pRat = 3;
            else if (scout > team.two) pRat = 2;
            else pRat = 1;
        } else {
            int calcOvr = ratOvr - (year * (100 - ratPot) / 8);
            if (calcOvr > team.five) pRat = 5;
            else if (calcOvr > team.four) pRat = 4;
            else if (calcOvr > team.three) pRat = 3;
            else if (calcOvr > team.two) pRat = 2;
            else pRat = 1;
        }
        if (isWalkOn) pRat = 0;

        return pRat;
    }


    //Potential Overall Score
    public int getPotRating(int hc) {
        if (!team.league.showPotential) return 0;
        int potential;
        potential = ratOvr + ((3 * ratPot + 2 * hc) / 50) * (4 - year);
        return potential;
    }


    public int getProgression() {
        int num = (ratPot * 2 + team.HC.ratTalent * 1 + 3 * team.teamFacilities + (int) (Math.random() * getChemistryProgression())) / 3;
        return num;
    }

    public int getProgressionOff() {
        int num = (ratPot * 4 + team.HC.ratTalent * 2 + team.OC.ratOff + 7 * team.teamFacilities + (int) (Math.random() * getChemistryProgression())) / 7;
        return num;
    }

    public int getProgressionDef() {
        int num = (ratPot * 4 + team.HC.ratTalent * 2 + team.DC.ratDef + 7 * team.teamFacilities + (int) (Math.random() * getChemistryProgression())) / 7;
        return num;
    }

    public double getGamesBonus() {
        double games = (double) (getGamesStarted()) + (double) ((getGames() - getGamesStarted()) / 3);
        games = (games * 2.5);
        return games;
    }

    public double getMidSeasonBonus() {
        ratOvrStart = ratOvr;
        double games = (double) (getGamesStarted()) + (double) ((getGames() - getGamesStarted()) / 3);
        return games;
    }

    public double getChemistryProgression() {
        return team.teamChemistry - team.league.getAverageTeamChemistry();
    }

    public void durabilityProgression() {
        ratDurability += Math.random() * 2 * year;
        if(ratDurability > 100) ratDurability = 100;
    }


    //STRING MAKERS

    public String getInitialName() {
        String[] names = name.split(" ");
        if (names.length > 1) {
            return names[0].substring(0, 1) + ". " + names[1];
        } else {
            return name;
        }
    }

    public String getYrStr() {
        if (year == 0) {
            return "RS";
        } else if (year == 1) {
            return "Fr";
        } else if (year == 2) {
            return "So";
        } else if (year == 3) {
            return "Jr";
        } else if (year == 4) {
            return "Sr";
        } else if (year >= 5) {
            return "5Y-Sr";
        }
        return "ERROR";
    }

    String getScoutingGradeString() {
        String grade;

        if (recruitRating == 0) {
            grade = "Walk-On";
        } else if (recruitRating == 1) {
            grade = "1 Star";
        } else if (recruitRating == 2) {
            grade = "2 Star";
        } else if (recruitRating == 3) {
            grade = "3 Star";
        } else if (recruitRating == 4) {
            grade = "4 star";
        } else {
            grade = "5 Star";
        }

        return grade;
    }


    private String getFullYrStr() {
        if (year == 0) {
            return "Redshirt";
        } else if (year == 1) {
            return "Freshman";
        } else if (year == 2) {
            return "Sophomore";
        } else if (year == 3) {
            return "Junior";
        } else if (year == 4) {
            return "Senior";
        } else if (year == 4) {
            return "5th Yr Sr";
        }
        return "ERROR";
    }

    public String getHomeState(int region) {
        return team.league.states[region];
    }


    public String getCharacter(int personality) {
        String trait = "";
        if (personality > 91) trait = "Leader";
        else if (personality > 84) trait = "Motivated";
        else if (personality > 75) trait = "";
        else if (personality > 67) trait = "Average";
        else if (personality > 59) trait = "Team Player";
        else if (personality > 54) trait = "Trouble";
        else trait = "Undisciplined";

        return trait;
    }

    String getStatus() {
        if (isTransfer) {
            return "Transfer";
        } else if (isRedshirt) {
            return "Redshirt";
        } else if (isMedicalRS) {
            return "Medical";
        } else if (isInjured) {
            return "Injured";
        } else if (isSuspended) {
            return "Suspended";
        } else {
            return "Active";
        }
    }

    public String getTransferStatus() {
        if (isGradTransfer) return "Grad";
        else return getYrStr();
    }

    public String getTransferStatusMessage() {
        if (isGradTransfer) return "Grad Transfer";
        else return getYrStr() + " Transfer";
    }

    String getHeight() {

        int feet = height / 12;
        int inch = height % 12;

        return feet + "' " + inch + "\"";
    }

    String getWeight() {
        return weight + " lbs";
    }

    public static int getPosNumber(String pos) {
        switch (pos) {
            case "QB":
                return 0;
            case "RB":
                return 1;
            case "WR":
                return 2;
            case "TE":
                return 3;
            case "OL":
                return 4;
            case "K":
                return 5;
            case "DL":
                return 6;
            case "LB":
                return 7;
            case "CB":
                return 8;
            case "S":
                return 9;
            default:
                return 10;

        }
    }

    public String getInfoForLineup() {
        return null;
    }

    public String getInfoLineupInjury() {
        if (injury != null) {
            return getInitialName() + " [" + getYrStr() + "] " + injury.toString();
        }
        return getInitialName() + " [" + getYrStr() + "] " + "Ovr: " + ratOvr + ", Pot: " + getPotRating(team.HC.ratTalent);
    }

    public String getInfoLineupTransfer() {
        return getInitialName() + " [" + getYrStr() + "] " + "Ovr: " + ratOvr + "  Transfer";
    }

    public String getInfoLineupSuspended() {
        return getInitialName() + " [" + getYrStr() + "] " + "Ovr: " + ratOvr + "  Suspended";

    }

    public String getPosNameYrOvrPotTra_Str() {
        return position + " " + name + " [" + getYrStr() + "]>" + "Ovr: " + ratOvr + " [Transfer]";
    }

    public String getGraduatingPlayerInfo() {
        return position + " " + name + " [" + getYrStr() + "]>" + "Ovr: " + ratOvr;
    }

    public String getPosNameYrOvrPot_OneLine() {
        if (injury != null) {
            return position + " " + getInitialName() + " [" + getYrStr() + "]  Ovr: " + ratOvr + " " + injury.toString();
        }
        return position + " " + getInitialName() + " [" + getYrStr() + "] " + " Ovr: " + ratOvr;
    }

    public String getPosNameYrOvr_Str() {
        return team.name + ": " + position + " " + name + " [" + getYrStr() + "] Ovr: " + ratOvr;
    }


    public String getMockDraftStr(int round, int selection, String nflTeam) {
        return "Round " + round + ", Pick " + selection + " : " + nflTeam + "\n" + position + " " + name + "\n" + getFullYrStr()
                + ">\n" + team.name + "\n" + "Overall: " + ratOvr;
    }

    public String getAwardStats() {
        return "";
    }

    public String getAwardDescription() {
        return "";
    }

    public String getAllTeamStats() {
        return "";
    }

    public ArrayList<String> getDetailAllStatsList() {
        return null;
    }

    public ArrayList<String> getCareerStatsList() {
        ArrayList<String> pStats = new ArrayList<>();
        pStats.add("Games: " + getCareerGames() + ">Yrs: " + getYearsPlayed());
        pStats.add("Awards: " + getAwards() + "> ");
        return pStats;
    }

    private String getYearsPlayed() {
        int startYear = team.league.getYear() - year + 1;
        int endYear = team.league.getYear();
        return startYear + "-" + endYear;
    }

    private String getAwards() {
        ArrayList<String> awards = new ArrayList<>();
        int heis = getHeismans();
        int aa = getAllAmericans();
        int ac = getAllConference();
        if (heis > 0) awards.add(heis + "x POTY");
        if (aa > 0) awards.add(aa + "x All-Amer");
        if (ac > 0) awards.add(ac + "x All-Conf");
        if (getTopFreshman() > 0 || wonTopFreshman) awards.add("Top Freshman");
        if (getAllFreshman() > 0 || wonAllFreshman) awards.add("All-Fresh");

        String awardsStr = "";
        for (int i = 0; i < awards.size(); ++i) {
            awardsStr += awards.get(i);
            if (i != awards.size() - 1) awardsStr += ", ";
        }

        return awardsStr;
    }

    //PLAYER PROFILE MAKER

    public ArrayList<String> stringPlayerInfo() {
        ArrayList<String> pAttr = new ArrayList<>();
        pAttr.add("Team: " + team.name + ">Overall: " + ratOvr);
        pAttr.add("Height " + getHeight() + ">Weight: " + getWeight());
        pAttr.add("Home State: " + getHomeState(homeState) + ">Scout Grade: " + getScoutingGradeString());
        return pAttr;
    }

    public ArrayList<String> stringPlayerAttributes() {
        ArrayList<String> pAttr = new ArrayList<>();
        pAttr.add("Character: " + character + " > " + getStatus());
        pAttr.add("Durability: " + ratDurability + ">Intelligence: " + ratIntelligence);
        return pAttr;
    }

    public ArrayList<Integer> getPlayerRatingsValues() {
        ArrayList<Integer> pAttr = new ArrayList<>();

        pAttr.add(ratAttr1);
        pAttr.add(ratAttr2);
        pAttr.add(ratAttr3);
        pAttr.add(ratAttr4);
        return pAttr;
    }

    public String getPlayerRatings() {
        return "ATTR1,RATING,ATTR2,RATING,ATTR3,RATING,ATTR4,RATING,";
    }

    public String getProfileBasics() {
        String rs ="";
        if(wasRedshirt) rs = "RS ";
        return position + "," + rs + getFullYrStr() + "," + team.name + "," + getHomeState(homeState) + "," + getScoutingGradeString() + "," + getHeight() + "," + getWeight() + "," + ratOvr + "," + character + "," + ratIntelligence + "," + getStatus() + "," + ratDurability;
    }



    public ArrayList<String> getPlayerStats() {
        ArrayList<String> pStats = new ArrayList<>();

        if (Arrays.asList(offensePos).contains(position)) {
            if (getCareerPassAtt() > 0) {
                pStats.add("Passing,G,GS,Cmp%,Yrds,TD,INT,SCK,QBR");
                for (int i = 0; i < year; i++) {
                    if (getXPassAtt(i) > 0)
                        pStats.add(getXSeason(i) + "," + getXGames(i) + "," + getXGamesStarted(i) + "," + df2.format(getXPassCompPCT(i)) + "," + getXPassYards(i) + "," + getXPassTD(i) + "," + getXPassInt(i) + "," + getXSacked(i) + "," + df2.format(getXQBR(i)));
                }
                pStats.add(getSeason() + "," + getGames() + "," + getGamesStarted() + "," + df2.format(getPassPCT()) + "," + getPassYards() + "," + getPassTD() + "," + getPassInt() + "," + getSacked() + "," + df2.format(getPasserRating()));
                pStats.add("Career," + getCareerGames() + "," + getCareerGamesStarted() + "," + df2.format(getCareerPassPCT()) + "," + getCareerPassYards() + "," + getCareerPassTD() + "," + getCareerPassInt() + "," + getCareerSacked() + "," + df2.format(getCareerPasserRating()));
                pStats.add(" , , , , , , , , ");
            }


            if (getCareerRushAtt() > 0) {
                pStats.add("Rushing,G,GS,Att,Yrds,TD,Fum,YPC,YPG");
                for (int i = 0; i < year; i++) {
                    if (getXRushAtt(i) > 0)
                        pStats.add(getXSeason(i) + "," + getXGames(i) + "," + getXGamesStarted(i) + "," + getXRushAtt(i) + "," + getXRushYards(i) + "," + getXRushTDs(i) + "," + getXFumbles(i) + "," + df2.format(getXYardsperCarry(i)) + "," + df2.format(getXYardsperGame(i)));
                }
                pStats.add(getSeason() + "," + getGames() + "," + getGamesStarted() + "," + getRushAtt() + "," + getRushYards() + "," + getRushTDs() + "," + getFumbles() + "," + df2.format(getYardsperCarry()) + "," + df2.format(getRushYardsPerGame()));
                pStats.add("Career," + getCareerGames() + "," + getCareerGamesStarted() + "," + getCareerRushAtt() + "," + getCareerRushYards() + "," + getCareerRushTDs() + "," + getCareerFumbles() + "," + df2.format(getCareerYardsperCarry()) + "," + df2.format(getCareerRushYardsPerGame()));
                pStats.add(" , , , , , , , , ");
            }

            if (getCareerReceptions() > 0) {
                pStats.add("Receiving,G,GS,Rec,Yrds,TD,Drp,Cat%,YPC");
                for (int i = 0; i < year; i++) {
                    if (getXTargets(i) > 0)
                        pStats.add(getXSeason(i) + "," + getXGames(i) + "," + getXGamesStarted(i) + "," + getXReceptions(i) + "," + getXRecYards(i) + "," + getXRecTDs(i) + "," + getXDrops(i) + "," + df2.format(getXCatchPCT(i)) + "," + df2.format(getXYPR(i)));
                }
                pStats.add(getSeason() + "," + getGames() + "," + getGamesStarted() + "," + getReceptions() + "," + getRecYards() + "," + getRecTDs() + "," + getDrops() + "," + df2.format(getCatchPCT()) + "," + df2.format(getYPR()));
                pStats.add("Career," + getCareerGames() + "," + getCareerGamesStarted() + "," + getCareerReceptions() + "," + getCareerRecYards() + "," + getCareerRecTDs() + "," + getCareerDrops() + "," + df2.format(getCareerCatchPCT()) + "," + df2.format(getCareerYPR()));
                pStats.add(" , , , , , , , , ");
            }


            if (getCareerKORets() > 0 || getCareerPuntRets() > 0) {
                pStats.add("ST Return,G,GS,KO,KOYrds,KOTD,PtRet,PtYrd,PtTD");
                for (int i = 0; i < year; i++) {
                    if (getXKORets(i) > 0 || getXPuntRets(i) > 0)
                        pStats.add(getXSeason(i) + "," + getXGames(i) + "," + getXGamesStarted(i) + "," + getXKORets(i) + "," + getXKOYards(i) + "," + getXKOTDs(i) + "," + getXPuntRets(i) + "," + getXPuntYards(i) + "," + getXPuntTDs(i));
                }
                pStats.add(getSeason() + "," + getGames() + "," + getGamesStarted() + "," + getKORets() + "," + getKOYards() + "," + getKOTDs() + "," + getPuntRets() + "," + getPuntYards() + "," + getPuntTDs());
                pStats.add("Career," + getCareerGames() + "," + getCareerGamesStarted() + "," + getCareerKORets() + "," + getCareerKOYards() + "," + getCareerKOTDs() + "," + getCareerPuntRets() + "," + getCareerPuntYards() + "," + getCareerPuntTDs());
                pStats.add(" , , , , , , , , ");
            }


        } else if (Arrays.asList(defensePos).contains(position)) {

            if (getGames() > 0) {
                pStats.add("Defense,G,GS,Tckl,Sacks,Int,Fum,Def, ");
                for (int i = 0; i < year; i++) {
                    if (getXGames(i) > 0) {
                        pStats.add(getXSeason(i) + "," + getXGames(i) + "," + getXGamesStarted(i) + "," + getXTackles(i) + "," + getXSacks(i) + "," + getXInterceptions(i) + "," + getXFumblesRec(i) + "," + getXDefended(i) + ", ");
                    }
                }
                pStats.add(getSeason() + "," + getGames() + "," + getGamesStarted() + "," + getTackles() + "," + getSacks() + "," + getInterceptions() + "," + getFumblesRec() + "," + getDefended() + ", ");
                pStats.add("Career," + getCareerGames() + "," + getCareerGamesStarted() + "," + getCareerTackles() + "," + getCareerSacks() + "," + getCareerInterceptions() + "," + getCareerFumblesRec() + "," + getCareerDefended() + ", ");
                pStats.add(" , , , , , , , , ");
            }

            if (getCareerKORets() > 0 || getCareerPuntRets() > 0) {
                pStats.add("ST,G,GS,KO,KOYrds,KOTD,PtRet,PtYrd,PtTD");
                for (int i = 0; i < year; i++) {
                    if (getXKORets(i) > 0 || getXPuntRets(i) > 0)
                        pStats.add(getXSeason(i) + "," + getXGames(i) + "," + getXGamesStarted(i) + "," + getXKORets(i) + "," + getXKOYards(i) + "," + getXKOTDs(i) + "," + getXPuntRets(i) + "," + getXPuntYards(i) + "," + getXPuntTDs(i));
                }
                pStats.add(getSeason() + "," + getGames() + "," + getGamesStarted() + "," + getKORets() + "," + getKOYards() + "," + getKOTDs() + "," + getPuntRets() + "," + getPuntYards() + "," + getPuntTDs());
                pStats.add(" , , , , , , , , ");
                pStats.add("Career," + getCareerGames() + "," + getCareerGamesStarted() + "," + getCareerKORets() + "," + getCareerKOYards() + "," + getCareerKOTDs() + "," + getCareerPuntRets() + "," + getCareerPuntYards() + "," + getCareerPuntTDs());
                pStats.add(" , , , , , , , , ");
            }


        } else if (Arrays.asList(olPos).contains(position)) {
            if (getCareerRunSnaps() > 0 || getCareerPassSnaps() > 0) {
                pStats.add("OL,G,GS,RSnaps,RYPG,PSnaps,PYPG,Sacks, ");
                for (int i = 0; i < year; i++) {
                    if (getXRunSnaps(i) > 0 || getXPassSnaps(i) > 0)
                        pStats.add(getXSeason(i) + "," + getXGames(i) + "," + getXGamesStarted(i) + "," + getXRunSnaps(i) + "," + df2.format(getXOLRYPG(i)) + "," + getXPassSnaps(i) + "," + df2.format(getXOLPYPG(i)) + "," + getXOLSacksAllowed(i) + ", ");
                }
                pStats.add(getSeason() + "," + getGames() + "," + getGamesStarted() + "," + getRunSnaps() + "," + df2.format(getOLYardsPerRush()) + "," + getPassSnaps() + "," + df2.format(getOLYardsPerPass()) + "," + getOLSacksAllowed() + ", ");
                pStats.add("Career," + getCareerGames() + "," + getCareerGamesStarted() + "," + getCareerRunSnaps() + "," + df2.format(getCareerOLYardsPerRush()) + "," + getCareerPassSnaps() + "," + df2.format(getCareerOLYardsPerPass()) + "," + getCareerOLSacksAllowed() + ", ");
                pStats.add(" , , , , , , , , ");
            }


        } else if (Arrays.asList(kickPos).contains(position)) {

            if (getCareerFGAtt() > 0 || getCareerXPAtt() > 0) {
                pStats.add("Kicking,G,GS,XP,Att,XP%,FG,Att,FG%");
                for (int i = 0; i < year; i++) {
                    if (getXRunSnaps(i) > 0 || getXPassSnaps(i) > 0)
                        pStats.add(getXSeason(i) + "," + getXGames(i) + "," + getXGamesStarted(i) + "," + getXXPMade(i) + "," + getXXPAtt(i) + "," + df2.format(getXPATpct(i)) + "," + getXFGMade(i) + "," + getXFGAtt(i) + "," + df2.format(getXFGpct(i)));
                }
                pStats.add(getSeason() + "," + getGames() + "," + getGamesStarted() + "," + getXPMade() + "," + getXPAtt() + "," + df2.format(getPATpct()) + "," + getFGMade() + "," + getFGAtt() + "," + df2.format(getFGpct()));
                pStats.add("Career," + getCareerGames() + "," + getCareerGamesStarted() + "," + getCareerXPMade() + "," + getCareerXPAtt() + "," + df2.format(getCareerXPpct()) + "," + getCareerFGMade() + "," + getCareerFGAtt() + "," + df2.format(getCareerFGpct()));
                pStats.add(" , , , , , , , , ");
            }

        } else {
            //HEAD COACH IN FUTURE
        }

        return pStats;
    }

    public ArrayList<String> getPlayerFeaturedStats() {
        ArrayList<String> pStats = new ArrayList<>();

        if (position.equals("QB")) {
            pStats.add("Pass Yds/Game");
            pStats.add("Season: " + df2.format(getPassYardsPerGame()) + "\nCareer: " + df2.format(getCareerPassYardsPerGame()));

            pStats.add("Comp PCT");
            pStats.add("Season: " + df2.format(getPassPCT()) + "%" + "\nCareer: " + df2.format(getCareerPassPCT()) + "%");

            pStats.add("Yards per Pass");
            pStats.add("Season: " + df2.format(getYardsPerAttempt()) + "\nCareer: " + df2.format(getCareerYardsPerAttempt()));
        }

        if (position.equals("RB")) {
            pStats.add("Rush Yds/Game");
            pStats.add("Season: " + df2.format(getRushYardsPerGame()) + "\nCareer: " + df2.format(getCareerRushYardsPerGame()));

            pStats.add("Yards/Carry");
            pStats.add("Season: " + df2.format(getYardsperCarry()) + "\nCareer: " + df2.format(getCareerYardsperCarry()));

            pStats.add("Yards/Catch");
            pStats.add("Season: " + df2.format(getYPR()) + "\nCareer: " + df2.format(getCareerYPR()));
        }

        if (position.equals("WR") || position.equals("TE")) {
            pStats.add("Yards/Catch");
            pStats.add("Season: " + df2.format(getYPR()) + "\nCareer: " + df2.format(getCareerYPR()));

            pStats.add("Yards/Target");
            pStats.add("Season: " + df2.format(getYardsperTGT()) + "\nCareer: " + df2.format(getCareerYardsperTGT()));

            pStats.add("QB-WR Rating");
            pStats.add("Season: " + df2.format(getWRPassRating()) + "\nCareer: " + df2.format(getWRPassRatingCareer()));
        }

        if (position.equals("OL")) {
            pStats.add("Sacks Allowed");
            pStats.add("Season: " + df2.format(getOLSacksAllowed()) + "\nCareer: " + df2.format(getCareerOLSacksAllowed()));

            pStats.add("Rush Yards/Snap");
            pStats.add("Season: " + df2.format(getOLYardsPerRush()) + "\nCareer: " + df2.format(getCareerOLYardsPerRush()));

            pStats.add("Pass Yards/Snap");
            pStats.add("Season: " + df2.format(getOLYardsPerPass()) + "\nCareer: " + df2.format(getCareerOLYardsPerPass()));
        }

        if (position.equals("K")) {
            pStats.add("FG PCT");
            pStats.add("Season: " + df2.format(getFGpct()) + "\nCareer: " + df2.format(getCareerFGpct()) + "%");

            pStats.add("PAT PCT");
            pStats.add("Season: " + df2.format(getPATpct()) + "\nCareer: " + df2.format(getCareerXPpct()) + "%");

            pStats.add("FG Long");
            pStats.add("Season: N/A");
            pStats.add("Career: N/A");
        }

        if (position.equals("DL") || position.equals("S")) {
            pStats.add("Tackles/Game");
            pStats.add("Season: " + df2.format(getTacklesPerGame()) + "\nCareer: " + df2.format(getCareerTacklesPerGame()));

            pStats.add("Sacks");
            pStats.add("Season: " + getSacks() + "\nCareer: " + getCareerSacks());

            pStats.add("Turnovers");
            pStats.add("Season: " + getTotalTurnovers() + "\nCareer: " + getCareerTotalTurnovers());
        }

        if (position.equals("CB") || position.equals("LB")) {
            pStats.add("Tackles/Game");
            pStats.add("Season: " + df2.format(getTacklesPerGame()) + "\nCareer: " + df2.format(getCareerTacklesPerGame()) + "%");

            pStats.add("Shutdown PCT");
            pStats.add("Season: " + df2.format(getShutdownPCT()) + "\nCareer: " + df2.format(getCareerShutdownPCT()) + "%");

            pStats.add("Turnovers");
            pStats.add("Season: " + getTotalTurnovers() + "\nCareer: " + getCareerTotalTurnovers());
        }


        //AWARDS
        pStats.add("Awards");
        StringBuilder sb = new StringBuilder();
        if (getHeismans() > 0) sb.append(getHeismans() + " POTY\n");
        if (getAllAmericans() > 0) sb.append(getAllAmericans() + " All-Amer\n");
        if (getAllConference() > 0) sb.append(getAllConference() + " All-Conf\n");
        if (getTopFreshman() > 0) sb.append(getAllAmericans() + " Top Fresh\n");
        if (getAllFreshman() > 0) sb.append(getAllAmericans() + " All-Fresh\n");
        if (sb.toString().length() == 0) sb.append("none");
        pStats.add(sb.toString());

        return pStats;
    }




    ///////////////////////////////////////////////////////////////////

    //PLAYER STATISTICS

    //Season Played
    public int getSeason() {
        return stats[0];
    }

    public void recordSeason() {
        stats[0] = team.league.getYear();
    }

    public String getSeasonsPlayed() {
        return careerStats.get(0)[0] + " to " + careerStats.get(careerStats.size())[0];
    }


    public int getXSeason(int i) {
        return careerStats.get(i)[0];
    }


    //Games Played
    public int getGames() {
        return stats[1];
    }

    public void recordGame(int x) {
        stats[1] = stats[1] + x;
    }

    public int getCareerGames() {
        int x = stats[1];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[1];
        }

        return x;
    }

    public int getXGames(int i) {
        return careerStats.get(i)[1];
    }


    //Games Started
    public int getGamesStarted() {
        return stats[2];
    }

    public void recordGameStarted(int x) {
        stats[2] = stats[2] + x;
    }

    public int getCareerGamesStarted() {
        int x = stats[2];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[2];
        }

        return x;
    }

    public int getXGamesStarted(int i) {
        return careerStats.get(i)[2];
    }

    //SPECIAL TEAMS

    //Kickoff Att
    public int getKORets() {
        return stats[3];
    }

    public void recordKORets(int x) {
        stats[3] = stats[3] + x;
    }

    public int getCareerKORets() {
        int x = stats[3];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[3];
        }

        return x;
    }

    public int getXKORets(int i) {
        return careerStats.get(i)[3];
    }

    //Kickoff yards
    public int getKOYards() {
        return stats[4];
    }

    public void recordKOYards(int x) {
        stats[4] = stats[4] + x;
    }

    public int getCareerKOYards() {
        int x = stats[4];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[4];
        }

        return x;
    }

    public int getXKOYards(int i) {
        return careerStats.get(i)[4];
    }

    //Kickoff TD
    public int getKOTDs() {
        return stats[5];
    }

    public void recordKOTDs(int x) {
        stats[5] = stats[5] + x;
    }

    public int getCareerKOTDs() {
        int x = stats[5];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[5];
        }

        return x;
    }

    public int getXKOTDs(int i) {
        return careerStats.get(i)[5];
    }

    //Punt Rets
    public int getPuntRets() {
        return stats[6];
    }

    public void recordPuntRets(int x) {
        stats[6] = stats[6] + x;
    }

    public int getCareerPuntRets() {
        int x = stats[6];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[6];
        }

        return x;
    }

    public int getXPuntRets(int i) {
        return careerStats.get(i)[6];
    }

    //Punt Yards
    public int getPuntYards() {
        return stats[7];
    }

    public void recordPuntYards(int x) {
        stats[7] = stats[7] + x;
    }

    public int getCareerPuntYards() {
        int x = stats[7];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[7];
        }

        return x;
    }

    public int getXPuntYards(int i) {
        return careerStats.get(i)[7];
    }

    //Punt TD
    public int getPuntTDs() {
        return stats[8];
    }

    public void recordPuntTDs(int x) {
        stats[8] = stats[8] + x;
    }

    public int getCareerPuntTDs() {
        int x = stats[8];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[8];
        }

        return x;
    }

    public int getXPuntTDs(int i) {
        return careerStats.get(i)[8];
    }

    //OFFENSE


    //Pass Attempts
    public int getPassAtt() {
        return stats[9];
    }

    public void recordPassAtt(int x) {
        stats[9] = stats[9] + x;
    }

    public int getCareerPassAtt() {
        int x = stats[9];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[9];
        }

        return x;
    }

    public int getXPassAtt(int i) {
        return careerStats.get(i)[9];
    }

    //Pass Completions
    public int getPassComp() {
        return stats[10];
    }

    public void recordPassComp(int x) {
        stats[10] = stats[10] + x;
    }

    public int getCareerPassComp() {
        int x = stats[10];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[10];
        }

        return x;
    }

    public int getXPassComp(int i) {
        return careerStats.get(i)[10];
    }

    //Pass Touchdowns
    public int getPassTD() {
        return stats[11];
    }

    public void recordPassTD(int x) {
        stats[11] = stats[11] + x;
    }

    public int getCareerPassTD() {
        int x = stats[11];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[11];
        }

        return x;
    }

    public int getXPassTD(int i) {
        return careerStats.get(i)[11];
    }

    //Pass Int
    public int getPassInt() {
        return stats[12];
    }

    public void recordPassInt(int x) {
        stats[12] = stats[12] + x;
    }

    public int getCareerPassInt() {
        int x = stats[12];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[12];
        }

        return x;
    }

    public int getXPassInt(int i) {
        return careerStats.get(i)[12];
    }

    //Pass Yards
    public int getPassYards() {
        return stats[13];
    }

    public void recordPassYards(int x) {
        stats[13] = stats[13] + x;
    }

    public int getCareerPassYards() {
        int x = stats[13];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[13];
        }

        return x;
    }

    public int getXPassYards(int i) {
        return careerStats.get(i)[13];
    }

    //QB Sacked
    public int getSacked() {
        return stats[14];
    }

    public void recordSacked(int x) {
        stats[14] = stats[14] + x;
    }

    public int getCareerSacked() {
        int x = stats[14];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[14];
        }

        return x;
    }

    public int getXSacked(int i) {
        return careerStats.get(i)[14];
    }

    //Rush Attempts
    public int getRushAtt() {
        return stats[15];
    }

    public void recordRushAtt(int x) {
        stats[15] = stats[15] + x;
    }

    public int getCareerRushAtt() {
        int x = stats[15];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[15];
        }

        return x;
    }

    public int getXRushAtt(int i) {
        return careerStats.get(i)[15];
    }

    //Rush Yards
    public int getRushYards() {
        return stats[16];
    }

    public void recordRushYards(int x) {
        stats[16] = stats[16] + x;
    }

    public int getCareerRushYards() {
        int x = stats[16];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[16];
        }

        return x;
    }

    public int getXRushYards(int i) {
        return careerStats.get(i)[16];
    }

    //Rush TDs
    public int getRushTDs() {
        return stats[17];
    }

    public void recordRushTDs(int x) {
        stats[17] = stats[17] + x;
    }

    public int getCareerRushTDs() {
        int x = stats[17];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[17];
        }

        return x;
    }

    public int getXRushTDs(int i) {
        return careerStats.get(i)[17];
    }

    //Fumbled
    public int getFumbles() {
        return stats[18];
    }

    public void recordFumbles(int x) {
        stats[18] = stats[18] + x;
    }

    public int getCareerFumbles() {
        int x = stats[18];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[18];
        }
        return x;
    }

    public int getXFumbles(int i) {
        return careerStats.get(i)[18];
    }

    //Targets
    public int getTargets() {
        return stats[19];
    }

    public void recordTargets(int x) {
        stats[19] = stats[19] + x;
    }

    public int getCareerTargets() {
        int x = stats[19];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[19];
        }

        return x;
    }

    public int getXTargets(int i) {
        return careerStats.get(i)[19];
    }

    //Receptions
    public int getReceptions() {
        return stats[20];
    }

    public void recordReceptions(int x) {
        stats[20] = stats[20] + x;
    }

    public int getCareerReceptions() {
        int x = stats[20];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[20];
        }

        return x;
    }

    public int getXReceptions(int i) {
        return careerStats.get(i)[20];
    }

    //Recieving Yards
    public int getRecYards() {
        return stats[21];
    }

    public void recordRecYards(int x) {
        stats[21] = stats[21] + x;
    }

    public int getCareerRecYards() {
        int x = stats[21];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[21];
        }

        return x;
    }

    public int getXRecYards(int i) {
        return careerStats.get(i)[21];
    }

    //Rec TDs
    public int getRecTDs() {
        return stats[22];
    }

    public void recordRecTDs(int x) {
        stats[22] = stats[22] + x;
    }

    public int getCareerRecTDs() {
        int x = stats[22];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[22];
        }

        return x;
    }

    public int getXRecTDs(int i) {
        return careerStats.get(i)[22];
    }

    //QB Drops
    public int getDrops() {
        return stats[23];
    }

    public void recordDrops(int x) {
        stats[23] = stats[23] + x;
    }

    public int getCareerDrops() {
        int x = stats[23];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[23];
        }

        return x;
    }

    public int getXDrops(int i) {
        return careerStats.get(i)[23];
    }


    //DEFENSE


    //Tackles
    public int getTackles() {
        return stats[9];
    }

    public void recordTackles(int x) {
        stats[9] = stats[9] + x;
    }

    public int getCareerTackles() {
        int x = stats[9];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[9];
        }

        return x;
    }

    public int getXTackles(int i) {
        return careerStats.get(i)[9];
    }

    //Sacks
    public int getSacks() {
        return stats[10];
    }

    public void recordSacks(int x) {
        stats[10] = stats[10] + x;
    }

    public int getCareerSacks() {
        int x = stats[10];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[10];
        }

        return x;
    }

    public int getXSacks(int i) {
        return careerStats.get(i)[10];
    }

    //FumbleRec
    public int getFumblesRec() {
        return stats[11];
    }

    public void recordFumblesRec(int x) {
        stats[11] = stats[11] + x;
    }

    public int getCareerFumblesRec() {
        int x = stats[11];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[11];
        }

        return x;
    }

    public int getXFumblesRec(int i) {
        return careerStats.get(i)[11];
    }

    //Interceptions
    public int getInterceptions() {
        return stats[12];
    }

    public void recordInterceptions(int x) {
        stats[12] = stats[12] + x;
    }

    public int getCareerInterceptions() {
        int x = stats[12];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[12];
        }

        return x;
    }

    public int getXInterceptions(int i) {
        return careerStats.get(i)[12];
    }

    //Targeted
    public int getTargeted() {
        return stats[13];
    }

    public void recordTargeted(int x) {
        stats[13] = stats[13] + x;
    }

    public int getCareerTargeted() {
        int x = stats[13];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[13];
        }

        return x;
    }

    public int getXTargeted(int i) {
        return careerStats.get(i)[13];
    }

    //DefIncomplete
    public int getDefIncompleted() {
        return stats[14];
    }

    public void recordDefIncompleted(int x) {
        stats[14] = stats[14] + x;
    }

    public int getCareerDefIncompleted() {
        int x = stats[14];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[14];
        }

        return x;
    }

    public int getXDefIncompleted(int i) {
        return careerStats.get(i)[14];
    }

    //Defended
    public int getDefended() {
        return stats[15];
    }

    public void recordDefended(int x) {
        stats[15] = stats[15] + x;
    }

    public int getCareerDefended() {
        int x = stats[15];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[15];
        }

        return x;
    }

    public int getXDefended(int i) {
        return careerStats.get(i)[15];
    }


    //KICKING

    //XPAttempt
    public int getXPAtt() {
        return stats[3];
    }

    public void recordXPAtt(int x) {
        stats[3] = stats[3] + x;
    }

    public int getCareerXPAtt() {
        int x = stats[3];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[3];
        }

        return x;
    }

    public int getXXPAtt(int i) {
        return careerStats.get(i)[3];
    }


    //XPMade
    public int getXPMade() {
        return stats[4];
    }

    public void recordXPMade(int x) {
        stats[4] = stats[4] + x;
    }

    public int getCareerXPMade() {
        int x = stats[4];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[4];
        }

        return x;
    }

    public int getXXPMade(int i) {
        return careerStats.get(i)[4];
    }

    //FGAttempt
    public int getFGAtt() {
        return stats[5];
    }

    public void recordFGAtt(int x) {
        stats[5] = stats[5] + x;
    }

    public int getCareerFGAtt() {
        int x = stats[5];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[5];
        }

        return x;
    }

    public int getXFGAtt(int i) {
        return careerStats.get(i)[5];
    }

    //FGMade
    public int getFGMade() {
        return stats[6];
    }

    public void recordFGMade(int x) {
        stats[6] = stats[6] + x;
    }

    public int getCareerFGMade() {
        int x = stats[6];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[6];
        }

        return x;
    }

    public int getXFGMade(int i) {
        return careerStats.get(i)[6];
    }

    //Offensive Line

    //Run Snaps
    public int getRunSnaps() {
        return stats[3];
    }

    public void recordRunSnaps(int x) {
        stats[3] = stats[3] + x;
    }

    public int getCareerRunSnaps() {
        int x = stats[3];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[3];
        }

        return x;
    }

    public int getXRunSnaps(int i) {
        return careerStats.get(i)[3];
    }


    //PassSnaps
    public int getPassSnaps() {
        return stats[4];
    }

    public void recordPassSnaps(int x) {
        stats[4] = stats[4] + x;
    }

    public int getCareerPassSnaps() {
        int x = stats[4];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[4];
        }

        return x;
    }

    public int getXPassSnaps(int i) {
        return careerStats.get(i)[4];
    }

    //OLRunYards
    public int getOLRunYards() {
        return stats[5];
    }

    public void recordOLRunYards(int x) {
        stats[5] = stats[5] + x;
    }

    public int getCareerOLRunYards() {
        int x = stats[5];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[5];
        }

        return x;
    }

    public int getXOLRunYards(int i) {
        return careerStats.get(i)[5];
    }

    //OLPassYards
    public int getOLPassYards() {
        return stats[6];
    }

    public void recordOLPassYards(int x) {
        stats[6] = stats[6] + x;
    }

    public int getCareerOLPassYards() {
        int x = stats[6];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[6];
        }

        return x;
    }

    public int getXOLPassYards(int i) {
        return careerStats.get(i)[6];
    }


    //OLSacksAllowed
    public int getOLSacksAllowed() {
        return stats[7];
    }

    public void recordOLSacksAllowed(int x) {
        stats[7] = stats[7] + x;
    }

    public int getCareerOLSacksAllowed() {
        int x = stats[7];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[7];
        }

        return x;
    }

    public int getXOLSacksAllowed(int i) {
        return careerStats.get(i)[7];
    }

    //Pancakes
    public int getPancakes() {
        return stats[8];
    }

    public void recordPancakes(int x) {
        stats[8] = stats[8] + x;
    }

    public int getCareerPancakes() {
        int x = stats[8];

        for (int i = 0; i < careerStats.size(); i++) {
            x += careerStats.get(i)[8];
        }

        return x;
    }

    public int getXPancakes(int i) {
        return careerStats.get(i)[8];
    }


    ///////////////////////////////////////////////////////////
    ///AVERAGE STATS

    //PASSING
    public float getPasserRating() {
        if (getPassAtt() < 1) {
            return 0;
        } else {
            float rating = (float) (((8.4 * getPassYards()) + (300 * getPassTD()) + (100 * getPassComp()) - (200 * getPassInt())) / getPassAtt());
            return rating;
        }
    }

    public float getPassPCT() {
        if (getPassAtt() < 1) {
            return 0;
        } else {
            float rating = (float) 100 * getPassComp() / (getPassAtt());
            return rating;
        }
    }

    public float getCareerPassPCT() {
        if (getCareerPassAtt() < 1) {
            return 0;
        } else {
            float rating = (float) 100 * getCareerPassComp() / getCareerPassAtt();
            return rating;
        }
    }

    public float getCareerPasserRating() {
        if (getPassAtt() < 1) {
            return 0;
        } else {
            float rating = (float) (((8.4 * getCareerPassYards()) + (300 * (getCareerPassTD())) + (100 * (getCareerPassComp())) - (200 * (getCareerPassInt()))) / (getCareerPassAtt()));
            return rating;
        }
    }

    public float getYardsPerAttempt() {
        if (getPassAtt() < 1) {
            return 0;
        } else {
            float ypa = (float) getPassYards() / getPassAtt();
            return ypa;
        }
    }

    public float getCareerYardsPerAttempt() {
        if (getPassAtt() + getCareerPassAtt() < 1) {
            return 0;
        } else {
            float ypa = (float) (getPassYards() + getCareerPassYards()) / (getPassAtt() + getCareerPassAtt());
            return ypa;
        }
    }

    public float getPassYardsPerGame() {
        if (getGames() < 1) {
            return 0;
        } else {
            float ypg = (float) getPassYards() / getGames();
            return ypg;
        }
    }


    public float getCareerPassYardsPerGame() {
        if (getCareerGames() < 1) {
            return 0;
        } else {
            float ypg = (float) getCareerPassYards() / getCareerGames();
            return ypg;
        }
    }

    public float getXPassCompPCT(int i) {
        if (getXPassAtt(i) < 1) {
            return 0;
        } else {
            float rating = (float) 100 * getXPassComp(i) / getXPassAtt(i);
            return rating;
        }
    }

    public float getXQBR(int i) {
        if (getXPassAtt(i) < 1) {
            return 0;
        } else {
            float rating = (float) (((8.4 * getXPassYards(i)) + (300 * getXPassTD(i)) + (100 * getXPassComp(i)) - (200 * getXPassInt(i))) / getXPassAtt(i));
            return rating;
        }
    }


    //RUSHING

    public float getYardsperCarry() {
        if (getRushAtt() < 1) {
            return 0;
        } else {
            float rating = (float) getRushYards() / getRushAtt();
            return rating;
        }
    }

    public float getCareerYardsperCarry() {
        if (getCareerRushAtt() < 1) {
            return 0;
        } else {
            float rating = (float) getCareerRushYards() / getCareerRushAtt();
            return rating;
        }
    }

    public float getXYardsperCarry(int i) {
        if (getXRushAtt(i) < 1) {
            return 0;
        } else {
            float rating = (float) getXRushYards(i) / getXRushAtt(i);
            return rating;
        }
    }

    public float getRushYardsPerGame() {
        if (getGames() < 1) {
            return 0;
        } else {
            float rating = (float) getRushYards() / getGames();
            return rating;
        }
    }

    public float getCareerRushYardsPerGame() {
        if (getGames() < 1) {
            return 0;
        } else {
            float rating = (float) getCareerRushYards() / getCareerGames();
            return rating;
        }
    }

    public float getXYardsperGame(int i) {
        if (getXRushAtt(i) < 1) {
            return 0;
        } else {
            float rating = (float) getXRushYards(i) / getXGames(i);
            return rating;
        }
    }

    //Receiving

    public float getYardsperTGT() {
        if (getReceptions() < 1) {
            return 0;
        } else {
            float rating = (float) getRecYards() / getTargets();
            return rating;
        }
    }

    public float getCareerYardsperTGT() {
        if (getCareerReceptions() < 1) {
            return 0;
        } else {
            float rating = (float) getCareerRecYards() / getCareerTargets();
            return rating;
        }
    }

    public float getYPR() {
        if (getReceptions() < 1) {
            return 0;
        } else {
            float rating = (float) getRecYards() / getReceptions();
            return rating;
        }
    }

    public float getCareerYPR() {
        if (getCareerReceptions() < 1) {
            return 0;
        } else {
            float rating = (float) getCareerRecYards() / getCareerReceptions();
            return rating;
        }
    }

    public float getXYPR(int i) {
        if (getXReceptions(i) < 1) {
            return 0;
        } else {
            float rating = (float) getXRecYards(i) / getXReceptions(i);
            return rating;
        }
    }

    public float getCatchPCT() {
        if (getTargets() < 1) {
            return 0;
        } else {
            float rating = (float) 100 * getReceptions() / (getTargets());
            return rating;
        }
    }

    public float getCareerCatchPCT() {
        if (getCareerTargets() < 1) {
            return 0;
        } else {
            float rating = (float) 100 * getCareerReceptions() / getCareerTargets();
            return rating;
        }
    }

    public float getXCatchPCT(int i) {
        if (getXTargets(i) < 1) {
            return 0;
        } else {
            float rating = (float) 100 * getXReceptions(i) / getXTargets(i);
            return rating;
        }
    }

    public float getWRPassRating() {
        if (getTargets() < 1) {
            return 0;
        } else {
            float rating = (float) (((8.4 * getRecYards()) + (300 * getRecTDs()) + (100 * getReceptions()) - (200 * getDrops())) / getTargets());
            return rating;
        }
    }

    public float getWRPassRatingCareer() {
        if (getTargets() + getCareerTargets() < 1) {
            return 0;
        } else {
            float rating = (float) (((8.4 * getCareerRecYards()) + (300 * getCareerRecTDs()) + (100 * getCareerReceptions()) - (200 * getCareerDrops())) / getCareerTargets());
            return rating;
        }
    }

    public float getRecYardsPerGame() {
        if (getGames() < 1) {
            return 0;
        } else {
            return (float) getRecYards() / getGames();
        }
    }

    public float getCareerRecYardsPerGame() {
        if (getCareerGames() < 1) {
            return 0;
        } else {
            return (float) getCareerRecYards() / getCareerGames();
        }
    }

    //OL PLAY

    public float getOLYardsPerRush() {
        if (getRunSnaps() < 1) {
            return 0;
        } else {
            float ypa = (float) getOLRunYards() / getRunSnaps();
            return ypa;
        }
    }

    public float getOLYardsPerPass() {
        if (getPassSnaps() < 1) {
            return 0;
        } else {
            float ypa = (float) getOLPassYards() / getPassSnaps();
            return ypa;
        }
    }

    public float getCareerOLYardsPerRush() {
        if (getCareerRunSnaps() < 1) {
            return 0;
        } else {
            float ypa = (float) getCareerOLRunYards() / getCareerRunSnaps();
            return ypa;
        }
    }

    public float getCareerOLYardsPerPass() {
        if (getCareerPassSnaps() < 1) {
            return 0;
        } else {
            float ypa = (float) getCareerOLPassYards() / getCareerPassSnaps();
            return ypa;
        }
    }


    public float getXOLRYPG(int i) {
        if (getXRunSnaps(i) < 1) return 0;

        else return (float) getXOLRunYards(i) / getXRunSnaps(i);
    }

    public float getXOLPYPG(int i) {
        if (getXPassSnaps(i) < 1) return 0;

        else return (float) getXOLPassYards(i) / getXPassSnaps(i);
    }

    //DEFENDING

    public float getTacklesPerGame() {
        if (getGames() < 1) return 0;
        else return (float) getTackles() / getGames();
    }

    public float getCareerTacklesPerGame() {
        if (getCareerGames() < 1) return 0;
        else return (float) getCareerTackles() / getCareerGames();
    }

    public int getTotalTurnovers() {
        return getFumblesRec() + getInterceptions();
    }

    public int getCareerTotalTurnovers() {
        return getCareerFumblesRec() + getCareerInterceptions();
    }

    public float getShutdownPCT() {
        if (getTargeted() < 1) return 0;
        else return (float) 100 * getDefIncompleted() / getTargeted();
    }

    public float getCareerShutdownPCT() {
        if (getCareerTargeted() < 1) return 0;
        else return (float) 100 * getCareerDefIncompleted() / getCareerTargeted();
    }


    //KICKING

    public float getFGpct() {
        if (getFGAtt() < 1) {
            return 0;
        } else {

            float rating = 100 * (float) getFGMade() / (getFGAtt());
            return rating;
        }
    }

    public float getPATpct() {
        if (getXPAtt() < 1) {
            return 0;
        } else {

            float rating = 100 * (float) getXPMade() / (getXPAtt());
            return rating;
        }
    }

    public float getCareerFGpct() {
        if (getCareerFGAtt() < 1) {
            return 0;
        } else {

            float rating = (float) 100 * getCareerFGMade() / getCareerFGAtt();
            return rating;
        }
    }

    public float getCareerXPpct() {
        if (getCareerXPAtt() < 1) {
            return 0;
        } else {

            float rating = 100 * (float) getCareerXPMade() / (getCareerXPAtt());
            return rating;
        }
    }

    public float getXPATpct(int i) {
        if (getXXPAtt(i) < 1) {
            return 0;
        } else {

            float rating = 100 * (float) getXXPMade(i) / (getXXPAtt(i));
            return rating;
        }
    }

    public float getXFGpct(int i) {
        if (getXFGAtt(i) < 1) {
            return 0;
        } else {

            float rating = 100 * (float) getXFGMade(i) / (getXFGAtt(i));
            return rating;
        }
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

    //RESET DATA FOR GAME SIMULATIONS

    public void resetGameSimData() {
        gameSnaps = 0;
        gameFatigue = 100;
        gameSim = 0;
        posDepth = 0;
        gamePassAtempts = 0;
        gamePassComplete = 0;
        gamePassYards = 0;
        gamePassTDs = 0;
        gamePassInts = 0;
        gameRushAttempts = 0;
        gameRushYards = 0;
        gameRushTDs = 0;
        gameTargets = 0;
        gameReceptions = 0;
        gameRecYards = 0;
        gameRecTDs = 0;
        gameDrops = 0;
        gameFumbles = 0;
        gameTackles = 0;
        gameSacks = 0;
        gameInterceptions = 0;
        gameDefended = 0;
        gameIncomplete = 0;
        gameFGAttempts = 0;
        gameFGMade = 0;
        gameXPAttempts = 0;
        gameXPMade = 0;
    }

}
