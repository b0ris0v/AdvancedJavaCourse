package web.startupServerExample;

import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;
import org.eclipse.jetty.annotations.ServletContainerInitializersStarter;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.plus.annotation.ContainerInitializer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class JettyJSPConfiguration {

    public static void main(String[] args) {

        Server server = new Server(8580);
//        startWebserver(server);
//        setServletWithSession(server);

//        setFrontend(server);

        WebAppContext ctx = getWebAppContext();
        server.setHandler(ctx);
        LoginServlet loginServlet = new LoginServlet();
        Thread thread =new Thread(loginServlet);
        thread.start();


//        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
//        URL urlTaglibs = null;
//        try {
//            urlTaglibs = new File("http://java.sun.com/jsp/jstl/core").toURI().toURL();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        URLClassLoader newClassLoader = new URLClassLoader(new URL[]{urlTaglibs},currentClassLoader);
//        Thread.currentThread().setContextClassLoader(newClassLoader);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private static void setFrontend(Server server) {
//        //--------------------------
//        Frontend servletHandler = new Frontend();
//        Thread thread =new Thread(servletHandler);
//        thread.start();
//        ContextHandler context = new ContextHandler("/frontend/*");
//        context.setHandler(servletHandler);
//        server.setHandler(context);
//    }

//    private void startWebserver(Server server) {
//
//        server.setSessionIdManager(new HashSessionIdManager());
//
////        RewriteHandler rewriteHandler = new RewriteHandler();
////        rewriteHandler.setRewriteRequestURI(true);
////        rewriteHandler.setRewritePathInfo(true);
////        rewriteHandler.setOriginalPathAttribute("requestedPath");
////        RedirectRegexRule rule = new RedirectRegexRule();
////        rule.setRegex("/");
////        rule.setReplacement("/index");
////        rewriteHandler.addRule(rule);
//
//        WebAppContext dynamicHandler = new WebAppContext();
//        String webDir = this.getClass().getClassLoader().getResource("web").toExternalForm();
//        dynamicHandler.setResourceBase(webDir);
//
////        dynamicHandler.addServlet(new ServletHolder(new IndexServlet(this)),"/index");
////        dynamicHandler.addServlet(new ServletHolder(new LTIServlet(this)),"/lti");
////        dynamicHandler.addServlet(new ServletHolder(new TokenServlet(this)),"/token");
////        dynamicHandler.addServlet(new ServletHolder(new AssignmentServlet(this)),"/assignment");
////        dynamicHandler.addServlet(new ServletHolder(new ConsumerServlet(this)),"/consumer");
////        dynamicHandler.addServlet(new ServletHolder(new LTIConfigServlet()),"/config.xml");
//
//        ResourceHandler staticHandler = new ResourceHandler();
//        String staticDir = this.getClass().getClassLoader().getResource("static").toExternalForm();
//        staticHandler.setResourceBase(staticDir);
//
//        HandlerList handlers = new HandlerList();
//        handlers.setHandlers(new Handler[] { /*rewriteHandler,*/ staticHandler, dynamicHandler, new DefaultHandler() });
//        server.setHandler(handlers);
//    }

    private static WebAppContext getWebAppContext() {
        WebAppContext ctx = new WebAppContext();
        ctx.setDescriptor("src/main/webapp/WEB-INF/web.xml");
        ctx.setResourceBase("src/main/webapp");
        ctx.setContextPath("/jettyjspconfiguration-example");
        ctx.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                ".*/[^/]*servlet-api-[^/]*/.jar$|.*/javax.servlet.jsp.jstl-.*/.jar$|.*/[^/]*taglibs.*/.jar$");
        ctx.setAttribute("org.eclipse.jetty.containerInitializers", jspInitializers());
        ctx.setAttribute(InstanceManager.class.getName(), new SimpleInstanceManager());
        ctx.addBean(new ServletContainerInitializersStarter(ctx), true);
        ctx.setClassLoader(new URLClassLoader(new URL[0], JettyJSPConfiguration.class.getClassLoader()));
        return ctx;
    }

    private static List<ContainerInitializer> jspInitializers() {
        JettyJasperInitializer sci = new JettyJasperInitializer();
        ContainerInitializer initializer = new ContainerInitializer(sci, null);
        List<ContainerInitializer> initializers = new ArrayList<ContainerInitializer>();
        initializers.add(initializer);
        return initializers;
    }
//    private static void setServletWithSession(Server server) {
////        //-----------------------
////        ServletContextHandler context = new ServletContextHandler();
////        context.setContextPath("/");
////        server.setHandler(context);
////
////        server.dumpStdErr();
////
////        SecurityHandler security = new ConstraintSecurityHandler();
////        security.setAuthenticator(new FormAuthenticator());
////
////        ServletHandler servlet = context.getServletHandler();
////        context.setHandler(security);
////        security.setHandler(servlet);
//
//
//
//        ServletContextHandler context = new ServletContextHandler(server, "/*", ServletContextHandler.SESSIONS);
////        context.setContextPath("/frontend");
//        FrontendServlet servletHandler = new FrontendServlet();
//
//        context.addServlet(servletHandler.getClass(),"/frontend");
//        context.addServlet(Login.class,"/login");
//        context.addServlet(LoginServlet.class,"/loginS");
//
////        context.setResourceBase("src/main/webapp");
////        context.setContextPath("/jettyjspconfiguration-example");
//
//
//        Thread thread =new Thread(servletHandler);
//        thread.start();
//
////        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
//
////        Constraint constraint = new Constraint();
////        constraint.setName(Constraint.__FORM_AUTH);
////        constraint.setAuthenticate(true);
////        constraint.setRoles(new String[] { "user", "admin" });
////
////        ConstraintMapping cm = new ConstraintMapping();
////        cm.setPathSpec("/frontend/*");
////        cm.setConstraint(constraint);
//
////        security.setConstraintMappings(Arrays
////                .asList(new ConstraintMapping[] { cm }));
////        security.setAuthenticator(new FormAuthenticator("login.jsp", "damn...", false));
////        security.setLoginService(new HashLoginService());
//
//
//        // 1) Causes No SessionManager
//        // context.setSecurityHandler(security);
//        // server.setHandler(context);
//
//        // 2) Works
////         context.setSecurityHandler(security);
//
////         context.setSecurityHandler(basicAuth("uname","pass", "rname"));//Для авторизации по этому логин/паролю, т.е. другие пользователи не зайдут
//         server.setHandler(context.getSessionHandler());
//
//        // 3) Causes No SessionManager
//        // security.setHandler(context);
//        // server.setHandler(security);
//
//        // 4) Causes No SessionManager
////        security.setHandler(context.getServletHandler());
////        server.setHandler(security);
//    }

//    //Для авторизации по этому логин/паролю, т.е. другие пользователи не зайдут
//    private static SecurityHandler basicAuth(String username, String password, String realm) {
//
//        HashLoginService l = new HashLoginService();
//        l.putUser(username, Credential.getCredential(password), new String[]{"user"});
//        l.setName(realm);
//
//        Constraint constraint = new Constraint();
//        constraint.setName(Constraint.__BASIC_AUTH);
//        constraint.setRoles(new String[]{"user"});
//        constraint.setAuthenticate(true);
//
//        ConstraintMapping cm = new ConstraintMapping();
//        cm.setConstraint(constraint);
//        cm.setPathSpec("/*");
//
//        ConstraintSecurityHandler csh = new ConstraintSecurityHandler();
//        csh.setAuthenticator(new BasicAuthenticator());
//        csh.setRealmName("myrealm");
//        csh.addConstraintMapping(cm);
//        csh.setLoginService(l);
//
//        return csh;
//
//    }
}