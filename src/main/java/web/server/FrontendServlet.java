//package web.server;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Date;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class FrontendServlet extends HttpServlet implements Runnable {
//    private static AtomicInteger count = new AtomicInteger(0);
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        count.set(count.incrementAndGet());
//        PrintWriter out = response.getWriter();
//        Date date = new Date();
//        out.println("<html>" + "<h2>Hi There!</h2><br/><h3>Date="+date +"</h3>");
//        try
//        {
//            HttpSession session = request.getSession();
//            if (session.isNew())
//            {
//                out.printf("New Session: %s%n", session.getId());
////                response.sendRedirect("../webapp/web.server.EnterTheName.html");
////                request.getRequestDispatcher("web.server.EnterTheName.html");
//                response.sendRedirect("EnterTheName");
//                //Введите имя
//                //Запуск процесса ввода имени
//            }
//            else
//            {
//                out.printf("Old Session: %s%n", session.getId());
//                //Имя введено?
//                //нет
//                //  Процесс ввода имени запущен?
//                //  нет
//                //      Введите имя
//                //      Запуск процесса ввода имени
//                //  да
//                //      Подождите идет загрузка
//                //да
//                //  Сообщение о приветствии
//            }
//        }
//        catch (IllegalStateException ex)
//        {
//            out.println("Exception!" + ex);
//            ex.printStackTrace(out);
//        }
//        out.println("</html>");
//        System.out.println("hm.. it works");
//    }
//
//    private final static Logger LOGGER =
//            Logger.getLogger("global");
//    public void run() {
//        class Logger extends TimerTask {
//            public void run() {
//                LOGGER.log(Level.INFO, String.valueOf(count.get()));
//            }
//        }
//// And From your main() method or any other method
//        Timer timer = new Timer();
//        timer.schedule(new Logger(), 0, 5000);//5 sec
//    }
//}
