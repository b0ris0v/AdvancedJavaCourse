package web.services;

import org.eclipse.jetty.server.Request;
import web.main.TimeHelper;
import web.messageSystem.Abonent;
import web.messageSystem.Address;
import web.messageSystem.MessageSystem;
import web.messageSystem.MsgGetUserId;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/web.server.FrontendServlet")
public class FrontendServlet extends HttpServlet implements Abonent, Runnable {
    private static String SERVICE_NAME = "/test/";
    private Address address;
    private MessageSystem ms;

    private Map<String, Integer> nameToId = new HashMap<String, Integer>();

//    private final static Logger LOGGER = Logger.getLogger("global");
    private final static long serialVersionUID = 1L;
//    static int count = 0;

    public FrontendServlet() {
        super();
    }

    public FrontendServlet(MessageSystem ms) {
        this.ms = ms;
        this.address = new Address();
        ms.addService(this);
    }

    public void run() {
        while(true){
            ms.execForAbonent(this);
            TimeHelper.sleep(10);
        }
    }

    public Address getAddress() {
        return address;
    }

//    private String makeSession(HttpServletRequest request, String prefix) {
//        try {
//            HttpSession httpSession = request.getSession();
//            if (httpSession.isNew()) {
//                System.out.printf(prefix + " - New Session: %s%n", httpSession.getId());
//            } else {
//                System.out.printf(prefix + " - Old Session: %s%n", httpSession.getId());
//            }
//            return httpSession.getId();
//        } catch (IllegalStateException ex) {
//            System.out.println(prefix + " - Exception!" + ex);
//            return null;
//        }
//    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////        count++;
//        String sessionId = makeSession(request, "GET");
////        request.getSession().setAttribute("mySecretMessage", count);
//        if (sessionId != null) {
//            request.getSession().setAttribute("sessionId", sessionId);
//            request.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
//        } else {
//            System.out.println("Session error!");
//        }
        String name = "Slava";
        Integer id = nameToId.get(name);
        if (id != null) {
            request.getSession().setAttribute("name", name);
            request.getSession().setAttribute("id", id);
            request.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            System.out.println("<h1>Wait for authorization</h1>");
            Address addressAS = ms.getAddressService().getAddressMap(AccountService.class);
            ms.sendMessage(new MsgGetUserId(getAddress(), addressAS, name));
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
    }

        public void setId(String name, Integer id) {
        nameToId.put(name, id);
    }

    public void setMessageSystem(MessageSystem ms) {
        this.ms = ms;
    }
}