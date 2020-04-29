package web.messageSystem;

import web.services.GameMechanics;

public class MsgStartGameSession extends MsgToGM {
    private Integer id;

    public MsgStartGameSession(Address from, Address to, Integer id) {
        super(from, to);
        this.id = id;
    }

    void exec(GameMechanics gameMechanics) {
        gameMechanics.startGameSessionFor(id);
    }
}
