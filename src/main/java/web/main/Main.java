package web.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.spi.SessionFactoryServiceRegistryBuilder;
import web.dataSets.UserDataset;
import web.messageSystem.MessageSystem;
import web.services.AccountService;
import web.services.Frontend;
import web.services.GameMechanics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws Exception {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jspserver", "root", "myp@ssw0rd");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("select * from demo");
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString("name"));
//            }
////            ResourceFactory resourceFactory = new ResourceFactory();
////            UserDataset userDataset = new UserDataset(1, "Slava");
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }

        Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/jspserver");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "myp@ssw0rd");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");

        ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

        configuration.addAnnotatedClass(UserDataset.class);

        SessionFactory sf = configuration.buildSessionFactory(sr);
        Session session = sf.openSession();
        session.beginTransaction();

        UserDataset ydc = new UserDataset(2, "Ana");

        session.save(ydc);
        session.getTransaction().commit();

        session.close();
        sf.close();

        MessageSystem ms = new MessageSystem();
        Frontend frontend = new Frontend(ms);
        AccountService accountService = new AccountService(ms);
        GameMechanics gameMechanics = new GameMechanics(ms);

        (new Thread(frontend)).start();
        (new Thread(accountService)).start();
        (new Thread(gameMechanics)).start();

        Server server = new Server(8080);

        SessionHandler sessionHandler = new SessionHandler();
        sessionHandler.setHandler(frontend);

        server.setHandler(sessionHandler);

//        VFS vfs = new VFSImpl("/Volumes/Macbook/Users/vladislavborisov/IdeaProjects/jspserver/vfs");

        server.start();
        server.join();
    }

}
