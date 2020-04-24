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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Frontend extends AbstractHandler implements Abonent, Runnable {
    private static String APP_NAME = "/";
    private Address address;
    private MessageSystem messageSystem;
    String cashedName = null;

    private Map<String, Integer> nameToId = new HashMap<String, Integer>();
    private Map<String, String> sessionToName = new HashMap<String, String>();

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

        String prefix = "Frontend";
        String sessionId = null;
        HttpSession httpSession = null;
        try {
            httpSession = httpServletRequest.getSession();
            sessionId = httpSession.getId();
            if (httpSession.isNew()) {
                System.out.printf(prefix + " - New Session: %s%n", httpSession.getId());
            } else {
                System.out.printf(prefix + " - Old Session: %s%n", httpSession.getId());
            }
        } catch (IllegalStateException ex) {
            System.out.println(prefix + " - Exception!" + ex);
        }

        if (!target.equals(APP_NAME)) {
            httpServletResponse.getWriter().println("<h1>"+"Target is wrong"+"</h1>");
            return;
        }

        Integer id = null;
        if (cashedName == null) {
            httpServletResponse.getWriter().println(
            "SessionId: " + sessionId +
            "<form method='post' action='/'>"+
                "<input type='text' name='cashedName'>"+
                "<button>Enter Your name</button>"+
            "</form>");
            cashedName = httpServletRequest.getParameter("cashedName");
        } else {
            if (sessionId != null) {
                id = nameToId.get(cashedName);
            }
            sessionToName.put(sessionId, cashedName);
        }
        if (id != null) {
            System.out.println(cashedName);
            httpServletResponse.getWriter().println("<h1>"+"User name: "+sessionToName.get(sessionId)+" Id: "+id+" sessionId: "+sessionId+"</h1>");
        } else {
            if (cashedName != null) {
                httpServletResponse.getWriter().println("<h1>" + "Wait for authorization, sessionId: " + sessionId + "</h1>");
                Address addressAS = messageSystem.getAddressService().getAddressMap(AccountService.class);
                messageSystem.sendMessage(new MsgGetUserId(getAddress(), addressAS, cashedName));
            }
        }
    }

    public Address getAddress() {
        return address;
    }

    public void setId(String name, Integer id) {
        nameToId.put(name, id);
    }
}
