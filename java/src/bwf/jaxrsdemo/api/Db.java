package bwf.jaxrsdemo.api;

import java.util.*;

//must be thread-safe!!!
public interface Db{
   public Person create(Person p);
   public void update(Person p);
   public void delete(long id);
   public Person find(long id);
   public List<Person> find(
      String name, String patname, String surname, Date bornFrom, Date bornTo
   );
}