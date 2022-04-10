package kwy.demo.parser.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kwy.demo.parser.client.OpenApiClient;
import kwy.demo.parser.model.ApiResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressParser {

  private static final String SPECIAL_CHARS = "[^\uAC00-\uD7A3\\s0-9a-zA-Z]";   // 한글, 숫자, 띄어쓰기, 영어를 제외한 모든 문자는 특수문자로 취급함.
  private static final List<String> END_SUFFIXES = List.of("((?<=\uAE38))", "((?<=\uB85C))"); // 길, 로

  private static AddressParser addressParser;

  public static AddressParser getInstance() {
    if (addressParser == null) {
      addressParser = new AddressParser();
    }
    return addressParser;
  }

  private String makeValidString(String text) {
    return text.replaceAll(SPECIAL_CHARS, "");
  }

  public Optional<String> parseRnName(String text) {
    String specialCharsRemoved = makeValidString(text);
    for (String suffix : END_SUFFIXES) {
      List<String> splitWithSuffix = splitWithSuffix(suffix, specialCharsRemoved);

      for (String requestParam : splitWithSuffix) {
        ApiResponse apiResponse = null;
        do {
          apiResponse = OpenApiClient.get(requestParam, apiResponse == null ? 1 : apiResponse.getNextPage());
          if (apiResponse != null && apiResponse.hasResults()) {
            Optional<String> name = apiResponse.getRoadName(requestParam);
            if (name.isPresent()) {
              return Optional.of(name.get());
            }
          }
        } while (apiResponse != null && apiResponse.hasNextPage());
      }
    }
    return Optional.empty();
  }

  private List<String> splitWithSuffix(String match, String text) {
    return Arrays.stream(text.split(match))
        .map(item -> item.replaceAll(" ", ""))
        .collect(Collectors.toList());
  }
}
