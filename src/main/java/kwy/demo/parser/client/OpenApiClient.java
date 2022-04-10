package kwy.demo.parser.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import kwy.demo.parser.model.ApiResponse;
import kwy.demo.parser.util.JsonBodyHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenApiClient {

  private static final HttpClient client = HttpClient.newHttpClient();
  private static final String BASE_URL = "https://www.juso.go.kr/addrlink/addrLinkApi.do?"
      + "confmKey=devU01TX0FVVEgyMDIyMDQxMDE3MDc1OTExMjQ0NDM="
      + "&resultType=json"
      + "&countPerPage=100"
      + "&keyword=%s"
      + "&currentPage=%d";

  public static ApiResponse get(String requestParam, int page) {
    URI uri = URI.create(String.format(BASE_URL, requestParam, page));
    HttpRequest request = HttpRequest.newBuilder(uri)
        .header("Accept", "application/json")
        .GET()
        .build();
    try {
      return client.send(request, new JsonBodyHandler<>(ApiResponse.class)).body();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
