package scari.corp.MyFilm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import scari.corp.MyFilm.domain.Film;

public interface FilmRepo extends JpaRepository<Film, Long>{
}
