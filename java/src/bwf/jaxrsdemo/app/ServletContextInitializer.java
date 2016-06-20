package bwf.jaxrsdemo.app;

import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;

import bwf.jaxrsdemo.api.*;
import bwf.jaxrsdemo.db.MockDb;
import bwf.jaxrsdemo.rest.PersonApi;

@WebListener
public class ServletContextInitializer implements ServletContextListener{
   public void contextInitialized(ServletContextEvent sce){
System.out.println("****ServletContextInitializer.contextInitialized()****");
      Db db=new MockDb();
      PersonApi.db=db;
      for(int i=5;i-->0;){
         Person p=new Person();
         p.birthDate=new Date(System.currentTimeMillis()-(20-i)*3600000*24*365);
         p.name="person "+i;
         db.create(p);
      }
   }
   
   public void contextDestroyed(ServletContextEvent sce){}
}