package webservices;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("rest")
public class RestActivator extends Application {
    // This class activates JAX-RS and sets the base path to /rest
    // All REST endpoints will be accessible under http://localhost:8080/your-app/rest/
}