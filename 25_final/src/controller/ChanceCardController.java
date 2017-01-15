package controller;

import boundary.GlobalBoundary;
import entity.DeckShuffler;
import entity.Player;


public class ChanceCardController {
    private GlobalBoundary boundary;
    private int numberOfCards = 33;
    private int drawPosition = 0;
    private DeckShuffler shuffler;
    private int[] deck = new int[numberOfCards];
    public ChanceCardController(GlobalBoundary boundary, DeckShuffler shuffler){
        this.boundary = boundary;
        DeckShuffler shuffler1 = shuffler;

        for (int i = 0; i < numberOfCards; i++) deck[i] = i + 1;
        this.shuffler = shuffler;
        deck = shuffler.shuffle(deck);
    }

    // Metoden skal finde effekten for det næste kort i bunken
    public void drawCard(Player player) {
        int nextCard = deck[drawPosition % deck.length];
        String cardText;
        switch (nextCard) {
            // 1-10 modtag penge
            case 1:
                cardText = "Deres præmieobligation er kommet ud. De modtager kr. 1000 af banken.";
                displayCardText(cardText, player);
                player.receive(1000);
                break;
            case 2:
                // duplikation af 1
                cardText = "Deres præmieobligation er kommet ud. De modtager kr. 1000 af banken.";
                displayCardText(cardText, player);
                player.receive(1000);
                break;
            case 3:
                cardText = "Modtag udbytte af Deres aktier kr. 1000.";
                displayCardText(cardText, player);
                player.receive(1000);
                break;
            case 4:
                cardText = "Værdien af egen avl fra nyttehaven udgør kr. 200, som De modtager af banken.";
                displayCardText(cardText, player);
                player.receive(200);
                break;
            case 5:
                cardText = "Modtag udbytte af Deres aktier kr. 1000.";
                displayCardText(cardText, player);
                player.receive(1000);
                break;
            case 6:
                cardText = "De har vundet i kasselotteriet. Modtag kr. 500.";
                displayCardText(cardText, player);
                player.receive(500);
                break;
            case 7:
                cardText = "De modtager Deres aktieudbytte. Modtag kr. 1000 af banken.";
                displayCardText(cardText, player);
                player.receive(1000);
                break;
            case 8:
                cardText = "Grundet dyrtiden har De fået gageforhøjelse. Modtag kr. 1000.";
                displayCardText(cardText, player);
                player.receive(1000);
                break;
            case 9:
                cardText = "De har en række med elleve rigtige i tipning. Modtag kr. 1000.";
                displayCardText(cardText, player);
                player.receive(1000);
                break;
            case 10:
                cardText = "Kommunen har eftergivet et kvartals skat. Hæv i banken kr. 3000.";
                displayCardText(cardText, player);
                player.receive(3000);
                break;
            case 11:
                cardText = "De har solgt deres gamle klude. modtag kr. 3000.";
                displayCardText(cardText, player);
                player.receive(3000);
                break;
            // 12-18 betal penge
            case 12:
                cardText = "Betal kr. 3000. for reperation af Deres vogn.";
                displayCardText(cardText, player);
                player.pay(3000);
                break;
            case 13:
                // Duplikation af 12
                cardText = "Betal kr. 3000. for reperation af Deres vogn.";
                displayCardText(cardText, player);
                player.pay(3000);
                break;
            case 14:
                cardText = "De har kørt frem for \"Fuld Stop\". Betal kr. 1000 i bøde.";
                displayCardText(cardText, player);
                player.pay(1000);
                break;
            case 15:
                cardText = "Betal deres bilforsikring kr. 1000.";
                displayCardText(cardText, player);
                player.pay(1000);
                break;
            case 16:
                cardText = "De har modtaget Deres tandlægeregning. Betal kr. 2000.";
                displayCardText(cardText, player);
                player.pay(2000);
                break;
            case 17:
                cardText = "De har været en tur i udlandet og haft for mange cigaretter med hjem. Betal told kr. 200.";
                displayCardText(cardText, player);
                player.pay(200);
                break;
            case 18:
                cardText = "De har måttet vedtage en parkeringsbøde. Betal kr. 200 i bøde.";
                displayCardText(cardText, player);
                player.pay(200);
                break;
            // 19-27 ryk til felt
            case 19:
                cardText = "Gå i fængsel. Ryk direkte til fængslet. Selv om De passerer \"Start\" indkasserer de ikke kr. 4000";
                displayCardText(cardText, player);
                GameHelper.sendToJail(player);
                break;
            case 20:
                cardText = "Gå i fængsel. Ryk direkte til fængslet. Selv om De passerer \"Start\" indkasserer de ikke kr. 4000";
                displayCardText(cardText, player);
                GameHelper.sendToJail(player);
                break;
            case 21:
                cardText = "Ryk frem til \"Start\".";
                displayCardText(cardText, player);
                GameHelper.move(player,0,true);
                break;
            case 22:
                cardText = "Tag med LB-færgerne - Flyt brikken frem, og hvis de passerer \"Start\", indkassér da kr. 4000";
                displayCardText(cardText, player);
                GameHelper.move(player,5,true);
                break;
            case 23:
                cardText = "Ryk frem til Frederiksberg Allé. Hvis De passerer \"Start\" indkassér kr. 4000";
                displayCardText(cardText, player);
                GameHelper.move(player,11,true);
                break;
            case 24:
                cardText = "Ryk fren til Grønningen. Hvis De passerer \"Start\", indkassér da kr. 4000.";
                displayCardText(cardText, player);
                GameHelper.move(player,24,true);
                break;
            case 25:
                cardText = "Tag ind på Rådhuspladsen";
                displayCardText(cardText, player);
                GameHelper.move(player,39,true);
                break;
            case 26:
                cardText = "Ryk tre felter tilbage.";
                displayCardText(cardText, player);
                GameHelper.backward(player,3);
                break;
            case 27:
                cardText = "Ryk tre felter tilbage.";
                displayCardText(cardText, player);
                GameHelper.backward(player,3);
                break;
            // Specielle kort herfra
            case 28:
                cardText = "I anledning af kongens fødselsdag benådes De herved for fængsel. Dette kort kan opbevares, indtil De får brug for det.";
                displayCardText(cardText, player);
                player.grantPardon();
                break;
            case 29:
                cardText = "I anledning af kongens fødselsdag benådes De herved for fængsel. Dette kort kan opbevares, indtil De får brug for det.";
                displayCardText(cardText, player);
                player.grantPardon();
                break;
            case 30:
                cardText = "Ryk brikken frem til det nærmeste redderi og betal ejeren to gange den leje, han ellers er berretiget til. Hvis selskabet ikke ejes af nogen kan De købe det af banken.";
                displayCardText(cardText, player);
                GameHelper.moveToNearestShipping(player);
                break;
            case 31:
                cardText = "Ryk brikken frem til det nærmeste redderi og betal ejeren to gange den leje, han ellers er berretiget til. Hvis selskabet ikke ejes af nogen kan De købe det af banken.";
                displayCardText(cardText, player);
                GameHelper.moveToNearestShipping(player);
                break;
            case 32:
                cardText = "Det er Deres fødselsdag. Modtag af hver medspiller kr. 200";
                displayCardText(cardText, player);
                for (Player aPlayer : GameState.players) {
                    aPlayer.pay(200, player);
                }
                break;
            case 33:
                cardText = "Ejendomsskatterne er steget, ekstraudgifterne er: kr. 500 pr. hus, kr. 2000 pr. hotel";
                displayCardText(cardText, player);
                for (Player aPlayer : GameState.players)
                    aPlayer.pay(500 * aPlayer.getHoldingCompany().countHouses() + 2000 * aPlayer.getHoldingCompany().countHotels());
                break;
            case 34:
                cardText = "Olieprisserne er steget, og der skal betales: kr. 500 pr. hus, kr. 2000 pr. hotel";
                displayCardText(cardText, player);
                for (Player aPlayer : GameState.players)
                    aPlayer.pay(500 * aPlayer.getHoldingCompany().countHouses() + 2000 * aPlayer.getHoldingCompany().countHotels());
                break;
            case 35:
                cardText = "De modtager \"Matador-legatet for værdig trængede\", stort kr. 40000. Ved værdig trængende forstås at deres formue, d.v.s. Deres kontante penge + skøder + bygninger ikke overstiger kr. 15000";
                displayCardText(cardText, player);
                if (player.fortune()<15000) player.receive(40000);
                break;
        }
        this.drawPosition++;
    }
    // Viser kort med boundary og fortæller spilleren at han har trukket et kort
    private void displayCardText(String cardText, Player player){
        boundary.displayChanceCard(cardText);
        player.decide().landedOnAndDrewChanceCard();
    }
}
