    package social_app.example.social_app.entity;

    import jakarta.persistence.*;
    import lombok.*;
    import org.hibernate.annotations.CreationTimestamp;

    import java.time.Instant;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.List;

    @Entity
    @Table(name = "members")
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    @Builder
    public class Members {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer id;

        @Column(name = "full_name")
        private String fullName;

        @Column(name = "birthday")
        private LocalDate birthDay;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id",unique = true)
        @ToString.Exclude
        private Users user;

        @Builder.Default
        @OneToMany(mappedBy = "recipient",fetch = FetchType.LAZY)
        @ToString.Exclude
        private List<Notification> notificationList = new ArrayList<>();

        @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
        @ToString.Exclude
        @Builder.Default
        private List<Posts> postsList = new ArrayList<>();

        @CreationTimestamp // auto create when create
        @Column(name = "create_at",nullable = false)
        private Instant createAt;
    }
