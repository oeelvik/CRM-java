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
@RequestMapping("/collection/{collectionName:.*}/{documentId:.*}")
public class DocumentResource {
    
    @RequestMapping(
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public String readDocument(
            @PathVariable("collectionName") String collectionName,
            @PathVariable("documentId") String documentId) {
        
        return "Document: " + collectionName + "/" + documentId;
    }
    
    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public String updateDocument(
            @PathVariable("collectionName") String collectionName,
            @PathVariable("documentId") String documentId) {
        
        return "Document: " + collectionName + "/" + documentId + " updated";
    }
    
    @RequestMapping(
            method = RequestMethod.DELETE,
            consumes = "application/json",
            produces = "application/json")
    public void deleteDocument() {
        
    }
}
