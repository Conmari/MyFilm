package scari.corp.MyFilm.repo;

import org.springframework.data.repository.CrudRepository;
import scari.corp.MyFilm.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
