package DataBody;

import lombok.*;
import lombok.extern.jackson.Jacksonized;


@Jacksonized
@Builder
@ToString
@Setter
@Getter
@lombok.Data
public class Device {

    String id;

    String name;

    DataPojo data;

    String createdAt;

}


