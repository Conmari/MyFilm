package scari.corp.MyFilm.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scari.corp.MyFilm.domain.Film;
import scari.corp.MyFilm.repo.FilmRepo;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class FilmController {
    private final FilmRepo filmRepo;

    @Autowired
    public FilmController(FilmRepo filmRepo) {
        this.filmRepo = filmRepo;
    }


    @Value("${upload.path}")
    private String uploadPath;
    @Value("${upload.path_film}")
    private String uploadPathFilm;

    @GetMapping("/")
    public String ViewActualInformationS(Model model) {
        Iterable<Film> Film = filmRepo.findAll();
        model.addAttribute("Film", Film);
        return "index";
    }
    @GetMapping("/film")
    public String ViewActualInformation(Model model) {
        Iterable<Film> Film = filmRepo.findAll();
        model.addAttribute("Film", Film);
        return "index";
    }
    @GetMapping("/film/{id}") /*Обновление(Редактирование фильма)*/
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Film film = filmRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("film", film);
        return "update-film";
    }
    @GetMapping("/onefilm/{id}") /*Обновление*/
    public String showUpdateForm1(@PathVariable("id") long id, Model model) {
        Film film = filmRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("film", film);
        return "onefilm";
    }

    @GetMapping("/addfilm")
    public String add(Model model) {
        return "addfilm";
    }
    @GetMapping("/pravila")
    public String pravila(Model model) {
        return "pravila";
    }

    @PostMapping("/addfilm")
    public String postAdd(
            @RequestParam String film_name,
            @RequestParam String genre,
            @RequestParam String full_text,
            @RequestParam("file") MultipartFile file,
            @RequestParam("file_video") MultipartFile file_video,
            Model model
    ) throws IOException {
        Film post = new Film(film_name, genre, full_text);
        if(file != null || file_video != null){
            File uploadDir = new File(uploadPath);
            File uploadDirVideo = new File(uploadPathFilm);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            if (!uploadDirVideo.exists()){
                uploadDirVideo.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String uuidVideo = UUID.randomUUID().toString();

            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            String resultVideoname = uuidVideo + "." + file_video.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));
            file_video.transferTo(new File(uploadPathFilm + "/" + resultVideoname));

            post.setFilename(resultFilename);
            post.setFilm(resultVideoname);
        }
        filmRepo.save(post);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Film film,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            film.setId(id);
            return "update-film";
        }

        filmRepo.save(film);
        return "redirect:/film";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Film film = filmRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        filmRepo.delete(film);
        return "redirect:/film";
    }
}
