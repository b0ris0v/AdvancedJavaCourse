package web.messageSystem;

import web.services.GameMechanics;

public abstract class MsgToGM extends Msg {

    public MsgToGM(Address from, Address to) {
        super(from, to);
    }

    void exec(Abonent abonent) {
        if (abonent instanceof GameMechanics) {
            exec((GameMechanics)abonent);
        }
    }

    abstract void exec(GameMechanics gameMechanics);
}
