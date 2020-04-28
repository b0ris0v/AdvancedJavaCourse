package web.services;

import org.eclipse.jetty.util.ConcurrentHashSet;
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

    }

    public Address getAddress() {
        return null;
    }

    public void startGameSessionFor(Integer id) {
        //msgToFrontend
        if (gamers.size() > 0)
            ms.sendMessage(new MsgMakeNewGameSessionBetween(address, address, id, gamers.poll(), gameIdCreator.incrementAndGet()));
        else
            gamers.add(id);
    }
}
