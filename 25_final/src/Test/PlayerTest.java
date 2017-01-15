package Test;

import boundary.PlayerBoundary;

import controller.PlayerDecisions;

import entity.Neighbourhood;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import entity.Account;
import entity.Player;
import entity.fields.Street;
import entity.HoldingCompany;



public class PlayerTest {
    private Player lander;
    private Player owner;

    private HoldingCompany ownerCompany = new HoldingCompany();
    private HoldingCompany landerCompany = new HoldingCompany();
    private Street street1;
    private Street street2;

    @Before
    public void setUp() throws Exception {
        lander = new Player("lander", new Account(30000),landerCompany, new PlayerDecisions(new PlayerBoundary()));
        owner = new Player("owner", new Account(30000), ownerCompany, new PlayerDecisions(new PlayerBoundary()));
    }

    @After
    public void tearDown() throws Exception {
        lander = new Player("lander", new Account(30000), landerCompany, new PlayerDecisions(new PlayerBoundary()));
        owner = new Player("owner", new Account(30000), ownerCompany, new PlayerDecisions(new PlayerBoundary()));
    }

    @Test
    public void testEnterties() {
        Assert.assertNotNull(this.lander);
        Assert.assertNotNull(this.owner);
    }

    @Test
    public void testBalance(){
        //Checking if the player has been initialised with the right amount of money on his acount
        assertEquals(this.lander.getAccount().getBalance(), 30000);
    }

    @Test
    public void testPay(){
        //Checking if the player can pay another player and if the right amount gets moved
        this.lander.pay(5000, this.owner);
        assertEquals(this.owner.getAccount().getBalance(), 35000);
        assertEquals(this.lander.getAccount().getBalance(), 25000);

        //Checking if a player can pay another player a negative amount
        this.lander.pay(-5000, this.owner);
        assertEquals(this.lander.getAccount().getBalance(), 30000);
        assertEquals(this.owner.getAccount().getBalance(), 30000);

    }

    @Test
    public void testJailed(){
        //checker om spilleren er blevet sat i fængsel
        this.lander.setJailed(true);
        assertTrue(this.lander.getJailed());

        //checker at spilleren er den rigtige antal dage i fængsel
        assertEquals(this.lander.getDaysJailed(), 0);

        this.lander.tickDaysJailed();
        assertEquals(this.lander.getDaysJailed(), 1);

        this.lander.tickDaysJailed();
        assertEquals(this.lander.getDaysJailed(), 2);

        this.lander.tickDaysJailed();
        assertEquals(this.lander.getDaysJailed(), 3);

        this.lander.setJailed(false).resetDaysJailed();
        assertFalse(this.lander.getJailed());
        assertEquals(this.lander.getDaysJailed(), 0);
    }

    @Test
    public void testLandOnFieldPay(){
        // Checker at den rigtige mængde penge
        // bliver trukket fra og overført til
        // ejeren når en spiller lander på et
        // normal felt ejet af en anden spiller
        Neighbourhood blue = new Neighbourhood(1000);

        street1 = new Street("Rødovrevej", 60, new int[]{50,250,750,2250,4000,6000}, blue);
        street2 = new Street("Hvidovrevej", 60, new int[]{50,250,750,2250,4000,6000}, blue);

        this.street1.setOwner(this.ownerCompany);
        this.ownerCompany.addProperty(this.street1);

        this.street1.landOnField(this.lander);
        assertEquals(this.lander.getAccount().getBalance(), 29950);
        assertEquals(this.owner.getAccount().getBalance(), 30050);
    }
    @Test
    public void testNeighbourhoodFull(){
        // Checker at en spiller betaler doubelt
        // feltets rente hvis hand lander i en serie
        // af felter alle ejet af den samme person
        Neighbourhood blue = new Neighbourhood(1000);

        street1 = new Street("Rødovrevej", 60, new int[]{50,250,750,2250,4000,6000}, blue);
        street2 = new Street("Hvidovrevej", 60, new int[]{50,250,750,2250,4000,6000}, blue);

        this.street1.setOwner(this.ownerCompany);
        this.street2.setOwner(this.ownerCompany);
        this.ownerCompany.addProperty(this.street2);
        this.ownerCompany.addProperty(this.street1);

        assertTrue(this.street1.getNeighbourhood().sameOwner());
        assertTrue(blue.sameOwner());

        this.street1.landOnField(this.lander);
        assertEquals(this.lander.getAccount().getBalance(), 29900);
        assertEquals(this.owner.getAccount().getBalance(), 30100);

    }

    @Test
    public void testBankrupt(){
    Neighbourhood blue = new Neighbourhood(1000);
    street1 = new Street("Rødovrevej", 60, new int[]{50,250,750,2250,4000,6000}, blue);

    this.street1.setOwner(this.landerCompany);
    this.landerCompany.addProperty(this.street1);

    this.lander.pay(50000);

    assertTrue(this.lander.getBroke());
    assertTrue(this.landerCompany.getProperties().isEmpty());
    }
}