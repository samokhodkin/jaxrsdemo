//uses jquery
//uses PERSON_API_PATH

//params: {name, patname, surname, from, to}
function dbFindPerson(params, onResp, onErr){
   dbCall(
      "find", params, onResp, onErr
   );
}

function dbGetPerson(id, onResp, onErr){
   dbCall("get", {id: id}, onResp, onErr);
}

function dbCreatePerson(person, onResp, onErr){
   dbCall("create", {data: jsonstr(person)}, onResp, onErr);
}

function dbUpdatePerson(person, onResp, onErr){
   dbCall("update", {data: jsonstr(person)}, onResp, onErr);
}

function dbDeletePerson(person, onResp, onErr){
   dbCall("delete", {id: person.id}, onResp, onErr);
}

function dbCall(method, params, onResp, onErr){
console.log("dbCall("+method+","+params+")");
   $.ajax({
      url: PERSON_API_PATH+"/"+method,
      method:"POST",
      data: params,
      contentType:"application/x-www-form-urlencoded",
      dataType: "json",
      success: onResp,
      error: function(xhr,status,error){
         if(!onErr) console.log("error: "+error+", resp:"+xhr.responseText);
         else onErr(xhr);
      }
   });
}
function jsonstr(obj){
   return JSON.stringify(obj);
}
