package bwf.jaxrsdemo.rest;

import java.util.*;
import java.lang.reflect.Type;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.google.gson.*;

import bwf.jaxrsdemo.api.*;

@Path("/person")
public class PersonApi{
   public static Db db; //inject
   
   private static final Gson gson = new GsonBuilder().registerTypeAdapter(
      Date.class, new JsonSerializer<Date>(){
         public JsonElement serialize(
            Date src, Type typeOfSrc, JsonSerializationContext context
         ){
            return src == null ? null : new JsonPrimitive(src.getTime());
         }
      }
   ).registerTypeAdapter(Date.class, new JsonDeserializer<Date>(){
      public Date deserialize(
         JsonElement json, Type typeOfT, JsonDeserializationContext context
      ) throws JsonParseException{
         return json == null ? null : new Date(json.getAsLong());
      }
   }).create();
   
   @GET
   @Path("/get")
   @Produces(MediaType.APPLICATION_JSON)
   public Person get(@QueryParam("id") long id){
      return db.find(id);
   }
   
   @POST
   @Path("/get")
   @Produces(MediaType.APPLICATION_JSON)
   public Person getByPost(@FormParam("id") long id){
      return get(id);
   }
   
   
   @GET
   @Path("/find")
   @Produces(MediaType.APPLICATION_JSON)
   public List<Person> find(
      @QueryParam("name") String name,
      @QueryParam("patname") String patname,
      @QueryParam("surname") String surname,
      @QueryParam("from") Date from,
      @QueryParam("to") Date to
   ){
System.out.println("Api.find("+name+","+patname+","+surname+","+from+","+to+")");
      return db.find(name,patname,surname,from,to);
   }
   
   @POST
   @Path("/find")
   @Produces(MediaType.APPLICATION_JSON)
   public List<Person> findByPost(
      @FormParam("name") String name,
      @FormParam("patname") String patname,
      @FormParam("surname") String surname,
      @FormParam("from") Date from,
      @FormParam("to") Date to
   ){
System.out.println("Api.findByPost("+name+","+patname+","+surname+","+from+","+to+")");
      return find(name,patname,surname,from,to);
   }
   
   @POST
   @Path("/create")
   @Produces(MediaType.APPLICATION_JSON)
   public Person create(@FormParam("data") Person data){
      return db.create(data);
   }
   
   @POST
   @Path("/update")
   @Produces(MediaType.APPLICATION_JSON)
   public void update(@FormParam("data") Person data){
System.out.println("Api.update(): data="+data);
      db.update(data);
   }
   
   @POST
   @Path("/delete")
   @Produces(MediaType.APPLICATION_JSON)
   public void delete(@FormParam("id") long id){
System.out.println("Api.delete(): id="+id);
      db.delete(id);
   }
   
   @GET
   @Path("/test")
   @Produces(MediaType.APPLICATION_JSON)
   public Person test(){
System.out.println("Api.test()");
      return new Person();
   }
   
   @POST
   @Path("/test")
   @Produces(MediaType.APPLICATION_JSON)
   public Person testPost(@FormParam("data") Person p){
System.out.println("Api.testPost("+p+")");
      return p;
   }
   
   @GET
   @Path("/hello")
   @Produces(MediaType.TEXT_PLAIN)
   public String hello(){
      return "Hello!!!";
   }
   
   public static void main(String[] args){
      //Gson gson=new Gson();
      Date d=new Date();
      String s=gson.toJson(new Date());
      System.out.println(d);
      System.out.println(s);
      System.out.println(gson.fromJson(s,Date.class));
   }
}