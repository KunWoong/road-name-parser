package kwy.demo.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import kwy.demo.parser.service.AddressParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ApplicationTest {

  static AddressParser addressParser;

  @BeforeAll
  static void setUp() {
    addressParser = AddressParser.getInstance();
  }

  @Test
  void inputWithValidRoadNameIncludingAnotherDelimiter() {
    String input = "성남, 분당 백 현 로 265, 푸른마을 아파트로 보내주세요!!";
    Optional<String> result = addressParser.parseRnName(input);

    assertAll(
        () -> assertTrue(result.isPresent()),
        () -> assertEquals("백현로", result.get())
    );
  }

  @Test
  void inputWithValidRoadName() {
    String input = "마포구 도화-2길 코끼리분식";
    Optional<String> result = addressParser.parseRnName(input);

    assertAll(
        () -> assertTrue(result.isPresent()),
        () -> assertEquals("도화2길", result.get())
    );
  }

  @Test
  void inputWithInvalidText() {
    String input = "가나다라마바사 아자차카타파하";
    Optional<String> result = addressParser.parseRnName(input);

    assertTrue(result.isEmpty());
  }

}
