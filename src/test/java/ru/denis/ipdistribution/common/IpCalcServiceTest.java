package ru.denis.ipdistribution.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.denis.ipdistribution.common.impl.IpCalcServiceImpl;
import ru.denis.ipdistribution.common.service.IpCalcService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IpCalcServiceTest {

  private IpCalcService ipCalcService = new IpCalcServiceImpl();

  @ParameterizedTest
  @MethodSource("wrongIpWithAddBitsDataProvider")
  @DisplayName("IpMaskService.addBitsToIp(...): тест с НЕвалидными параметрами")
  void addBitsToWrongIp(String ip, int bitForAdd) {
    assertThrows(Exception.class, () -> ipCalcService.addBitsToIp(ip, bitForAdd));
  }

  @ParameterizedTest
  @MethodSource("correctIpWithAddBitsDataProvider")
  @DisplayName("IpMaskService.addBitsToIp(...): тест с корректными параметрами")
  void addBitsToCorrectIp(String ip, int bitForAdd, String expected) {
    assertEquals(ipCalcService.addBitsToIp(ip, bitForAdd), expected);
  }

  private static Stream<Arguments> wrongIpWithAddBitsDataProvider() {
    return Stream.of(
            Arguments.of("smth string", 5),
            Arguments.of("172.28.", 4),
            Arguments.of("172.28.0.5.7", 8)
    );
  }

  private static Stream<Arguments> correctIpWithAddBitsDataProvider() {
    return Stream.of(
            Arguments.of("172.28.0.1", 5, "172.28.0.6"),
            Arguments.of("172.255.255.255", 4, "173.0.0.3"),
            Arguments.of("172.28.0.5", 1, "172.28.0.6")
    );
  }

}