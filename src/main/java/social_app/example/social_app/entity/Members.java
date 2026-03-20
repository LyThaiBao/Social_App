    package social_app.example.social_app.entity;

    import jakarta.persistence.*;
    import lombok.*;

    import java.time.LocalDate;

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
    }
