package no.meso.crm.rest;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import no.meso.crm.store.MongoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Øystein Schrøder Elvik
 */
@Controller
@RequestMapping("/collection/{collectionName:.*}")
public class CollectionResource {
    
    @Autowired
    private MongoDBService mongo;
    
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void readCollection(
            @PathVariable("collectionName") String collectionName,
            HttpServletResponse response) throws IOException {
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        DBCollection collection = mongo.getDb().getCollection(collectionName);
        
        DBCursor cursor = collection.find();
        response.getOutputStream().write("[".getBytes());
        boolean first = true;
        while(cursor.hasNext()) {
            if(first) {
                first = false;
            } else {
                response.getOutputStream().write(", ".getBytes());
            }
            response.getOutputStream().write(cursor.next().toString().getBytes());
        }
        response.getOutputStream().write("]".getBytes());
    }
    
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String createDocument(
            @PathVariable("collectionName") String collectionName,
            @RequestBody String body,
            HttpServletResponse response) {
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        DBCollection collection = mongo.getDb().getCollection(collectionName);
        DBObject dbObject = (DBObject) JSON.parse(body);
        collection.insert(dbObject);
        
        return JSON.serialize(dbObject);
    }
}
