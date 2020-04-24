package web.messageSystem;

import web.services.FrontendServlet;

public class MsgUpdateUserId extends MsgToFrontend {
    private String name;
    private Integer id;

    public MsgUpdateUserId(Address from, Address to, String name, Integer id) {
        super(from, to);
        this.name = name;
        this.id = id;
    }

    void exec(FrontendServlet frontend) {
        frontend.setId(name, id);
    }
}
