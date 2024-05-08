package blps.labs.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "review")
@NoArgsConstructor
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotEmpty
    private String authorName;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @NotEmpty
    private String reviewText;

    @NotEmpty
    private String pros;

    @NotEmpty
    private String cons;

    @NotEmpty
    private String advice;

    @NotNull
    private boolean approved = false;

    public Review(String authorName, String reviewText, String pros, String cons, String advice, boolean approved, Car car) {
        this.authorName = authorName;
        this.reviewText = reviewText;
        this.pros = pros;
        this.cons = cons;
        this.advice = advice;
        this.approved = approved;
        this.car = car;
    }

}
