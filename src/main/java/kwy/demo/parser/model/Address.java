package kwy.demo.parser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
class Address {
  String rn;

  public boolean subStringOf(String requestAddress) {
    return requestAddress.contains(rn);
  }
}
