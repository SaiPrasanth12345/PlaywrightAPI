package DataBody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonProperty;
import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@ToString
public class DataPojo {

    @JsonPropertyOrder({
            "year",
            "price",
            "CPU_model",
            "Hard disk size"
    })

    @JsonProperty("year")
    Integer year;

    @JsonProperty("price")
    Double price;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonProperty("CPU_model")
    String CPU_model;

    @JsonProperty("Hard disk size")
    String size;
}
