package me.zahar.task;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
public class Participant extends Thread {
    private Integer participantId;
    private int currentLotPrice;
    private int cash;
    private CyclicBarrier barrier = Auction.barrier;
    public Participant(int id, int currentLotPrice, int cash) {
        this.participantId = id;
        this.currentLotPrice = currentLotPrice;
        this.cash = cash;
    }
    public Integer getBidId() {
        return participantId;
    }
    public int getCurrentLotPrice() {
        return currentLotPrice;
    }
    public int getCash() {
        return cash;
    }
    public void setCash(int cash) {
        this.cash = cash;
    }
    @Override
    public void run() {
        try {
            System.out.println("Participant " + participantId
                    + " specifies a price. (cash = " + cash + ")");
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(2500));
            int delta = new Random().nextInt(20); /* determine the level of
price increase */
            currentLotPrice += delta;
            System.out.println("Auction Participant " + participantId + " : " + currentLotPrice);
            this.barrier.await(); // stop at the barrier
            System.out.println("Participant " + participantId
                    + " Continue to work...(cash = " + cash + ")");
        } catch (BrokenBarrierException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}