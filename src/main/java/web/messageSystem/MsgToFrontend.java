package web.messageSystem;

import web.services.FrontendServlet;

public abstract class MsgToFrontend extends Msg {

    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    void exec(Abonent abonent) {
        if (abonent instanceof FrontendServlet) {
            exec((FrontendServlet)abonent);
        }
    }

    abstract void exec(FrontendServlet frontend);
}
