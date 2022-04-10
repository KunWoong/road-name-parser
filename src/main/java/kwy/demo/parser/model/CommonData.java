package kwy.demo.parser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CommonData {
  String errorMessage;
  int countPerPage;
  int totalCount;
  String errorCode;
  int currentPage;
}
