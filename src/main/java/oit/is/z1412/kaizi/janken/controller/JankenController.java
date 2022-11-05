package oit.is.z1412.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.ui.ModelMap;

import oit.is.z1412.kaizi.janken.model.Entry;
import oit.is.z1412.kaizi.janken.model.User;
import oit.is.z1412.kaizi.janken.model.UserMapper;
import oit.is.z1412.kaizi.janken.service.AsyncKekka;
import oit.is.z1412.kaizi.janken.model.Match;
import oit.is.z1412.kaizi.janken.model.MatchInfo;
import oit.is.z1412.kaizi.janken.model.MatchMapper;
import oit.is.z1412.kaizi.janken.model.MatchInfoMapper;

@Controller
public class JankenController {

  @Autowired
  private Entry entry;

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

  @Autowired
  AsyncKekka kekka;

  @GetMapping("/janken")
  @Transactional
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);
    model.addAttribute("user", loginUser);

    ArrayList<User> users = userMapper.selectAllUser();
    model.addAttribute("users", users);
    ArrayList<Match> matches = matchMapper.selectAllMatch();
    model.addAttribute("matches", matches);
    ArrayList<MatchInfo> matchInfo = matchInfoMapper.selectAllActiveMatch();
    model.addAttribute("matchInfo", matchInfo);
    return "janken.html";
  }

  @GetMapping("/match")
  public String match(@RequestParam int id, Principal prin, ModelMap model) {
    model.addAttribute("user1", userMapper.selectByName(prin.getName()));
    model.addAttribute("user2", userMapper.selectById(id));
    return "match.html";
  }

  @GetMapping("/fight")
  public String fight(@RequestParam int id, @RequestParam String hand, Principal prin, ModelMap model) {
    Match match = new Match();
    MatchInfo matchInfo = new MatchInfo();
    int loginId = userMapper.selectByName(prin.getName()).getId();
    ArrayList<MatchInfo> activeMatch = matchInfoMapper.selectAllActiveMatchById(loginId);

    if (activeMatch.size() == 0) {
      matchInfo.setUser1(loginId);
      matchInfo.setUser2(id);
      matchInfo.setUser1Hand(hand);
      matchInfo.setActive(true);
      matchInfoMapper.insertMatchInfo(matchInfo);
    } else {
      match.setUser1(loginId);
      match.setUser2(id);
      match.setUser1Hand(hand);
      match.setUser2Hand(activeMatch.get(0).getUser1Hand());
      match.setActive(true);
      this.kekka.syncFinishMatch(match);
      matchInfoMapper.updateById(activeMatch.get(0).getId());
    }

    return "wait.html";
  }

  @GetMapping("/result")
  public SseEmitter result() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.kekka.asyncShowResult(sseEmitter);
    return sseEmitter;
  }

  @GetMapping("/jankengame")
  public String buttle(@RequestParam String hand, ModelMap model) {
    String player = "";
    String result = "";
    if (hand.equals("g")) {
      player = "グー";
      result = "Draw!";
    } else if (hand.equals("c")) {
      player = "チョキ";
      result = "You Lose!";
    } else if (hand.equals("p")) {
      player = "パー";
      result = "You Win!";
    }
    model.addAttribute("player", "あなたの手: " + player);
    model.addAttribute("cpu", "相手の手: グー");
    model.addAttribute("result", "結果: " + result);
    return "janken.html";
  }

  @PostMapping("/janken")
  public String janken(@RequestParam String user, ModelMap model) {
    model.addAttribute("user", user);
    return "janken.html";
  }
}
