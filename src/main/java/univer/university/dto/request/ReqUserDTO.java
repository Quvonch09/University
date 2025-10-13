package univer.university.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReqUserDTO {
    private String fullName;
    private String phone;
    private String email;
    private String imageUrl;
//    private List<Object> infos;
//    private Long categoryId;
}
