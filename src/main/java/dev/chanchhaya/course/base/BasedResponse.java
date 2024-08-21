package dev.chanchhaya.course.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasedResponse<T> {

    private T payload;

    private T errors;

    private Object metadata;

}
