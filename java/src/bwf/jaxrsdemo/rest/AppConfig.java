package bwf.jaxrsdemo.rest;

import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@ApplicationPath("/api")
public class AppConfig extends Application{
   public Set<Class<?>> getClasses(){
      return new HashSet<Class<?>>(Arrays.asList(
         PersonApi.class,
         GsonConverter.class
      ));
   }
   
//   public Set<Object> getSingletons(){
//      ;
//   }
}