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
        Participant winner = Collections.max(participants,
                Comparator.comparingInt(Participant::getCurrentLotPrice));
        System.out.println("Participant #" + winner.getBidId() + ", price:"
                + winner.getCurrentLotPrice() + " win!");
        winner.setCash(winner.getCash() - winner.getCurrentLotPrice());
        return winner;
    }
}
