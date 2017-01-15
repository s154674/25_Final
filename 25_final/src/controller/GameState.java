package controller;

import boundary.GlobalBoundary;
import boundary.PlayerBoundary;
import desktop_codebehind.Car;
import desktop_resources.GUI;
import entity.*;
import entity.fields.*;

import java.util.HashMap;


public class GameState {
    public static GlobalBoundary            boundary;
    public static Cup                       cup;
    public static DeckShuffler              shuffler;
    public static ChanceCardController      cardController;
    public static Neighbourhood[]           neighbourhoods;
    public static Field[]                   fields;
    public static Player[]                  players;
    public static HashMap<Player, Integer>  map;

    private static GlobalBoundary           customBoundary;
    private static Cup                      customCup;
    private static DeckShuffler             customShuffler;
    private static ChanceCardController     customCardController;
    private static Neighbourhood[]          customNeighbourhoods;
    private static Field[]                  customFields;
    private static Player[]                 customPlayers;
    private static HashMap<Player, Integer> customMap;
    private static Car[]                    customCars;

    public static void useBoundary(GlobalBoundary boundary) { customBoundary = boundary; }
    public static void useCup(Cup cup) {
        customCup = cup;
    }
    public static void useShuffler(DeckShuffler shuffler) {
        customShuffler = shuffler;
    }
    public static void useCardController(ChanceCardController cardController) {
        customCardController = cardController;
    }
    public static void useNeighbourhoods(Neighbourhood[] neighbourhoods) {
        customNeighbourhoods = neighbourhoods;
    }
    public static void useFields(Field[] fields) {
        customFields = fields;
    }
    public static void usePlayers(Player[] players) {
        customPlayers = players;
    }
    public static void useMap(HashMap map) {
        customMap = map;
    }
    public static void useCars(Car[] cars) {
        customCars = cars;
    }

    public static void ready(){
        // Brug default objekter medmindre der er givet nogle custom før start
        boundary       = (customBoundary      !=null) ? customBoundary       : defaultBoundary();
        cup            = (customCup           !=null) ? customCup            : defaultCup();
        shuffler       = (customShuffler      !=null) ? customShuffler       : defaultShuffler();
        cardController = (customCardController!=null) ? customCardController : defaultCardController();
        neighbourhoods = (customNeighbourhoods!=null) ? customNeighbourhoods : defaultNeighbourhoods();
        fields         = (customFields        !=null) ? customFields         : defaultFields();
        // Lad spillerne vælge
        players        = (customPlayers       !=null) ? customPlayers        : getPlayers();
        Car[] cars     = (customCars          !=null) ? customCars           : getCars();

        map            = (customMap           !=null) ? customMap            : makeMap();

        boundary.placePlayers(players, cars, map);
    }

