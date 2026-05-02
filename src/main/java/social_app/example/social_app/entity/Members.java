    package social_app.example.social_app.entity;

    import jakarta.persistence.*;
    import lombok.*;
    import org.hibernate.annotations.CreationTimestamp;

    import java.time.Instant;
    import java.time.LocalDate;
    import java.util.List;

    @Entity
    @Table(name = "members")
    @NoArgsConstructor
    @AllArgsConstructor
    @Data @Builder
    public class Members {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer id;

        @Column(name = "full_name")
        private String fullName;

        @Column(name = "birthday")
        private LocalDate birthDay;

        @ToString.Exclude
        @OneToOne
        @JoinColumn(name = "user_id",unique = true)
        private Users user;

        @OneToMany(mappedBy = "recipient")
        @ToString.Exclude
        private List<Notification> notificationList;

        @CreationTimestamp
        @Column(name = "create_at",nullable = false)
        private Instant createAt;
    }
