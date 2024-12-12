package org.tj.tjmovies.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.tj.tjmovies.Entity.Enum.UserRole;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @Column(name = "userid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private UserRole role;

    @Column(name = "isactive")
    private int isActive;
}
