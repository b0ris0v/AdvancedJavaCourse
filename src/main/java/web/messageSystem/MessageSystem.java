package web.messageSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageSystem {
    private Map<Address, ConcurrentLinkedQueue<Msg>> messages = new HashMap<Address, ConcurrentLinkedQueue<Msg>>();
    private AddressService addressService = new AddressService();

    public void addService(Abonent abonent) {
        addressService.setAddressMap(abonent);
        messages.put(abonent.getAddress(), new ConcurrentLinkedQueue<Msg>());
    }

    public void sendMessage(Msg message) {
        Queue<Msg> msgQueue = messages.get(message.getTo());
        msgQueue.add(message);
    }

    public void execForAbonent(Abonent abonent) {
        Queue<Msg> msgQueue = messages.get(abonent.getAddress());
        if (msgQueue == null)
            return;
        while (!msgQueue.isEmpty()) {
            Msg msg = msgQueue.poll();
            msg.exec(abonent);
        }
    }

    public AddressService getAddressService() {
        return addressService;
    }
}
