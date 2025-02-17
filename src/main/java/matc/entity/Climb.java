package matc.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDate;

/**
 * A class to represent a Climb.
 */
@Entity
@Table(name = "climb")
public class Climb {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @ManyToOne
    @JoinColumn(name = "gym_id", nullable = false)
    private Gym gym;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "climb_type", nullable = false)
    private String climbType;

    @Column(name = "grade", nullable = false)
    private String grade;

    @Column(name = "attempts", nullable = false)
    private int attempts;

    @Column(name = "success", nullable = false)
    private boolean success;

    @Column(name = "notes")
    private String notes;
}
