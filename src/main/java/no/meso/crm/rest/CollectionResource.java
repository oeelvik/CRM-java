package no.meso.crm.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author oysteine
 */
@Controller
@RequestMapping("/collection/{collectionName:.*}")
public class CollectionResource {
    
    @RequestMapping(
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public String readCollection(@PathVariable("collectionName") String collectionName) {
        return "Collection: " + collectionName;
    }
    
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public String createDocument(@PathVariable("collectionName") String collectionName) {
        return "Document: " + collectionName + "/documentId" ;
    }
}
