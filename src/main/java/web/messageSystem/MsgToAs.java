package web.messageSystem;

import web.services.AccountService;

public abstract class MsgToAs extends Msg {

    public MsgToAs(Address from, Address to) {
        super(from, to);
    }

    void exec(Abonent abonent) {
        if (abonent instanceof AccountService) {
            exec((AccountService) abonent);
        }
    }

    abstract void exec(AccountService accountService);
}
