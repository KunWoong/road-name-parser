package kwy.demo.parser.client;

import static org.junit.jupiter.api.Assertions.*;

import kwy.demo.parser.model.ApiResponse;
import org.junit.jupiter.api.Test;

class OpenApiClientTest {

  @Test
  void validParameter() {
    ApiResponse apiResponse = OpenApiClient.get("송파대로44길", 1);

    assertAll(
        () -> assertNotNull(apiResponse),
        () -> assertTrue(apiResponse.hasResults())
    );
  }

  @Test
  void invalidParameter() {
    ApiResponse apiResponse = OpenApiClient.get("서울시", 1);

    assertAll(
        () -> assertNotNull(apiResponse),
        () -> assertEquals("주소를 상세히 입력해 주시기 바랍니다.", apiResponse.getErrorMessage())
    );
  }
}
