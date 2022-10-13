package oit.is.z1412.kaizi.janken.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.ModelMap;

import oit.is.z1412.kaizi.janken.model.Entry;

@Controller
public class JankenController {

  @Autowired
  private Entry entry;

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);
    model.addAttribute("user", loginUser);
    return "janken.html";
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
