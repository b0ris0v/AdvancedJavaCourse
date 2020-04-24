//package web.main;
//
//import web.messageSystem.MessageSystem;
//import web.messageSystem.MsgToFrontend;
//import web.messageSystem.MsgUpdateUserId;
//import web.services.FrontendServlet;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class LoginServlet extends HttpServlet {
//    protected void doPost(HttpServletRequest request,
//                          HttpServletResponse response) throws ServletException, IOException {
//        String userName = request.getParameter("user");
//        String sessionId = request.getSession().getId();
//        //Start process changing the name
//
//        MessageSystem ms = (MessageSystem) request.getSession().getAttribute("ms");
//        FrontendServlet frontend = (FrontendServlet) request.getSession().getAttribute("frontend");
//        ms.sendMessage(new MsgUpdateUserId());
//    }
//}