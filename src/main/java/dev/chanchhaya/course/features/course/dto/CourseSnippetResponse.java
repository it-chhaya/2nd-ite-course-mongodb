package dev.chanchhaya.course.features.course.dto;

import lombok.Builder;
import org.bson.types.Decimal128;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record CourseSnippetResponse(String id,
                                    String uuid,
                                    String title,
                                    String slug,
                                    String description,
                                    Decimal128 price,
                                    Integer discount,
                                    Boolean isPaid,
                                    Boolean isDrafted,
                                    String thumbnail,
                                    String categoryName,
                                    String instructorUsername,
                                    LocalDateTime updatedAt) {
}
