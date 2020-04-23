package web.messageSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageSystem {
    private Map<Address, ConcurrentLinkedQueue<Msg>> messageService = new HashMap<Address, ConcurrentLinkedQueue<Msg>>();
    private AddressService addressService = new AddressService();

    public void addService(Abonent abonent) {
        addressService.setAddressMap(abonent);
        messageService.put(abonent.getAddress(), new ConcurrentLinkedQueue<Msg>());
    }

    public void sendMessage(Msg message) {
        Queue<Msg> msgQueue = messageService.get(message.getTo());
        msgQueue.add(message);
    }

    public void execForAbonent(Abonent abonent) {
        Queue<Msg> msgQueue = messageService.get(abonent.getAddress());
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
