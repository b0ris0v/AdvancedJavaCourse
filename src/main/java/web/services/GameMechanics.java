package web.services;

import web.main.TimeHelper;
import web.messageSystem.*;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class GameMechanics implements Abonent, Runnable {
    private ConcurrentLinkedQueue<Integer> gamers = new ConcurrentLinkedQueue<Integer>();
    static private AtomicInteger gameIdCreator = new AtomicInteger();

    private Address address;
    private MessageSystem ms;

    public GameMechanics(MessageSystem ms) {
        this.ms = ms;
        this.address = new Address();
        ms.addService(this);
    }

    public void run() {
        while (true) {
            ms.execForAbonent(this);
            TimeHelper.sleep(10);
        }
    }

    public Address getAddress() {
        return address;
    }

    public void startGameSessionFor(Integer id) {
        if (gamers.size() > 0) {
            Address addressF = ms.getAddressService().getAddressMap(Frontend.class);
            ms.sendMessage(new MsgMakeNewGameSessionBetween(address, addressF, id, gamers.poll(), gameIdCreator.incrementAndGet()));
        } else
            gamers.add(id);
    }
}
