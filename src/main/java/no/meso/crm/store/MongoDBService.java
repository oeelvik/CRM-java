package no.meso.crm.store;

import com.mongodb.DB;
import org.springframework.stereotype.Service;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 *
 * @author oysteine
 */
@Service
public class MongoDBService {
    private MongoClient mongoClient;
    
    private DB db;
    
    private List<String> seeds;

    public void setSeeds(List<String> seeds) {
        this.seeds = seeds;
    }

    @PostConstruct
    public void init() throws RuntimeException {
        try {
            List<ServerAddress> seeds = new ArrayList<ServerAddress>();
            for (String serverAddress : this.seeds) {
                seeds.add(new ServerAddress(serverAddress.split(":")[0], Integer.parseInt(serverAddress.split(":")[1])));
            }
            mongoClient = new MongoClient(seeds);
            db = mongoClient.getDB("meso");
        } catch (UnknownHostException ex) {
            throw new RuntimeException(ex);
        }
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public DB getDb() {
        return db;
    }
    
    @PreDestroy
    public void cleanUp() {
        mongoClient.close();
    }
}
