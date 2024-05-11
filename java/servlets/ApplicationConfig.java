package servlets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.glassfish.jersey.CommonProperties;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@ApplicationPath("resources")
public class ApplicationConfig extends Application {
	
	 @Override
     public Map<String, Object> getProperties() {

         Map<String, Object> properties = new HashMap<String, Object>();
         properties.put("jersey.config.server.provider.packages", "com.study.rest");
         properties.put("jersey.config.server.wadl.disableWadl", "true");
         properties.put("jersey.config.server.provider.classnames","org.glassfish.jersey.media.multipart.MultiPartFeature");
         properties.put(CommonProperties.OUTBOUND_CONTENT_LENGTH_BUFFER,"0");
         return properties;
	 }
}
