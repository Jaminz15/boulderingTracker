package matc.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;

/**
 * A class to represent a Gym.
 */
@Entity
@Table(name = "gym")
public class Gym {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Instantiates a new Gym.
     */
    public Gym() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Instantiates a new Gym.
     *
     * @param name     the name of the gym
     * @param location the location of the gym
     */
    public Gym(String name, String location) {
        this.name = name;
        this.location = location;
        this.createdAt = LocalDateTime.now();
    }

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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
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

    @Override
    public String toString() {
        return "Gym{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

