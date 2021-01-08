package pl.javastart.thymeleafformularze;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //kontroler strony głównej

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("user", new User()); // do modelu dodaje obiekt klasy user

        return "home"; //home.html
    }
}
