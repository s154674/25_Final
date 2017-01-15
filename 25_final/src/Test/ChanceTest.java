package Test;

import boundary.GlobalBoundary;
import boundary.PlayerBoundary;
import controller.PlayerDecisions;


import entity.FisherYatesShuffler;
import entity.HoldingCompany;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import entity.Player;
import entity.Account;
import entity.fields.*;

import controller.ChanceCardController;


public class ChanceTest {
    private Player lander;

    private HoldingCompany landerCompany = new HoldingCompany();

    private Chance chance1;


    ChanceCardController testCardController = new ChanceCardController(new GlobalBoundary(),new FisherYatesShuffler());

    @Test
    public void testEnterties() {
        Assert.assertNotNull(this.lander);

    }
        @Before
        public void setUp () throws Exception {
            lander = new Player("lander", new Account(30000),landerCompany, new PlayerDecisions(new PlayerBoundary()));

            chance1 = new Chance(testCardController);

        }

        @After
        public void tearDown () throws Exception {
            lander = new Player("lander", new Account(30000),landerCompany, new PlayerDecisions(new PlayerBoundary()));
        }

        //
        @Test
        public void testChanceCards(){

            int fieldLand = 33;
            for (int i = 0; i<fieldLand; i++) {
                this.chance1.landOnField(this.lander);
            }

    }


    }

