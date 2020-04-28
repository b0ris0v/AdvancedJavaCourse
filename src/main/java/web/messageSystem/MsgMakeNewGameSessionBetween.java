package web.messageSystem;

import web.services.Frontend;

public class MsgMakeNewGameSessionBetween extends MsgToFrontend {
    Integer firstPlayer;
    Integer secondPlayer;
    Integer gameId;

    public MsgMakeNewGameSessionBetween(Address from, Address to, Integer firstPlayer, Integer secondPlayer, Integer gameId) {
        super(from, to);
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.gameId = gameId;
    }

    void exec(Frontend frontend) {
        frontend.gamePlay(firstPlayer, secondPlayer, gameId);
    }
}
