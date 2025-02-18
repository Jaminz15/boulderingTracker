package matc.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;
import java.util.List;

/**
 * A class to represent a User.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Column(name = "email_or_username", nullable = false, unique = true)
    private String emailOrUsername;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Climb> climbs;

    /**
     * Instantiates a new User.
     */
    public User() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Instantiates a new User.
     *
     * @param emailOrUsername the email or username
     */
    public User(String emailOrUsername) {
        this.emailOrUsername = emailOrUsername;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmailOrUsername() {
        return emailOrUsername;
    }

    public void setEmailOrUsername(String emailOrUsername) {
        this.emailOrUsername = emailOrUsername;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Climb> getClimbs() {
        return climbs;
    }

    public void setClimbs(List<Climb> climbs) {
        this.climbs = climbs;
    }
}