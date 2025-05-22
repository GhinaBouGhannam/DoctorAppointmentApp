package com.example.springboot.repository;

//import com.example.springboot.hibernateUtil;
import com.example.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u WHERE u.user_name = :userName AND u.password = :password")
    Optional<User> findByUserNameAndPassword(@Param("userName") String name, @Param("password") String password);
    @Query("SELECT u FROM User u WHERE u.user_name = :name")
    Optional<User> findByUserName(@Param("name") String name);
    @Query("SELECT u FROM User u WHERE u.user_id = :id")
    User getUserById(@Param("id") Long id);


    @Query("SELECT COUNT(u) FROM User u WHERE u.user_name = :name")
    int countByUserName(@Param("name")String name);


  /*  @Query(name = "SELECT * FROM User WHERE user_name = ?1 AND password = ?2", nativeQuery = true)
    User authenticateUser(String username, String password);*/
}




