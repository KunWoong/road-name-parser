package kwy.demo.parser.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ApiResponseTest {

  @Test
  void getRnWithNullAddresses() {
    ApiResponse apiResponse = ApiResponse.builder()
        .addresses(
            List.of(
                Address.builder().rn("긴고랑로").build(),
                Address.builder().rn("긴고랑로").build(),
                Address.builder().rn("긴고랑로").build(),
                Address.builder().rn("짧은고랑로").build()
            )
        )
        .build();
    Optional<String> roadName = apiResponse.getRoadName("엉뚱한길");

    assertTrue(roadName.isEmpty());
  }

  @Test
  void getRnWithAddresses() {
    ApiResponse apiResponse = ApiResponse.builder()
        .addresses(
            List.of(
                Address.builder().rn("긴고랑로").build(),
                Address.builder().rn("긴고랑로").build(),
                Address.builder().rn("긴고랑로").build(),
                Address.builder().rn("짧은고랑로").build()
            )
        )
        .build();
    Optional<String> roadName = apiResponse.getRoadName("서울시광진구짧은고랑로");

    assertAll(
        () -> assertTrue(roadName.isPresent()),
        () -> assertEquals("짧은고랑로", roadName.get())
    );
  }

}
