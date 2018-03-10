import com.x.server.ApiEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Service;

public class StartSparkApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(StartSparkApi.class);
    private final Service service;

    public StartSparkApi() {
        service = init();
        new ApiEndpoint(service).register();
    }

    public static void main(String[] args) {
        new StartSparkApi();
    }

    private Service init() {
        Service service;
        service = Service.ignite();
        service.initExceptionHandler((e) -> LOGGER.error("init", e));
        service.port(7040);
        return service;
    }
}
