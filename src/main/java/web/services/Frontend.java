package web.services;

import org.eclipse.jetty.server.Request;
import web.main.TimeHelper;
import web.messageSystem.Abonent;
import web.messageSystem.Address;
import web.messageSystem.MessageSystem;

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

@WebServlet("/web.server.Frontend")
public class Frontend extends HttpServlet implements Abonent, Runnable {
    private static String SERVICE_NAME = "/test/";
    private Address address;
    private MessageSystem ms;

    private Map<String, Integer> nameToId = new HashMap<String, Integer>();

//    private final static Logger LOGGER = Logger.getLogger("global");
    private final static long serialVersionUID = 1L;
//    static int count = 0;

    public Frontend(MessageSystem ms) {
        this.ms = ms;
        this.address = new Address();
        ms.addService(this);
    }

    public void run() {
//        class Logger extends TimerTask {
//            public void run() {
////                LOGGER.log(Level.INFO, String.valueOf(count));
//            }
//        }
//// And From your main() method or any other method
//        Timer timer = new Timer();
//        timer.schedule(new Logger(), 0, 5000);//5 sec
        while(true){
            ms.execForAbonent(this);
            TimeHelper.sleep(10);
        }
    }

    public Address getAddress() {
        return address;
    }

//    private void makeSession(HttpServletRequest request, String prefix) {
//        try {
//            HttpSession httpSession = request.getSession();
//            if (httpSession.isNew()) {
//                System.out.printf(prefix + " - New Session: %s%n", httpSession.getId());
//            } else {
//                System.out.printf(prefix + " - Old Session: %s%n", httpSession.getId());
//            }
//        } catch (IllegalStateException ex) {
//            System.out.println(prefix + " - Exception!" + ex);
//        }
//    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        count++;
//        makeSession(request, "GET");
//        request.getSession().setAttribute("mySecretMessage", count);

        request.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);

    }
}