package boundary;

import desktop_codebehind.Car;
import desktop_resources.GUI;
import entity.*;
import entity.fields.*;

import java.awt.*;
import java.util.HashMap;



public class GlobalBoundary {
    // Placer alle spillerne på deres felter
    public void placePlayers(Player [] players,Car[] cars, HashMap<Player, Integer> map){
        for (int i = 0; i < players.length; i++) {
            GUI.addPlayer(players[i].getName(), players[i].getAccount().getBalance(), cars[i]);
            GUI.setCar(map.get(players[i])+1, players[i].getName());
        }
    }

    // Opdater alle kontobalancer i GUI
    public void updateBalances(Player[] players){
        for (int i = 0; i < players.length; i++) updateBalance(players[i]);
    }
    // Opdater en kontobalance i GUI
    private void updateBalance(Player player){
        GUI.setBalance(player.getName(), player.getAccount().getBalance());
    }
    // Opdater alle ejerskab på GUI med de rigtige farver
    public void updateOwnerships(Field[] fields){
        for (int i = 0; i < fields.length; i++)
            if (fields[i] instanceof Ownable) updateOwnership((Ownable) fields[i], i);
    }
    // Opdater et enkelt ejerskab på GUI med de rigtige farver
    private void updateOwnership(Ownable property, int fieldnumber){
        if (property.getOwner()==null){
            GUI.removeOwner(fieldnumber+1);
        } else {
            String ownerName = property.getOwner()!=null ? property.getOwner().getOwner().getName() : "";
            GUI.setOwner(fieldnumber+1, ownerName);
        }
    }

    // Opdater alle felters bygninger i GUI
    public void updateBuildings(Field[] fields){
        for (int i = 0; i < fields.length; i++)
            if (fields[i] instanceof Street) updateBuilding((Street) fields[i], i);
    }
    // Opdater et felts bygninger i GUI
    private void updateBuilding(Street street, int fieldnumber) {
        GUI.setHotel(fieldnumber+1, street.getHouses()==5);
        GUI.setHouses(fieldnumber+1, street.getHouses());
    }
    // Fjerner bankarottede spillere
    public void updatePlayers(Player[] players, HashMap<Player, Integer> map) {
        int fieldNumber;
        for (Player player : players) {
            fieldNumber = map.get(player);
            if (player.getBroke())
                GUI.removeCar(fieldNumber+1, player.getName());
        }
    }
    // Vis et terningkast på GUI
    public void showRoll(Player currentPlayer, Player[] players, Cup cup) {
        int y=10;
        for (Player player : players) {
            y--;
            if (currentPlayer == player){
                GUI.setDice(cup.values()[0],5,y,cup.values()[1],6,y);
            }
        }

    }
    // Vis et prøvlykken kort
    public void displayChanceCard(String cardText) {
        GUI.displayChanceCard(cardText);
    }
    // Flyt en spiller fra et felt til et andet på gui
    public void movePlayer(Player player, int startPos, int endPos){
        GUI.removeCar(startPos+1, player.getName());
        GUI.setCar(endPos+1, player.getName());
    }













    // Spørg gruppen hvor mange spillere de er
    public int howManyPlayers() {
        int amount;
        do {
            amount = GUI.getUserInteger("Hvor mange spillere er i?",3,6);
        } while (3<=amount && amount >= 6);
        return amount;
    }

    // stil spørgsmål til hvordan en spiller vil have sin bil
    public Car getPlayerCar(int id, int players, String name) {
        Color[] colors = new Color[]{Color.RED,Color.YELLOW, Color.MAGENTA, Color.BLUE, Color.DARK_GRAY, Color.BLACK, Color.orange};
        String[] types = new String[]{"Bil", "Ræserbil", "Traktor", "Ufo"};
        String[] patterns = new String[]{"Udfyldt", "Ternet", "Diagonalt tofarvet", "Prikket", "Horisontalt tofarvet", "Horisontal gradient", "Mønstret horisontalt", "Zebra"};

        String type = GUI.getUserSelection("Spiller ("+id+"/"+players+") - Trin 2: Hvordan skal "+name+" komme rundt på brættet?", types);
        String pattern = GUI.getUserSelection("Spiller ("+id+"/"+players+") - Trin 3: Hvilket mønster skal "+name+"'s "+type+" have?", patterns);
        Color primaryColor = selectColor("Spiller ("+id+"/"+players+") - Trin 4: Hvilken primær farve skal "+name+"'s "+type+" have?");
        Color secondaryColor = selectColor("Spiller ("+id+"/"+players+") - Trin 5: Hvilken sekundær farve skal "+name+"'s "+type+" have?");

        return constructCar(type, pattern, primaryColor, secondaryColor);
    }

    private Color selectColor(String text){
        HashMap<String, Color> colorMap = new HashMap<String, Color>();
        String[] colorNames = new String[]{"Rød", "Gul", "Magenta", "Blå","Lyse-grå","Mørke-grå", "Sort", "Orange", "Turkis"};
        Color[] colors = new Color[]{Color.RED,Color.YELLOW, Color.MAGENTA, Color.BLUE, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.BLACK, Color.orange, Color.CYAN};
        for (int i = 0; i < colors.length; i++) {
            colorMap.put(colorNames[i], colors[i]);
        }

        String cName = GUI.getUserSelection(text, colorNames);

        return colorMap.get(cName);
    }

    private Car constructCar(String type, String pattern, Color pc, Color sc) {
        Car.Builder car = new Car.Builder();
        switch (type) {
            case "Bil":
                car.typeCar();
                break;
            case "Ræserbil":
                car.typeRacecar();
                break;
            case "Traktor":
                car.typeTractor();
                break;
            case "Ufo":
                car.typeUfo();
                break;
        }
        switch (pattern) {
            case "Udfyldt":
                car.patternFill();
                break;
            case "Ternet" :
                car.patternCheckered();
                break;
            case "Diagonalt tofarvet":
                car.patternDiagonalDualColor();
                break;
            case "Prikket":
                car.patternDotted();
                break;
            case "Horisontalt tofarvet":
                car.patternHorizontalDualColor();
                break;
            case "Horisontal gradient":
                car.patternHorizontalGradiant();
                break;
            case "Mønstret horisontalt":
                car.patternHorizontalLine();
                break;
            case "Zebra":
                car.patternZebra();
                break;
        }
        return car.primaryColor(pc).secondaryColor(sc).build();
    }
}
