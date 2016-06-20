package bwf.jaxrsdemo.db;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import bwf.jaxrsdemo.api.*;

public class MockDb implements Db{
   private Map<Long,Person> db=new ConcurrentHashMap<>();
   private AtomicLong idGen=new AtomicLong(0);
   
   public Person create(Person p){
      p=p.copy();
      p.id=idGen.incrementAndGet();
      db.put(p.id,p);
      return p;
   }
   public void update(Person p){
      if(p==null) throw new IllegalArgumentException("null person");
      if(p.id==null) throw new IllegalArgumentException("null person id");
      db.put(p.id,p.copy());
   }
   public void delete(long id){
      db.remove(id);
   }
   public Person find(long id){
      return db.get(id);
   }
   public List<Person> find(
      String name, String patname, String surname, Date bornFrom, Date bornTo
   ){
      List<Person> list=new ArrayList<>();
      for(Person p: db.values()){
         if(!matches(name,p.name)) continue;
         if(!matches(patname,p.patname)) continue;
         if(!matches(surname,p.surname)) continue;
         if(before(p.birthDate, bornFrom)) continue;
         if(before(bornTo, p.birthDate)) continue;
         list.add(p);
      }
      return list;
   }
   
   private static boolean matches(Object o1, Object o2){
      if(o1==null) return true;
      if(o2==null) return true;
      return o1.equals(o2);
   }
   
   private static boolean before(Date d1, Date d2){
      if(d1==null) return false;
      if(d2==null) return false;
      return d1.compareTo(d2)<0;
   }
   
   public static void main(String[] args){
      MockDb db=new MockDb();
      long t=System.currentTimeMillis();
      
      Date d1=new Date(t);
      Date d2=new Date(t+1000);
      Date d3=new Date(t+2000);
      Date d4=new Date(t+3000);
      Date d5=new Date(t+4000);
      
      Person p1=new Person();
      p1.name="Tom";
      p1.birthDate=d2;
      
      Person p2=new Person();
      p2.name="Peter";
      p2.birthDate=d4;
      
      db.create(p1);
      db.create(p2);
      
      System.out.println(db.find(null,null,null,null,null)); //all
      System.out.println(db.find(null,null,null,d1,d5));     //all
      System.out.println(db.find(null,null,null,d1,null));   //all
      System.out.println(db.find(null,null,null,d1,d3));     //tom
      System.out.println(db.find(null,null,null,null,d5));   //all
      System.out.println(db.find(null,null,null,d3,d5));     //peter
   }
}