package web.services;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import web.main.TimeHelper;
import web.messageSystem.Abonent;
import web.messageSystem.Address;
import web.messageSystem.MessageSystem;
import web.messageSystem.MsgGetUserId;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Frontend extends AbstractHandler implements Abonent, Runnable {
    private static String APP_NAME = "/";
    private Address address;
    private MessageSystem messageSystem;

    private Map<String, Integer> nameToId = new HashMap<String, Integer>();

    public Frontend(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        this.address = new Address();
        messageSystem.addService(this);
    }

    public void run() {
        while (true) {
            messageSystem.execForAbonent(this);
            TimeHelper.sleep(10);
        }
    }

    public void handle(String target, Request baseRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        httpServletResponse.setContentType("text/html;charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        if (!target.equals(APP_NAME))
            return;
        String name = "Slava";
        Integer id = nameToId.get(name);
        if (id != null) {
            httpServletResponse.getWriter().println("<h1>"+"User name: "+name+" Id: "+id+"</h1>");
        } else {
            httpServletResponse.getWriter().println("<h1>"+"Wait for authorization"+"</h1>");
            Address addressAS = messageSystem.getAddressService().getAddressMap(AccountService.class);
            messageSystem.sendMessage(new MsgGetUserId(getAddress(), addressAS, name));
        }
    }

    public Address getAddress() {
        return address;
    }

    public void setId(String name, Integer id) {
        nameToId.put(name, id);
    }
}
