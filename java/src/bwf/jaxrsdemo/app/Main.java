package bwf.jaxrsdemo.app;

import java.net.*;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.*;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.server.session.*;
import org.eclipse.jetty.util.thread.*;

import bwf.jaxrsdemo.api.*;
import bwf.jaxrsdemo.rest.*;
import bwf.jaxrsdemo.db.*;

import org.glassfish.jersey.servlet.ServletContainer;

public class Main{
   static final String HOST="localhost";
   static final int PORT=8080;
   
   static final String WWW_RESOURCE_PATH="/";
   //REST_CONTEXT_PATH+REST_SERVLET_PATH == <war name>/AppConfig@ApplicationPath
   //sync with person.html::PERSON_API_PATH
   static final String WWW_REST_CONTEXT_PATH="/api";
   static final String WWW_REST_SERVLET_PATH="/*";
   
   public static void main(String[] args) throws Exception{
      String homePath=detectHomePath();
      String wwwRootPath=homePath+"/wwwroot";
System.out.println("homePath="+homePath);
System.out.println("wwwRootPath="+wwwRootPath);

      QueuedThreadPool threadPool = new QueuedThreadPool();
      threadPool.setMaxThreads(100);
      Server server=new Server(threadPool);
      ServerConnector http=new ServerConnector(
         server, new HttpConnectionFactory()
      );
      http.setHost(HOST);
      http.setPort(PORT);
      http.setIdleTimeout(30000);
      server.addConnector(http);
      
      HandlerList handlers=new HandlerList();
      server.setHandler(handlers);
      
      ContextHandler resContext=new ContextHandler();
      resContext.setContextPath(WWW_RESOURCE_PATH);
      ResourceHandler res=new ResourceHandler();
      res.setDirectoriesListed(true);
      res.setMinMemoryMappedContentLength(-1);//allow hot replace of static content
      res.setResourceBase(wwwRootPath);
      resContext.setHandler(res);
      handlers.addHandler(resContext);

//      //general servlet ontext      
//      ServletContextHandler servlets=new ServletContextHandler();
//      servlets.setContextPath("/servlets");
//      //servlets.addServlet(new ServletHolder("session servlet", sessionServlet), "/session");
//      handlers.addHandler(servlets);
      
      //rest handlers
      ServletContextHandler rest=new ServletContextHandler();
      rest.setContextPath(WWW_REST_CONTEXT_PATH);
      ServletHolder restServlet=new ServletHolder("Rest container", ServletContainer.class);
//      restServlet.setInitParameter(
//         "jersey.config.server.provider.classnames",
//         PersonApi.class.getName()+","+GsonConverter.class.getName()
//      );
      restServlet.setInitParameter(
         "javax.ws.rs.Application", AppConfig.class.getName()
      );
      rest.addServlet(restServlet, WWW_REST_SERVLET_PATH);//same as path in AppConfig
      rest.addEventListener(new ServletContextInitializer());
      handlers.addHandler(rest);
      
      server.start();
      server.join();
   }
   
   static String detectHomePath() throws IOException{
      File f=new File(new File(".").getCanonicalPath());
      for(;;){
         if(
            new File(f,"java").isDirectory()
         ){
            return f.getPath();
         }
         String parent=f.getParent();
         if(parent==null) break;
         f=new File(parent);
      }
      throw new RuntimeException("Home not found");
   }
   
   static String path(String p1, String p2){
      if(p1==null) return p2;
      if(p1.length()==0) return p2;
      if(p2.startsWith("/")){
         if(p1.equals("/")) return p2;
         if(p1.endsWith("/")) return p1+p2.substring(1);
         return p1+p2;
      }
      if(!p1.endsWith("/")) return p1+"/"+p2;
      return p1+p2;
   }
}