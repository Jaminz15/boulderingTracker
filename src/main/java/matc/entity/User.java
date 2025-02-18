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

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets email or username.
     *
     * @return the email or username
     */
    public String getEmailOrUsername() {
        return emailOrUsername;
    }

    /**
     * Sets email or username.
     *
     * @param emailOrUsername the email or username
     */
    public void setEmailOrUsername(String emailOrUsername) {
        this.emailOrUsername = emailOrUsername;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets climbs.
     *
     * @return the climbs
     */
    public List<Climb> getClimbs() {
        return climbs;
    }

    /**
     * Sets climbs.
     *
     * @param climbs the climbs
     */
    public void setClimbs(List<Climb> climbs) {
        this.climbs = climbs;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", emailOrUsername='" + emailOrUsername + '\'' +
                ", createdAt=" + createdAt +
                ", climbs=" + climbs +
                '}';
    }
}