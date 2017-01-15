package demo;

import entity.Cup;

// Snyderaflebæger! Bruges til at opstille en række ønskede terningkast. god til test/demo
public class CheatCup extends Cup {
    int position;
    int[] rollList1, rollList2;
    public CheatCup(int [] rollList1, int [] rollList2){
        this.position = 0;
        this.rollList1 = rollList1;
        this.rollList2 = rollList2;
    }

    public int[] values(){
        return new int[]{rollList1[position], rollList2[position]};
    }
    public void rollAll() {
        position++;
    }
}
