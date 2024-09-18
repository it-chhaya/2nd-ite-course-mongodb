package dev.chanchhaya.course.features.course.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.bson.types.Decimal128;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CourseResponse(String id,
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
                             @JsonInclude(JsonInclude.Include.NON_NULL)
                             List<SectionDto> sections,
                             LocalDateTime updatedAt) {
}
