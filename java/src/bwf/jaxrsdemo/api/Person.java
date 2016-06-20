package bwf.jaxrsdemo.api;

import java.util.*;
import javax.persistence.*;

import com.google.gson.Gson;

@Entity
public class Person implements Cloneable{
   public Long id;
   public String name;
   public String patname;
   public String surname;
   public Date birthDate;
   
   public String toString(){
      return "Person{"+
         "name="+name+", "+
         "patname="+patname+", "+
         "surname="+surname+", "+
         "birthDate="+birthDate+
      "}";
   }
   
   public Person copy(){
      try{
         return (Person)clone();
      }
      catch(Exception e){throw new Error();}
   }
}