package com.umu.prompts.infrastructure.api.rest.dto.error;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemDetail {

    @Size(max = 500)
    private String title;

    @Min(400)
    @Max(600)
    private Integer status;

    @Size(max = 5000)
    private String detail;

    @Size(max = 2000)
    private String instance;

    @Builder.Default
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnySetter
    public ProblemDetail putAdditionalProperty(String key, Object value) {
        this.additionalProperties.put(key, value);
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public Object getAdditionalProperty(String key) {
        return this.additionalProperties.get(key);
    }
}

