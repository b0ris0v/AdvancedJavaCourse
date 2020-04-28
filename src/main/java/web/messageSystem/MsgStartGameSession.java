package web.messageSystem;

import web.services.AccountService;
import web.services.GameMechanics;

public class MsgStartGameSession extends MsgToGM {
    private Integer id;

    public MsgStartGameSession(Address from, Address to, Integer id) {
        super(from, to);
        this.id = id;
    }

//    void exec(AccountService accountService) {
//        Integer id = accountService.getUserId(name);
//        accountService.getMessageSystem().sendMessage(new MsgUpdateUserId(getTo(), getFrom(), name, id));
//    }

    void exec(GameMechanics gameMechanics) {
        gameMechanics.startGameSessionFor(id);
    }
}
