package web.messageSystem;

import java.util.HashMap;
import java.util.Map;

public class AddressService {
    private Map<Class<?>, Address> addressMap = new HashMap<Class<?>, Address>();

    public Address getAddressMap(Class<?> abonentClass) {
        return addressMap.get(abonentClass);
    }

    public void setAddressMap(Abonent abonent) {
        addressMap.put(abonent.getClass(), abonent.getAddress());
    }
}
