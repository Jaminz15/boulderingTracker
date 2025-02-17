package matc.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.Table;
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
}

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