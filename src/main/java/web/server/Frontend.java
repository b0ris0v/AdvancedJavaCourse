//package web.server;
//
//import org.eclipse.jetty.server.Request;
//import org.eclipse.jetty.server.handler.AbstractHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class Frontend extends AbstractHandler implements Runnable {
//    int count = 0;
//    private final static Logger LOGGER =
//            Logger.getLogger("global");
//    public void run() {
//        class Logger extends TimerTask {
//            public void run() {
//                LOGGER.log(Level.INFO, String.valueOf(count));
//            }
//        }
//// And From your main() method or any other method
//        Timer timer = new Timer();
//        timer.schedule(new Logger(), 0, 5000);//5 sec
//    }
//
//    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
//    {
//        PrintWriter out = response.getWriter();
//        response.setContentType("text/plain");
//
//        count++;
//
//        out.println("HELLO WORLD!!" + count);
//        out.close();
//    }
//}
