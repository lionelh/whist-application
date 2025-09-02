package be.lionelh.whist.score.backend.utils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui")
public class AngularUIController {

    @GetMapping("{[path:[^\\.]*}")
    String index() {
        return "forward:/ui/index.html";
    }
}