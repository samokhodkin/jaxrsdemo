UI_DATE_FORMAT="M d, yy";

function tableData(persons){
   var data=selectFields(persons,["id","name","patname","surname","birthDate"]);
   if(persons instanceof Array) return data.map(function(e){
      e[4]=decodeDate(e[4]);
      return e;
   });
   data[4]=decodeDate(data[4]);
   return data;
}

function selectFields(data,fields){
   if(data instanceof Array) return data.map(function(p){
      return selectObjFields(p,fields);
   });
   return selectObjFields(data,fields);
}
function selectObjFields(obj,fields){
   return fields.map(function(f){
      if(!obj[f]) return null;
      return obj[f];
   });
}

function decodeDate(t){
   if(t) return $.datepicker.formatDate(UI_DATE_FORMAT, new Date(t));
   return null;
}

function encodeDate(s){
   if(s) return $.datepicker.parseDate(UI_DATE_FORMAT, s).getTime();
   return null;
}

function printj(obj){
   console.log(JSON.stringify(obj));
}
