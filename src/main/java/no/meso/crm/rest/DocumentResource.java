package no.meso.crm.rest;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import javax.servlet.http.HttpServletResponse;
import no.meso.crm.store.MongoDBService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Øystein Schrøder Elvik
 */
@Controller
@RequestMapping("/rest/data/{collectionName:.*}/{documentId:.*}")
public class DocumentResource {
    
    @Autowired
    private MongoDBService mongo;
    
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String readDocument(
            @PathVariable("collectionName") String collectionName,
            @PathVariable("documentId") String documentId,
            HttpServletResponse response) {
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        DBCollection collection = mongo.getDb().getCollection(collectionName);
        DBObject searchById = new BasicDBObject("_id", new ObjectId(documentId));
        
        return JSON.serialize(collection.findOne(searchById));
    }
    
    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateDocument(
            @PathVariable("collectionName") String collectionName,
            @PathVariable("documentId") String documentId,
            @RequestBody String body,
            HttpServletResponse response) {
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        DBCollection collection = mongo.getDb().getCollection(collectionName);
        DBObject searchById = new BasicDBObject("_id", new ObjectId(documentId));
        DBObject dbObject = (DBObject) JSON.parse(body);
        
        collection.update(searchById, dbObject);
        
        return JSON.serialize(dbObject);
    }
    
    @RequestMapping(
            method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteDocument(
            @PathVariable("collectionName") String collectionName,
            @PathVariable("documentId") String documentId) {
        
        DBCollection collection = mongo.getDb().getCollection(collectionName);
        DBObject searchById = new BasicDBObject("_id", new ObjectId(documentId));
        collection.remove(searchById);
    }
}
