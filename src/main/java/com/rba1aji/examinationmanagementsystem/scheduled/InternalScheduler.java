package com.rba1aji.examinationmanagementsystem.scheduled;

import com.rba1aji.examinationmanagementsystem.model.DatabaseKeepAlive;
import com.rba1aji.examinationmanagementsystem.repository.DatabaseKeepAliveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@Slf4j
public class InternalScheduler {

  private final DatabaseKeepAliveRepository databaseKeepAliveRepository;

  @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
  public void keepAlive() {
    databaseKeepAliveRepository.save(new DatabaseKeepAlive());
    log.info("Executed Database Keep Alive Task");
  }

}
