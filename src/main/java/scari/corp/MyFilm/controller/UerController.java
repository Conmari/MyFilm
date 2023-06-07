package scari.corp.MyFilm.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import scari.corp.MyFilm.domain.Film;
import scari.corp.MyFilm.domain.Uer;
import scari.corp.MyFilm.repo.FilmRepo;
import scari.corp.MyFilm.repo.UerRepository;

@Controller
public class UerController {
    private final UerRepository uerRepository;

    @Autowired
    public UerController(UerRepository uerRepository) {
        this.uerRepository = uerRepository;
    }
    @GetMapping("/signup")
    public String showSignUpForm(Uer user) {
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid Uer user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }

        uerRepository.save(user);
        return "redirect:/index1";
    }
    @GetMapping("/index1")
    public String showUserList(Model model) {
        model.addAttribute("users", uerRepository.findAll());
        return "index1";
    }
    @GetMapping("/edituser/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Uer user = uerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("uer", user);
        return "update-user";
    }
    @PostMapping("/updateuser/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Uer user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        uerRepository.save(user);
        return "redirect:/index1";
    }

    @GetMapping("/deleteuser/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Uer user = uerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        uerRepository.delete(user);
        return "redirect:/index1";
    }
    // additional CRUD methods
}