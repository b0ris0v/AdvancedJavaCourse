package web.services;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import web.main.TimeHelper;
import web.messageSystem.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Frontend extends AbstractHandler implements Abonent, Runnable {
    private static String APP_NAME = "/";
    private Address address;
    private MessageSystem messageSystem;
    private Map<String, String> cashedNames = new HashMap<String, String>();

    private Map<String, Integer> nameToId = new HashMap<String, Integer>();
    private Map<String, Integer> sessionToId = new HashMap<String, Integer>();
    private boolean gameIsOver = true;
    private Map<Integer, Integer> gameIdByUserId = new HashMap<Integer, Integer>();
    private Map<Integer, GameParameters> gameParametersByGameId = new HashMap<Integer, GameParameters>();

    public Frontend(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        this.address = new Address();
        messageSystem.addService(this);
    }

    public void run() {
        while (true) {
            messageSystem.execForAbonent(this);
            TimeHelper.sleep(10);
        }
    }

    public void handle(String target, Request baseRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        httpServletResponse.setContentType("text/html;charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        String prefix = "Frontend";
        String sessionId = null;
        try {
            HttpSession httpSession = httpServletRequest.getSession();
            sessionId = httpSession.getId();
            if (httpSession.isNew()) {
                System.out.printf(prefix + " - New Session: %s%n", httpSession.getId());
            } else {
                System.out.printf(prefix + " - Old Session: %s%n", httpSession.getId());
            }
        } catch (IllegalStateException ex) {
            System.out.println(prefix + " - Exception!" + ex);
        }

        if (!target.equals(APP_NAME)) {
            httpServletResponse.getWriter().println("<h1>"+"Target is wrong"+"</h1>");
            return;
        }

        Integer id = null;
        if (cashedNames.isEmpty() || cashedNames.get(sessionId) == null) {
            httpServletResponse.getWriter().println(
            "SessionId: " + sessionId +
            "<form method='post' action='/'>"+
                "<input type='text' name='Name'>"+
                "<button>Enter Your name</button>"+
            "</form>");
            String name = httpServletRequest.getParameter("Name");
            if (name != null)
                cashedNames.put(sessionId, name);
        } else {
            id = nameToId.get(cashedNames.get(sessionId));
        }
        if (id != null) {
            sessionToId.put(sessionId, id);
            System.out.println(cashedNames);
            httpServletResponse.getWriter().println("<h1>"+"User name: "+ cashedNames.get(sessionId) +" Id: "+sessionToId.get(sessionId)+" sessionId: "+sessionId+"</h1>");
            Address addressGM = messageSystem.getAddressService().getAddressMap(GameMechanics.class);
            messageSystem.sendMessage(new MsgStartGameSession(getAddress(), addressGM, id));
            if (!gameIsOver) {
                Integer gameId = Integer.valueOf(gameIdByUserId(id));
                httpServletResponse.getWriter().println("Your game is N" + gameId);
                httpServletResponse.getWriter().println("score: " + gameParametersByGameId.get(gameId).getScore());
                Long date = gameParametersByGameId.get(gameId).getStartTime();
                SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
                String dateText = df2.format(date);
                httpServletResponse.getWriter().println("Game started at : " + dateText);
            }
        } else {
            if (!cashedNames.isEmpty()) {
                httpServletResponse.getWriter().println("<h1>" + "Wait for authorization, sessionId: " + sessionId + "</h1>");
                Address addressAS = messageSystem.getAddressService().getAddressMap(AccountService.class);
                messageSystem.sendMessage(new MsgGetUserId(getAddress(), addressAS, cashedNames.get(sessionId)));
            }
        }
    }

    private int gameIdByUserId(Integer id) {
        return gameIdByUserId.get(id);
    }

    public Address getAddress() {
        return address;
    }

    public void setId(String name, Integer id) {
        nameToId.put(name, id);
    }

    public void gamePlay(Integer firstPlayer, Integer secondPlayer, Integer gameId) {
        gameIsOver = false;
        gameIdByUserId.put(firstPlayer, gameId);
        gameIdByUserId.put(secondPlayer, gameId);
        GameParameters gameParameters = new GameParameters();
        gameParameters.setScore(0);
        gameParameters.setTime(new Date().getTime());

        gameParametersByGameId.put(gameId, gameParameters);
    }
}
