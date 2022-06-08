import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.*;
def Message processData(Message message) {
    
    
    def propriedades = message.getProperties()
    def index = propriedades.get("CamelLoopIndex")
    def body = message.getBody(String)
    def jsonParser = new JsonSlurper()
    def jsonObject = jsonParser.parseText(body)
    
    
    message.setBody(JsonOutput.toJson(jsonObject[index.toInteger()]))
    message.setHeader("Content-Type", "application/json")
    
    return message;
}