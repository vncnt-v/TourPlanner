package TourPlannerUI.businesslayer.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.config.Configurator;
import java.util.stream.IntStream;

public class LoggingImpl implements ILogging {

    private final Logger log;

    public LoggingImpl() {
        Configurator.initialize(null, "log.conf.xml");
        log = LogManager.getLogger(LoggingImpl.class);
    }

    @Override
    public void execute() {
        Marker flowMarker = new MarkerManager.Log4jMarker("NORMAL");
        IntStream.range(0,20).parallel().forEach(x -> {
            log.fatal(flowMarker, "entering application"); // check marker here
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("exiting application");
        });
    }
}