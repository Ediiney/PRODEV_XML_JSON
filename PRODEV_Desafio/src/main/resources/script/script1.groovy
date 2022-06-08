import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.*;
import java.util.ArrayList;
def Message processData(Message message) {
    
    def body = message.getBody(String)
    def headers = message.getHeaders()
    def size = headers.get("size").toInteger()
    def jsonParser = new JsonSlurper()
    def jsonObject = jsonParser.parseText(body)
    def json
    def array = []
    
    jsonObject.root.Export.ET_CLIENTS.TTYPE_ZTT_CLIENTE_RESERVA.forEach{ e ->
    json = JsonOutput.toJson(
    id                   : e.CLIENT,   
    clienteGmr           : e.CLIENT_GMR,
    clienteDg            : e.CLIENT_GD,   
    shippingPoint        : e.SHIPPING_POINT,  
    region               : e.REGION,    
    industryKEY          : e.INDUSTRY_KEY,   
    salesOrg             : e.SALES_ORG,   
    distributionChannel  : e.DISTRIBUTION_CHANNEL,
    division             : e.DIVISION,     
    salesGroup           : e.SALES_GROUP,  
    salesOffice          : e.SALES_OFFICE,  
    cpf                  : e.CPF,
    cnpj                 : e.CNPJ
    )
    array.push(json)
    }
    def a = []
    def length = array.size()

    envioJson(message, 0, array, a, size, length)
    return message;
    
}

def void envioJson(Message message, int init, def array, def a, int size, int length){
    def fim = init + size
    def it
    def ar = []
    for(int i =init; i < fim && i < size; i++){
        ar.push(array[i])
        it = i
    }
    
    a.push(ar)
    
    if(it == size - 1){
      //continua
    } else{
        envioJson(message, fim, array, a, size, length)
    }

    message.setBody(a.toString())
    
}