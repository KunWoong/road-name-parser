package kwy.demo.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.logging.Logger;
import kwy.demo.parser.service.AddressParser;

public class Application {

  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private static final String QUIT = "quit";
  private static final String NO_ANSWER = "적절한 도로명을 찾을 수 없습니다.";
  private static final String INPUT__FORM = "입력 : ";
  private static final String OUTPUT_FORMAT = "출력 : %s%n\n";

  public static void main(String[] args) {
    Application application = new Application();
    Runtime.getRuntime().addShutdownHook(new Thread(application::closeAll));

    AddressParser addressParser = AddressParser.getInstance();

    System.out.printf("%s을 입력하여 종료할 수 있습니다.%n", QUIT);
    while(true) {
      try {
        System.out.print(INPUT__FORM);
        String input = br.readLine().replaceAll("\n", "");

        if (QUIT.equals(input)) break;

        Optional<String> result = addressParser.parseRnName(input);
        result.ifPresentOrElse(
            application::printResult,
            () -> {
              application.printResult(NO_ANSWER);
            }
        );
      } catch (IOException ignored) {

      }
    }
  }

  private void closeAll() {
    System.out.println("프로세스를 종료합니다.");
    while (true) {
      try {
        br.close();
        return;
      } catch (IOException ignored) {}
    }
  }

  private void printResult(String result) {
    System.out.printf(OUTPUT_FORMAT, result);
  }
}
