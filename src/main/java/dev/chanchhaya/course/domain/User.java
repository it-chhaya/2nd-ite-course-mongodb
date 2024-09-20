package dev.chanchhaya.course.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "userDB")
@Builder
@ToString
public class User {

    @Id
    private String id;

    private String password;

    private String email;

    private String biography;
    private String gender;
    private String profile;

    private Boolean isDeleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String role;
    private List<FavoriteCourse> favoriteCourses = new ArrayList<>();

}


