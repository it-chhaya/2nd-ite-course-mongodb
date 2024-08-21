package dev.chanchhaya.course.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "categoryDB")
public class Category {

    private String id;
    private String uuid;
    private String name;
    private String icon;
    private Boolean isDeleted;

}
