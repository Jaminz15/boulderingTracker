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

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "cognito_sub", unique = true)
    private String cognitoSub;

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
     * @param email      the email
     * @param username   the username
     * @param cognitoSub the cognito sub
     */
    public User(String email, String username, String cognitoSub) {
        this.email = email;
        this.username = username;
        this.cognitoSub = cognitoSub;
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
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets cognito sub.
     *
     * @return the cognito sub
     */
    public String getCognitoSub() {
        return cognitoSub;
    }

    /**
     * Sets cognito sub.
     *
     * @param cognitoSub the cognito sub
     */
    public void setCognitoSub(String cognitoSub) {
        this.cognitoSub = cognitoSub;
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
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", cognitoSub='" + cognitoSub + '\'' +
                ", createdAt=" + createdAt +
                ", climbs=" + climbs +
                '}';
    }
}