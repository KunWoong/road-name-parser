package kwy.demo.parser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
  private CommonData common;

  @JsonProperty("juso")
  private List<Address> addresses;


  public Optional<String> getRoadName(String requestRoad) {
    return Optional.ofNullable(addresses)
        .orElse(Collections.emptyList())
        .stream()
        .filter(address -> address.subStringOf(requestRoad))
        .map(Address::getRn)
        .findFirst();
  }
  public String getErrorMessage() {
    return Optional.ofNullable(common)
        .map(CommonData::getErrorMessage)
        .orElse(null);
  }
  public boolean hasResults() {
    return common.totalCount > 0;
  }

  public boolean hasNextPage() {
    return common.totalCount > common.currentPage * common.countPerPage;
  }

  public int getNextPage() {
    return common.currentPage + 1;
  }
}
