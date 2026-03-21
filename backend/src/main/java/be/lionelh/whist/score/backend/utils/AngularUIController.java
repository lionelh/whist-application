package be.lionelh.whist.score.backend.utils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * This controller is used to forward all requests to the angular app, except those containing a dot (.) which are considered as requests for static resources (js, css, images, etc...).
 */
@Controller
@RequestMapping("/ui")
public class AngularUIController {

    @GetMapping("{[path:[^\\.]*}")
    String index() {
        return "forward:/ui/index.html";
    }
}