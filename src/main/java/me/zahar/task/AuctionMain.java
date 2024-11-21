package me.zahar.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AuctionMain {
    public static void main(String[] args) {
        int startPrice = 50;

        for (int auctionNum = 1; auctionNum <= 3; auctionNum++) { // Три аукциона
            int numberParticipant = 3 + new Random().nextInt(3); // 3–5 участников
            System.out.println("\nАукцион #" + auctionNum + ", число участников: " + numberParticipant);
            Auction auction = new Auction(numberParticipant);

            List<Participant> participants = new ArrayList<>();
            for (int num = 0; num < numberParticipant; num++) {
                int cash = 100 + new Random().nextInt(50);
                Participant participant = new Participant(num, startPrice, cash);
                auction.add(participant);
                participants.add(participant);
                participant.start();
            }

            // Дождаться завершения работы всех участников текущего раунда
            for (Participant participant : participants) {
                try {
                    participant.join(); // Ожидаем завершения потока
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Аукцион #" + auctionNum + " завершён!\n");
        }
    }
}


