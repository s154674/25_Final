package Test;

import boundary.PlayerBoundary;

import controller.PlayerDecisions;

import entity.*;
import entity.fields.Ownable;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import entity.fields.Brewery;

import java.util.ArrayList;



public class BreweryTest {
    private Player lander;
    private Player owner;

    private HoldingCompany landerCompany = new HoldingCompany();
    private HoldingCompany ownerCompany = new HoldingCompany();

    private Brewery brewery1;
    private Brewery brewery2;

    private Cup testBrewCup;

    private int testGetRentOneBrewery(){
        testBrewCup.rollAll();
        int facevalue = testBrewCup.getSum();
        return facevalue * 100;
    }

    private int testGetRentTwoBrewery(){
        testBrewCup.rollAll();
        int facevalue = testBrewCup.getSum();
        return facevalue * 100 * 2;
    }


    //
    @Before
    public void setUp() throws Exception {
        brewery1 = new Brewery("Harboe", 4000);
        brewery2 = new Brewery("Turbog", 4000);

        lander = new Player("lander", new Account(30000), landerCompany, new PlayerDecisions(new PlayerBoundary()));
        owner = new Player("owner", new Account(30000), ownerCompany, new PlayerDecisions(new PlayerBoundary()));

    }

    @After
    public void tearDown() throws Exception {
        this.lander = new Player("lander", new Account(30000), new HoldingCompany(), new PlayerDecisions(new PlayerBoundary()));
        this.owner = new Player("owner", new Account(30000), new HoldingCompany(), new PlayerDecisions(new PlayerBoundary()));
    }

    @Test
    //Tester om de bliver oprettet ordentligt og om breweries er en instance af Brewery.
    public void testEnterties() {
        Assert.assertNotNull(this.lander);
        Assert.assertNotNull(this.owner);

        Assert.assertNotNull(this.brewery1);
        Assert.assertNotNull(this.brewery2);

        Assert.assertTrue(this.brewery1 instanceof Brewery);
        Assert.assertTrue(this.brewery2 instanceof Brewery);
    }

    @Test
    //Spilleren lander på et brewer some koster 4000
    public void testBuyField() throws Exception {
        int actual = lander.getAccount().getBalance();
        brewery1.landOnField(this.lander);

        //Hvis spilleren kan købe den så test om dne rigtige mængde penge bliver taget
        if (actual == 26000) {
            this.brewery1.setOwner(this.landerCompany);
            int expected = 30000 - 4000;
            Assert.assertEquals(actual, expected);

            //tjekker om objektet er blevet tilføjet til spillerens properties array.
            boolean inArray = false;
            ArrayList<Ownable> properties = (this.landerCompany.getProperties());
            for (Ownable property: properties) {
                if (this.landerCompany.getProperties() != null && property instanceof Brewery) {
                    inArray = true;
                }
            }
            Assert.assertTrue(inArray);
        }
        //Check om kontoen er den samme som han startede med
        else {
            Assert.assertEquals(30000, actual);
        }
    }
        //Lander på et brewery ejet af en anden spiller
        @Test
        public void landOnOneBrewery(){

        testBrewCup = new Cup(new Dice(),new Dice());

        this.brewery1.setOwner(this.ownerCompany);

        int actual = this.lander.getAccount().getBalance();
        int expected = 30000;

        Assert.assertEquals(actual, expected);

            int expectedArray[] = {29800, 29700, 29600, 29500, 29400, 29300, 29200, 29100, 29000, 28900, 28800};
            actual = this.lander.getAccount().getBalance()-testGetRentOneBrewery();

            boolean exists = false;
            for (int i = 0; i < expectedArray.length; i++) {
                if (expectedArray[i] == actual) {
                    exists = true;
                }
            }
            Assert.assertTrue(exists);
        }


        //Lander på et brewery ejet af en spiller med 2 breweries
        @Test
        public void landOnTwoBrewery(){
            testBrewCup = new Cup(new Dice(),new Dice());

            int actual = this.lander.getAccount().getBalance();
            int expected = 30000;

            Assert.assertEquals(actual, expected);

            int expectedArray[] = {29600, 29400, 29200, 29000, 28800, 28600, 28400, 28200, 28000, 27800, 27600};
            actual = this.lander.getAccount().getBalance()-testGetRentTwoBrewery();

            boolean exists = false;
            for (int i = 0; i < expectedArray.length; i++) {
                if (expectedArray[i] == actual) {
                    exists = true;
                }
            }
            Assert.assertTrue(exists);
        }
    }

