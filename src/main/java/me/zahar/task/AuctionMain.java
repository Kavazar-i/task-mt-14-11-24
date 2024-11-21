package me.zahar.task;

import java.util.Random;
public class AuctionMain {
    public static void main(String[] args) {
        int numberParticipant = 5;
        Auction auction = new Auction(numberParticipant);
        int startPrice = 50;
        System.out.println("startPrice =" + startPrice);
        for (int num = 0; num < numberParticipant; num++) {
            int cash = 100 + new Random().nextInt(50);
            Participant participant = new Participant(num, startPrice, cash);
            auction.add(participant);
            participant.start();
        }
    }
}
