package oit.is.z1412.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.ModelMap;
// import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class JankenController {

  @GetMapping("/janken.html")
  public String janken() {
    return "janken.html";
  }

  /* @GetMapping("/janken.html/{hand}")
  public String buttle(@PathVariable String hand, ModelMap model) {
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
  } */

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

  @PostMapping("/janken.html")
  public String janken(@RequestParam String user, ModelMap model) {
    model.addAttribute("welcome", "Hi " + user);
    return "janken.html";
  }
}
