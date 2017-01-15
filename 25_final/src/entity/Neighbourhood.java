package entity;

import entity.fields.Street;

import java.util.ArrayList;


public class Neighbourhood {
    private ArrayList<Street> streets = new ArrayList<>();
    private int housePrice;

    public Neighbourhood(int housePrice) {
        this.housePrice = housePrice;
    }

    // Tilføj en vej til nabolaget
    public void addStreet(Street street) {
        streets.add(street);
    }

    // Skal besvare om nabolaget er komplet(om alle felterne har samme ejer)
    public boolean sameOwner() {
        HoldingCompany owner = streets.get(0).getOwner();
        for (Street street : streets) {
            if (owner != street.getOwner()) return false;
        }
        return true;
    }

    // Getter til housePrice
    public int getHousePrice() {
        return housePrice;
    }

    //Returnerer samlet antal huse.
    public int totalHouses() {
        int sum = 0;
        for (Street street : streets) {
            sum += street.getHouses();
        }
        return sum;
    }
    // Beregner hvor mange huse der er i gennemsnit på hver grund i dette nabolag
    public double averageHouses() {
        return ((double) totalHouses() / streets.size());
    }
}
