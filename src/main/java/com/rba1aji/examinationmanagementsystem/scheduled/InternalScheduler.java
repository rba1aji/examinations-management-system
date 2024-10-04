package com.rba1aji.examinationmanagementsystem.scheduled;

import com.rba1aji.examinationmanagementsystem.model.DatabaseKeepAlive;
import com.rba1aji.examinationmanagementsystem.repository.DatabaseKeepAliveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@Slf4j
public class InternalScheduler implements CommandLineRunner {

  private final DatabaseKeepAliveRepository databaseKeepAliveRepository;

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
  }

}
