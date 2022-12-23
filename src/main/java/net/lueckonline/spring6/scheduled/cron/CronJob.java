package net.lueckonline.spring6.scheduled.cron;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronJob {

  private static final Logger logger = LogManager.getLogger();
  
  @Scheduled(cron = "0 */1 * * * *")
  public void job() {
    final var now = Instant.now();
    logger.always().log("now={} now.truncated={}", now, now.truncatedTo(ChronoUnit.SECONDS));
  }
  
  
}
