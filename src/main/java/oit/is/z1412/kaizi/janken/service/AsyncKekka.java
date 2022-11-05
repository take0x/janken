package oit.is.z1412.kaizi.janken.service;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z1412.kaizi.janken.model.Match;
import oit.is.z1412.kaizi.janken.model.MatchMapper;

@Service
public class AsyncKekka {

  boolean dbUpdated = false;
  Match match;
  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  MatchMapper matchMapper;

  public void syncFinishMatch(Match match) {
    this.dbUpdated = true;
    this.match = match;
    matchMapper.insertMatch(match);
  }

  @Async
  public void asyncShowResult(SseEmitter emitter) {
    try {
      while (true) {// 無限ループ
        // DBが更新されていなければ0.5s休み
        if (!dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }

        System.out.println(match.getId());
        emitter.send(match);
        TimeUnit.MILLISECONDS.sleep(1000);
        dbUpdated = false;
        matchMapper.updateById(match.getId());
      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncShowResult complete");
  }
}
