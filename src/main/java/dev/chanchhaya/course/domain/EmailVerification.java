package dev.chanchhaya.course.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "emailVerificationDB")
@Builder
@ToString
public class EmailVerification {

    @Id
    private String id;

    private String userId;

    private LocalTime expiryTime;

    private String verificationCode;

}
