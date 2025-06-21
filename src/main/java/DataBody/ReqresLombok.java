package DataBody;

import lombok.*;
import lombok.Data;

@Data
@Getter
@Builder
@Setter
@ToString
public class ReqresLombok {
    String name;
    String salary;
    String age;
}
