package org.tj.tjmovies.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tj.tjmovies.Entity.Movies;
import org.tj.tjmovies.Entity.Users;

public interface UserDAO extends JpaRepository<Users, String> {
}
