package univer.university.dto.request;

import lombok.Getter;
import lombok.Setter;
import univer.university.entity.Category;

@Getter
@Setter
public class ReqSubCategory {
    private String name;
    private Long categoryId;
}
