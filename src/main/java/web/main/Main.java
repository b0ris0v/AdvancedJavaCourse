package web.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;
import web.messageSystem.MessageSystem;
import web.services.AccountService;
import web.services.Frontend;

public class Main {
    public static void main(String[] args) throws Exception {
        MessageSystem ms = new MessageSystem();
        Frontend frontend = new Frontend(ms);
        AccountService accountService = new AccountService(ms);

        (new Thread(frontend)).start();
        (new Thread(accountService)).start();

        Server server = new Server(8080);

        SessionHandler sessionHandler = new SessionHandler();
        sessionHandler.setHandler(frontend);

        server.setHandler(sessionHandler);

        server.start();
        server.join();
    }

}
