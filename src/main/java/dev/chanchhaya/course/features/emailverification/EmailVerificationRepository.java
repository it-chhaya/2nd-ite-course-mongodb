package dev.chanchhaya.course.features.emailverification;


import dev.chanchhaya.course.domain.EmailVerification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmailVerificationRepository extends MongoRepository<EmailVerification, Integer> {

    Optional<EmailVerification> findByUserId(String userId);

}
