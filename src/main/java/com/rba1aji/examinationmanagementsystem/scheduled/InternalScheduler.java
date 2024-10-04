package com.rba1aji.examinationmanagementsystem.scheduled;

import com.rba1aji.examinationmanagementsystem.model.DatabaseKeepAlive;
import com.rba1aji.examinationmanagementsystem.repository.DatabaseKeepAliveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@Slf4j
public class InternalScheduler implements CommandLineRunner {

  private final DatabaseKeepAliveRepository databaseKeepAliveRepository;

  @Value("${server.host.url}")
  private String serverHostUrl;

  @Override
  public void run(String... args) {
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        DatabaseKeepAlive databaseKeepAlive = new DatabaseKeepAlive();
        databaseKeepAliveRepository.save(databaseKeepAlive);
        log.info("Database keep alive task executed");
      }
    };
    long delay = 0;
    long period = TimeUnit.HOURS.toMillis(12);
    timer.scheduleAtFixedRate(task, delay, period);

    TimerTask serverKeepAliveTask = new TimerTask() {
      @Override
      public void run() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> resp = restTemplate.exchange(serverHostUrl, HttpMethod.GET, new HttpEntity<>(null), String.class);
        log.info("Server keep alive task executed {}", resp.getStatusCode());
      }
    };
    long serverKeepAliveInterval = TimeUnit.MINUTES.toMillis(10);
    timer.scheduleAtFixedRate(serverKeepAliveTask, delay, serverKeepAliveInterval);
  }

}
