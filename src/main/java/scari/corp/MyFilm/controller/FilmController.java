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
import java.util.List;
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
    public String ViewActualInformationFilm(Model model) {
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
    @GetMapping("/onefilm/{id}") /*Обновление(Редактирование фильма)*/
    public String showUpdateFormFilm(@PathVariable("id") long id, Model model) {
        Film film = filmRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("film", film);
        return "film/update-film";
    }
    @GetMapping("/film/{id}") /*Просмотр конкретного id*/
    public String showFormFilm(@PathVariable("id") long id, Model model) {
        Film film = filmRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("film", film);
        return "film/onefilm";
    }

    @GetMapping("/addfilm")
    public String addFilm(Model model) {
        return "film/addfilm";
    }

    @GetMapping("/pravila")
    public String pravila(Model model) {
        return "rules/pravila";
    }

    @PostMapping("/addfilm")
    public String postAddFilm(
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
    public String updateFilm(@PathVariable("id") long id, @Valid Film film,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            film.setId(id);
            return "film/update-film";
        }

        filmRepo.save(film);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteFilm(@PathVariable("id") long id, Model model) {
        Film film = filmRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        filmRepo.delete(film);
        return "redirect:/";
    }

    @RequestMapping(path = {"/","/search"})
    public String searchFilm(Film film, Model model, String keyword) {
        if(keyword!=null) {
            List<Film> list = filmRepo.findByKeyword(keyword);
            model.addAttribute("list", list);
        }else {
            List<Film> list = filmRepo.findAllFilm();
            model.addAttribute("list", list);}
        return "search/search";
    }
}
