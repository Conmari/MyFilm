//package scari.corp.MyFilm.controller;
//
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import scari.corp.MyFilm.domain.User;
//import scari.corp.MyFilm.repo.UserRepository;
//
//@Controller
//public class UserController {
//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//    @GetMapping("/signup")
//    public String showSignUpForm(User user) {
//        return "add-user";
//    }
//
//    @PostMapping("/adduser")
//    public String addUser(@Valid User user, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "add-user";
//        }
//        userRepository.save(user);
//
//        long id = user.getId();
//        return "redirect:/info/"+ id;
//    }
//
//    @GetMapping("/edituser/{id}")
//    public String showUpdateForm(@PathVariable("id") long id, Model model) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//
//        model.addAttribute("user", user);
//        return "update-user";
//    }
//    @PostMapping("/updateuser/{id}")
//    public String updateUser(@PathVariable("id") long id, @Valid User user,
//                             BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            user.setId(id);
//            return "update-user";
//        }
//
//        userRepository.save(user);
//        return "redirect:/index1";
//    }
//
//    @GetMapping("/deleteuser/{id}")
//    public String deleteUser(@PathVariable("id") long id, Model model) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//        userRepository.delete(user);
//        return "redirect:/index1";
//    }
//
//    @GetMapping("/info/{id}") /*Обновление(Редактирование фильма)*/
//    public String infoUserPageFor1m(@PathVariable("id") long id, Model model) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//
//        model.addAttribute("user", user);
//        return "index1";
//    }
//    @GetMapping("/userpage/{id}") /*Обновление(Редактирование фильма)*/
//    public String infoUserPageForm(@PathVariable("id") long id, Model model) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//
//        model.addAttribute("user", user);
//        return "userpage";
//    }
//    // additional CRUD methods
//}