package web.main;

import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;
import org.eclipse.jetty.annotations.ServletContainerInitializersStarter;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.plus.annotation.ContainerInitializer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import web.messageSystem.MessageSystem;
import web.services.AccountService;
import web.services.Frontend;
import web.startupServerExample.JettyJSPConfiguration;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        MessageSystem ms = new MessageSystem();
        Frontend frontend = new Frontend();
        frontend.setMessageSystem(ms);
        AccountService accountService = new AccountService(ms);

        (new Thread(frontend)).start();
        (new Thread(accountService)).start();

        Server server = new Server(8080);

        WebAppContext ctx = getWebAppContext();
        server.setHandler(ctx);

        server.start();
        server.join();
    }

    private static WebAppContext getWebAppContext() {
        WebAppContext ctx = new WebAppContext();
        ctx.setDescriptor("src/main/webapp/WEB-INF/web.xml");
        ctx.setResourceBase("src/main/webapp");
        ctx.setContextPath("/messageSystem");
        ctx.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                ".*/[^/]*servlet-api-[^/]*/.jar$|.*/javax.servlet.jsp.jstl-.*/.jar$|.*/[^/]*taglibs.*/.jar$");
        ctx.setAttribute("org.eclipse.jetty.containerInitializers", jspInitializers());
        ctx.setAttribute(InstanceManager.class.getName(), new SimpleInstanceManager());
        ctx.addBean(new ServletContainerInitializersStarter(ctx), true);
        ctx.setClassLoader(new URLClassLoader(new URL[0], Main.class.getClassLoader()));
        return ctx;
    }

    private static List<ContainerInitializer> jspInitializers() {
        JettyJasperInitializer sci = new JettyJasperInitializer();
        ContainerInitializer initializer = new ContainerInitializer(sci, null);
        List<ContainerInitializer> initializers = new ArrayList<ContainerInitializer>();
        initializers.add(initializer);
        return initializers;
    }
}