    // Default værdier
    private static GlobalBoundary defaultBoundary() { return new GlobalBoundary();}
    private static Cup defaultCup(){
        return new Cup(new Dice(), new Dice());
    }
    private static DeckShuffler defaultShuffler(){
        return new FisherYatesShuffler();
    }
    private static ChanceCardController defaultCardController(){
        return new ChanceCardController(boundary, shuffler);
    };
    private static Neighbourhood[] defaultNeighbourhoods(){
        return new Neighbourhood[]{
                new Neighbourhood(1000), // Blåt
                new Neighbourhood(1000), // Pink
                new Neighbourhood(2000), // Grøn
                new Neighbourhood(2000), // Grå
                new Neighbourhood(3000), // Rød
                new Neighbourhood(3000), // Hvid
                new Neighbourhood(4000), // Gul
                new Neighbourhood(4000)  // Lilla
        };
    }
    private static Field[] defaultFields(){
        return new Field[]{
                new EmptyField("Start"),
                new Street("Rødovrevej",            1200, new int[]{50,250,750,2250,4000,6000},         neighbourhoods[0]),
                new Chance(cardController),
                new Street("Hvidovrevej",           1200, new int[]{50,250,750,2250,4000,6000},         neighbourhoods[0]),
                new Tax("indkomstskat",             4000, 10),
                new Shipping("Øresund-redderiet",   4000),
                new Street("Roskildevej",           2000, new int[]{100,600,1800,5400,8000,11000},      neighbourhoods[1]),
                new Chance(cardController),
                new Street("Valby langgade",        2000, new int[]{100,600,1800,5400,8000,11000},      neighbourhoods[1]),
                new Street("Allégade",              2400, new int[]{150,800,2000,6000,9000,12000},      neighbourhoods[1]),
                new EmptyField("fængslet"),
                new Street("Frederiksberg allé",    2800, new int[]{200,1000,3000,9000,12500,15000},    neighbourhoods[2]),
                new Brewery("Tuborg-bryggeriet",    3000),
                new Street("Bülowsvej",             2800, new int[]{200,1000,3000,9000,12500,15000},    neighbourhoods[2]),
                new Street("Gammel Kongevej",       3200, new int[]{250,1250,3750,10000,14000,18000},   neighbourhoods[2]),
                new Shipping("D.F.D.S-redderiet",   4000),
                new Street("Bernstorffsvej",        3600, new int[]{300,1400,4000,11000,15000,19000},   neighbourhoods[3]),
                new Chance(cardController),
                new Street("Hellerupvej",           3600, new int[]{300,1400,4000,11000,15000,19000},   neighbourhoods[3]),
                new Street("Strandvejen",           4000, new int[]{350,1600,4400,12000,16000,20000},   neighbourhoods[3]),
                new EmptyField("Helle"),
                new Street("Trianglen",             4400, new int[]{350,1800,5000,14000,17500,21000},   neighbourhoods[4]),
                new Chance(cardController),
                new Street("Østerbrogade",          4400, new int[]{350,1800,5000,14000,17500,21000},   neighbourhoods[4]),
                new Street("Grønningen",            4800, new int[]{400,2000,6000,15000,18500,22000},   neighbourhoods[4]),
                new Shipping("Ø.S-redderiet",       4000),
                new Street("Bredgade",              5200, new int[]{450,2200,6600,16000,19500,23000},   neighbourhoods[5]),
                new Street("Kongens nytorv",        5200, new int[]{450,2200,6600,16000,19500,23000},   neighbourhoods[5]),
                new Brewery("Carlsberg",            3000),
                new Street("Østergade",             5600, new int[]{500,2400,7200,17000,20500,24000},   neighbourhoods[5]),
                new Jailer(),
                new Street("Amagertorv",            6000, new int[]{550,2600,7800,18000,22000,25000},   neighbourhoods[6]),
                new Street("Vimmelskaftet",         6000, new int[]{550,2600,7800,18000,22000,25000},   neighbourhoods[6]),
                new Chance(cardController),
                new Street("Nygade",                6400, new int[]{600,3000,9000,20000,24000,28000},   neighbourhoods[6]),
                new Shipping("Bornoholm-redderiet", 4000),
                new Chance(cardController),
                new Street("Frederiksberggade",     7000, new int[]{700,3500,10000,22000,26000,30000},  neighbourhoods[7]),
                new Tax("ekstraordinær statsskat",  2000),
                new Street("Rådhuspladsen",         8000, new int[]{1000,4000,12000,28000,34000,40000}, neighbourhoods[7])
        };
    }


    // Spørg efter spillernavne
    private static Player[] getPlayers(){
        int amount = boundary.howManyPlayers();

        players = new Player[amount];
        String name;
        PlayerDecisions brain;
        for (int i=0; i<players.length; i++){
            brain = new PlayerDecisions(new PlayerBoundary());
            name = brain.yourName(i+1, amount);
            players[i] = new Player(name, new Account(30000), new HoldingCompany(), brain);
        }
        return players;
    }
    // Spørg efter biltyper
    private static Car[] getCars(){
        Car[] cars = new Car[players.length];
        for (int i = 0; i < players.length; i++) {
            cars[i] = boundary.getPlayerCar(i+1, players.length, players[i].getName());
        }
        return cars;
    }

    // Lav en positionstabel hvor alle spillerne starter på det første felt
    private static HashMap makeMap(){
        HashMap thisMap = new HashMap<Player, Integer>();
        for (Player player : players) {
            thisMap.put(player, 0);
        }
        return thisMap;
    }

}
