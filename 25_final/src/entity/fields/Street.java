package entity.fields;

import entity.Neighbourhood;


public class Street extends Ownable {
    private int houses = 0;
    private int[] rents = new int[6];
    private Neighbourhood neighbourhood;
    // Contructor til at oprette et Street felt
    public Street(String fieldName, int price, int[] rents, Neighbourhood neighbourhood) {
        super(fieldName, price);
        this.rents = rents;
        this.neighbourhood = neighbourhood;
        neighbourhood.addStreet(this);
    }

    // Getter til neighbourhood
    public Neighbourhood getNeighbourhood() {
        return neighbourhood;
    }

    // Hvad koster det at lande på grunden?
    @Override
    protected int getRent() {
        if (neighbourhood.sameOwner() && houses == 0 ) {
            return rents[houses] * 2;
        } else {
            return rents[houses];
        }
    }

    // Besvarer hvor mange huse en ejendom har
    public int getHouses() {
        return houses;
    }

    // Opgradér en ejendom(byg huse/hotel)
    public void upgrade(){
        owner.getOwner().pay(neighbourhood.getHousePrice());
        houses++;
    }
    //Nedgradér en ejednom(sælg hus/hotel)
    public void downgrade(){
        owner.receive(neighbourhood.getHousePrice()/2);
        houses--;
    }
    // Besvarer om en ejendom kan opgraderes (bebygges med huse/hoteller)
    public boolean isUpgradable(){
        return neighbourhood.sameOwner() && neighbourhood.averageHouses()>=houses && houses < 5 && owner.getOwner().canAfford(neighbourhood.getHousePrice());
    }
    // Besvarer om en ejendom kan nedgraderes
    public boolean isDowngradable() {
        return neighbourhood.sameOwner() && neighbourhood.averageHouses()<=houses && houses > 0;
    }

    // Besvarer om en ejendom kan handles (sælges eller byttes væk)
    @Override
    public boolean isTradable(){
        return neighbourhood.totalHouses()== 0;
    }
    // Procedure for at sælge
    @Override
    public void sell() {
        super.sell();
        houses = 0;
    }
    // Værdien af denne her ejendom
    @Override
    public int value(){
        return getPrice()+houses*(neighbourhood.getHousePrice()/2);
    }
}
