package kwy.demo.parser.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.nio.charset.StandardCharsets;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JsonBodyHandler<M> implements HttpResponse.BodyHandler<M> {

  private final Class<M> model;

  @Override
  public BodySubscriber<M> apply(HttpResponse.ResponseInfo responseInfo) {
    return asJSON(model);
  }

  private <T> BodySubscriber<T> asJSON(Class<T> clazz) {
    BodySubscriber<String> upstream = BodySubscribers
        .ofString(StandardCharsets.UTF_8);

    return BodySubscribers.mapping(
        upstream,
        (String body) -> {
          try {
            ObjectReader reader = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readerFor(clazz)
                .withRootName("results");
            return reader.readValue(body);
          } catch (IOException e) {
            throw new UncheckedIOException(e);
          }
        });
  }
}
