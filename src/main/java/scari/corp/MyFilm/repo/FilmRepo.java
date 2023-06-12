package scari.corp.MyFilm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import scari.corp.MyFilm.domain.Film;

import java.util.Collection;
import java.util.List;

@Repository
public interface FilmRepo extends JpaRepository<Film, Long>{

    /*  Может подчеркивать ошибку, но работает корректно*/
    @Query(value = "SELECT * FROM film u WHERE u.film_name LIKE %:keyword% OR u.genre LIKE %:keyword%" ,
            nativeQuery = true)

    List<Film> findByKeyword(@Param("keyword") String keyword);

    @Query(
            value = "SELECT * FROM film u WHERE   u.film_name LIKE '%'",
            nativeQuery = true)
    List<Film> findAllActiveUsersNative();

}
