package web.services;

import web.main.TimeHelper;
import web.messageSystem.Abonent;
import web.messageSystem.Address;
import web.messageSystem.MessageSystem;

import java.util.HashMap;
import java.util.Map;

public class AccountService implements Abonent, Runnable{
    private Address address;
    private MessageSystem ms;

    private Map<String, Integer> fakeAccounter = new HashMap<String, Integer>();

    public AccountService(MessageSystem ms) {
        this.ms = ms;
        this.address = new Address();
        ms.addService(this);
        this.fakeAccounter.put("Slava", 1);
        this.fakeAccounter.put("Ana", 2);
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

    public Integer getUserId(String name) {
        TimeHelper.sleep(50);
        return fakeAccounter.get(name);
    }

    public MessageSystem getMessageSystem() {
        return ms;
    }
}
