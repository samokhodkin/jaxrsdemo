package bwf.jaxrsdemo.rest;

import java.util.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.lang.reflect.Type;
import java.lang.annotation.Annotation;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;

import com.google.gson.*;

import bwf.jaxrsdemo.api.*;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GsonConverter implements 
   MessageBodyReader<Object>, MessageBodyWriter<Object>//, ParamConverterProvider downgrade to EE6
{
   private static final Charset utf8=Charset.forName("utf8");
   
   public static final Gson gson = new GsonBuilder().registerTypeAdapter(
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
   
   public boolean isReadable(
      Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType
   ){
//System.out.println("GsonConverter.isReadable("+type+")");
      return mediaType.equals(MediaType.APPLICATION_JSON_TYPE);
   }
   
   public Object readFrom(
      Class<Object> type, Type genericType, Annotation[] annotations, 
      MediaType mediaType, MultivaluedMap<String,String> httpHeaders, 
      InputStream entityStream
   ) throws IOException{
      return gson.fromJson(new InputStreamReader(entityStream,utf8), type);
   }
   
   public long getSize(
      Object obj, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType
   ){
      return -1;
   }
   
   public boolean isWriteable(
      Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType
   ){
//System.out.println("GsonConverter.isWriteable("+type+","+genericType+",..,"+mediaType+")");
      return mediaType.equals(MediaType.APPLICATION_JSON_TYPE);
   }
   
   public void writeTo(
      Object obj, Class<?> type, Type genericType, Annotation[] annotations, 
      MediaType mediaType, MultivaluedMap<String,Object> httpHeaders,
      OutputStream entityStream
   ) throws IOException{
      String s=gson.toJson(obj);
      System.out.println(" -> "+s);
      ByteBuffer bb=utf8.encode(s);
      entityStream.write(bb.array(), bb.arrayOffset(), bb.remaining());
   }
   
//   public <T> ParamConverter<T> getConverter(
//      final Class<T> rawType, Type genericType, Annotation[] annotations
//   ){
////System.out.println("GsonConverter.getConverter("+rawType+",..)");
//      if(rawType.isPrimitive()) return null;
//      if(rawType==String.class) return null;
//      return new ParamConverter<T>(){
//         public T fromString(String value){
////System.out.println("GsonConverter.getConverter("+rawType+").fromString("+value+")");
//            return (T)gson.fromJson(value,rawType);
//         }
//         public String toString(T value){
//            return gson.toJson(value);
//         }
//      };
//   }
}