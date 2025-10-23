package univer.university.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqSubCategory {
    private Long categoryId;
    private List<String> subCategories;
}
