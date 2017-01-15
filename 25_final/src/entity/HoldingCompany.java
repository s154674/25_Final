package entity;

import entity.fields.Ownable;
import entity.fields.Street;

import java.util.ArrayList;


public class HoldingCompany {
    private Player owner;
    private ArrayList<Ownable> properties = new ArrayList<>();
    public HoldingCompany(Ownable...properties){
        for (Ownable property : properties) addProperty(property);
    }
    public Player getOwner() {
        return owner;
    }
    public void setOwner(Player owner){
        this.owner = owner;
    }
    public ArrayList<Ownable> getProperties() {
        return properties;
    }

    // Tilføjer/fjern en ejendom fra en spiller
    public HoldingCompany addProperty(Ownable property){
        property.setOwner(this);
        properties.add(property);
        return this;
    }
    public HoldingCompany removeProperty(Ownable property){
        properties.remove(property);
        return this;
    }
    // Beregner den samlede værdi af alle ejendomme i holdingselskabet
    public int value(){
        int sum = 0;
        for (Ownable property : properties) sum += property.value();
        return sum;
    }
    // Sælger alt hvad en spiller ejer
    public void sellAll(){
        while (!properties.isEmpty()){
            properties.get(0).sell();
        }
    }
    // Modtager penge og vidergiver dem til ejeren
    public void receive(int amount) {
        owner.receive(amount);
    }

    // Tæller hvor mange huse der er
    public int countHouses(){
        int houses = 0;
        Street aStreet;
        for (Ownable property : properties) {
            if (property instanceof Street) {
                aStreet = ((Street) property);
                if (aStreet.getHouses()!=5)
                    houses+=aStreet.getHouses();
            }
        }
        return houses;
    }
    // Tæller hvor mange hoteller der er
    public int countHotels(){
        int hotels = 0;
        Street aStreet;
        for (Ownable property : properties) {
            if (property instanceof Street) {
                aStreet = ((Street) property);
                if (aStreet.getHouses()==5)
                    hotels++;
            }
        }
        return hotels;
    }
    // Returnerer hvor mange ejendomme af en type der er heri
    public int countPropertiesOfType(Class c){
        int amount = 0;
        for (Ownable property : properties) {
            if (property.getClass().equals(c))
                amount++;
        }
        return amount;
    }
    // returner en liste med alle de ejendomme der kan byttes væk
    public ArrayList<Ownable> tradables(){
        ArrayList<Ownable> tradables = new ArrayList<>();
        for (Ownable property : properties) {
            if (property.isTradable()) tradables.add(property);
        }
        return tradables;
    }

    // Returnerer en liste med alle de ejendomme der kan opgraderes
    public ArrayList<Street> upgradables(){
        ArrayList<Street> upgradables = new ArrayList<>();
        Street aStreet;
        for (Ownable property : properties) {
            if (property instanceof Street){
                aStreet = (Street) property;
                if (aStreet.isUpgradable())
                    upgradables.add(aStreet);
            }
        }
        return upgradables;
    }
    // Returnerer en liste med alle de ejendomme der kan nedgraderes
    public ArrayList<Street> downgradables(){
        ArrayList<Street> downgradables = new ArrayList<>();
        Street aStreet;
        for (Ownable property : properties) {
            if (property instanceof Street){
                aStreet = (Street) property;
                if (aStreet.isDowngradable())
                    downgradables.add(aStreet);
            }
        }
        return downgradables;
    }
}
