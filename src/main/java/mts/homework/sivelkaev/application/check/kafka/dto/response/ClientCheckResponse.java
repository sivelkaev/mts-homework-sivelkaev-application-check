package mts.homework.sivelkaev.application.check.kafka.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientCheckResponse {
    @JsonProperty(value = "applicationId", required = true)
    private Long applicationId;

    @JsonProperty(value = "clientId")
    private Long clientId;

    @JsonProperty(value = "status", required = true)
    private Integer status;

    @JsonProperty(value = "statusDesc")
    private String statusDesc;
}
