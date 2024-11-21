package me.zahar.task;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CyclicBarrier;
public class Auction {
    private List<Participant> participants;
    public static CyclicBarrier barrier;
    public Auction(int numberParticipant) {
        participants = new ArrayList<>();
        this.barrier = new CyclicBarrier(numberParticipant, () -> Auction.this.defineWinner());
    }
    public boolean add(Participant e) {
        return participants.add(e);
    }

    public Participant defineWinner() {
        // Находим максимальную ставку
        int maxPrice = participants.stream()
                .max(Comparator.comparingInt(Participant::getCurrentLotPrice))
                .get()
                .getCurrentLotPrice();

        // Список участников с максимальными ставками
        List<Participant> maxBidders = participants.stream()
                .filter(p -> p.getCurrentLotPrice() == maxPrice)
                .toList();

        if (maxBidders.size() > 1) {
            System.out.println("Требуется дополнительный аукцион между участниками с максимальными ставками!");
            return conductTieBreaker(maxBidders);
        }

        Participant winner = maxBidders.get(0);
        System.out.println("Participant #" + winner.getBidId() + ", price:"
                + winner.getCurrentLotPrice() + " win!");
        winner.setCash(winner.getCash() - winner.getCurrentLotPrice());
        return winner;
    }

    private Participant conductTieBreaker(List<Participant> maxBidders) {
        CyclicBarrier tieBarrier = new CyclicBarrier(maxBidders.size(), () -> {
            System.out.println("Дополнительный раунд завершён!");
        });

        for (Participant p : maxBidders) {
            p.setBarrier(tieBarrier); // Передаём новую барьерную точку
            new Thread(p).start();
        }

        // Ждём завершения всех потоков
        try {
            for (Thread thread : Thread.getAllStackTraces().keySet()) {
                if (thread.getName().startsWith("Participant")) {
                    thread.join();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Collections.max(maxBidders, Comparator.comparingInt(Participant::getCurrentLotPrice));
    }

}
