package dev.chanchhaya.course.base;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasedError<T> {

    // Request Entity Too Large, Bad Request, ...
    // 7003
    private String code;

    // Detail error for user
    private T description;

}
