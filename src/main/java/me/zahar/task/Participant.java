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

    public void setBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            synchronized (System.out) { // Синхронизация вывода
                System.out.println("Participant " + participantId + " specifies a price. (cash = " + cash + ")");
            }

            // Увеличиваем задержку для читаемости
            TimeUnit.SECONDS.sleep(new Random().nextInt(3) + 1);

            int delta = new Random().nextInt(20); // Увеличение ставки
            currentLotPrice += delta;

            synchronized (System.out) { // Синхронизация вывода
                System.out.println("Auction Participant " + participantId + " : " + currentLotPrice);
            }

            this.barrier.await(); // Ожидание других участников

            synchronized (System.out) { // Синхронизация вывода
                System.out.println("Participant " + participantId + " Continue to work...(cash = " + cash + ")");
            }
        } catch (BrokenBarrierException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}