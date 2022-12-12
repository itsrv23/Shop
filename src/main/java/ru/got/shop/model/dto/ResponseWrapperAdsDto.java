package ru.got.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ResponseWrapperAds
 */
@lombok.AllArgsConstructor
@lombok.Builder
@lombok.NoArgsConstructor
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ResponseWrapperAdsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("count")
    private Integer count;

    @JsonProperty("results")
    @Valid
    private List<AdDto> results = null;

    public ResponseWrapperAdsDto count(Integer count) {
        this.count = count;
        return this;
    }

    /**
     * Get count
     *
     * @return count
     */

    @Schema(name = "count", required = false)
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ResponseWrapperAdsDto results(List<AdDto> results) {
        this.results = results;
        return this;
    }

    public ResponseWrapperAdsDto addResultsItem(AdDto resultsItem) {
        if (this.results == null) {
            this.results = new ArrayList<>();
        }
        this.results.add(resultsItem);
        return this;
    }

    /**
     * Get results
     *
     * @return results
     */
    @Valid
    @Schema(name = "results", required = false)
    public List<AdDto> getResults() {
        return results;
    }

    public void setResults(List<AdDto> results) {
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponseWrapperAdsDto responseWrapperAdsDto = (ResponseWrapperAdsDto) o;
        return Objects.equals(this.count, responseWrapperAdsDto.count) &&
                Objects.equals(this.results, responseWrapperAdsDto.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, results);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ResponseWrapperAds {\n");
        sb.append("    count: ").append(toIndentedString(count)).append("\n");
        sb.append("    results: ").append(toIndentedString(results)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

