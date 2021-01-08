package pl.javastart.thymeleafformularze;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    //kontroler strony głównej

    private List<User> users;

    public HomeController() { //w ramach konstruktora dodamy kilku użytkowniko do listy
        users = new ArrayList<>();
        users.add(new User(1L, "Damian", "Kowalski"));
        users.add(new User(2L, "Robert", "Zowalski"));
        users.add(new User(3L, "Andrzej", "Mowalski"));
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("user", new User()); // do modelu dodaje obiekt klasy user
        model.addAttribute("users", users); //przekazuje liste wszystkich użytkowników do wyświetlenia
        return "home"; //home.html
    }

    @PostMapping("/dodaj")
    public String addUser(User user) {
        long maxId = 1;
        for (User user1 : users) {
            if (user1.getId() > maxId) { //jezeli id jest mniejsze od max id to przypisz je do max id
                maxId = user1.getId();
            }
        }

        user.setId(maxId + 1); // wstaw to przypisane maxid i zwieksz o 1
        users.add(user); // doda uzupełnionego użytkownika
        return "redirect:/"; //przekierowanie na stronę główną
    }

    //nacisnięcie linku edytuj to metoda get
    //wyświetlanie formularza z edycją osoby
    @GetMapping("/edit")
    public String editForm(@RequestParam Long id, Model model) {  //za pomocą @RequestParam możemy pobrać identyfikator
        for (User user : users) { // jeżeli id z listy zgadza si e z id podany to dodajemy użytkonika do modelu
            if (user.getId().equals(id)) {
                model.addAttribute("user", user); // przekażemy znalezionego użytkonika
                return "edit"; //zwracamy szablon
            }

        }
        return "redirect:/"; // jeśli nie znajdzie uzytkownika to zrobi przekierowanie na stronę główną, lepiej byłoby z komunikatem
    }

    //edystowanie uzytkownika
    @PostMapping("/edit")
    public String edit(User user) {  // dostajemy uzupełnionego użytkownika z formularza edycji
        for (User user1 : users) { // jeżeli id z listy zgadza si e z id podany to dodajemy użytkonika do modelu
            if (user1.getId().equals(user.getId())) { // jeśli ma takie samo id jak user wysłany to ustawimy mu dane jak userowi nadesłanemu
                user1.setFirstName(user.getFirstName());
                user1.setLastName(user.getLastName());
            }
        }
        return "redirect:/";
    }
}
