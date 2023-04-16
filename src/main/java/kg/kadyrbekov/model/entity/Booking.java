package kg.kadyrbekov.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.enums.Night;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date showDate;

    private LocalDateTime froms;

    private LocalDateTime bto;

    private String response;

    private double totalPrice;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cabin_id")
    @JsonIgnore
    private Cabin cabin;
    @Transient
    private Long cabinId;

    @Enumerated(EnumType.STRING)
    private Night night;

    @OneToOne
    @JoinColumn(name = "computer_id")
    private Computer computer;
    @Transient
    private Long computerId;

    @OneToMany(mappedBy = "booking")
    @JsonIgnore
    private List<OrderHistory> orderHistories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Transient
    private Long userId;

    @OneToOne
    @JoinColumn(name = "volleyball_id")
    private Volleyball volleyball;
    @Transient
    private Long gymId;

    @OneToOne
    @JoinColumn(name = "turf_Id")
    private Turf turf;
    @Transient
    private Long turfId;

}
