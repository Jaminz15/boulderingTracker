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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Link climb to user
    private User user;

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

    /**
     * Instantiates a new Climb.
     */
    public Climb() {
    }

    /**
     * Instantiates a new Climb.
     *
     * @param gym       the gym where the climb took place
     * @param user      the user who logged the climb
     * @param date      the date of the climb
     * @param climbType the type of climb (e.g., Slab, Overhang)
     * @param grade     the grade of the climb
     * @param attempts  number of attempts
     * @param success   whether the climb was completed
     * @param notes     optional notes
     */
    public Climb(Gym gym, User user, LocalDate date, String climbType, String grade, int attempts, boolean success, String notes) {
        this.gym = gym;
        this.user = user;
        this.date = date;
        this.climbType = climbType;
        this.grade = grade;
        this.attempts = attempts;
        this.success = success;
        this.notes = notes;
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
     * Gets gym.
     *
     * @return the gym
     */
    public Gym getGym() {
        return gym;
    }

    /**
     * Sets gym.
     *
     * @param gym the gym
     */
    public void setGym(Gym gym) {
        this.gym = gym;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets climb type.
     *
     * @return the climb type
     */
    public String getClimbType() {
        return climbType;
    }

    /**
     * Sets climb type.
     *
     * @param climbType the climb type
     */
    public void setClimbType(String climbType) {
        this.climbType = climbType;
    }

    /**
     * Gets grade.
     *
     * @return the grade
     */
    public String getGrade() {
        return grade;
    }

    /**
     * Sets grade.
     *
     * @param grade the grade
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * Gets attempts.
     *
     * @return the attempts
     */
    public int getAttempts() {
        return attempts;
    }

    /**
     * Sets attempts.
     *
     * @param attempts the attempts
     */
    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    /**
     * Is success boolean.
     *
     * @return the boolean
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets success.
     *
     * @param success the success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Gets notes.
     *
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets notes.
     *
     * @param notes the notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * toString method for debugging.
     */
    @Override
    public String toString() {
        return "Climb{" +
                "id=" + id +
                ", gym=" + gym.getName() +
                ", user=" + (user != null ? user.getEmailOrUsername() : "Unknown User") +
                ", date=" + date +
                ", climbType='" + climbType + '\'' +
                ", grade='" + grade + '\'' +
                ", attempts=" + attempts +
                ", success=" + success +
                ", notes='" + notes + '\'' +
                '}';
    }
}