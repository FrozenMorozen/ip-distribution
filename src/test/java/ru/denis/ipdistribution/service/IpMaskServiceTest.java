package ru.denis.ipdistribution.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.denis.ipdistribution.service.impl.IpMaskServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IpMaskServiceTest {

  private IpMaskService ipMaskService = new IpMaskServiceImpl();

  @ParameterizedTest
  @MethodSource("wrongIpDataProvider")
  @DisplayName("IpMaskService.getNetworkMaskFromIp(...): тест с НЕвалидными параметрами")
  void getNetworkMaskFromWrongIp(String ip) {
    assertThrows(Exception.class, () -> ipMaskService.getNetworkMaskFromIp(ip));
  }

  @ParameterizedTest
  @MethodSource("correctIpDataProvider")
  @DisplayName("IpMaskService.getNetworkMaskFromIp(...): тест с корректнми параметрами")
  void getNetworkMaskFromCorrectIp(String ip, String expected) {
    assertEquals(ipMaskService.getNetworkMaskFromIp(ip), expected);
  }

  @ParameterizedTest
  @MethodSource("wrongIpWithAddBitsDataProvider")
  @DisplayName("IpMaskService.addBitsToIp(...): тест с НЕвалидными параметрами")
  void addBitsToWrongIp(String ip, int bitForAdd) {
    assertThrows(Exception.class, () -> ipMaskService.addBitsToIp(ip, bitForAdd));
  }

  @ParameterizedTest
  @MethodSource("correctIpWithAddBitsDataProvider")
  @DisplayName("IpMaskService.addBitsToIp(...): тест с корректными параметрами")
  void addBitsToCorrectIp(String ip, int bitForAdd, String expected) {
    assertEquals(ipMaskService.addBitsToIp(ip, bitForAdd), expected);
  }

  private static Stream<Arguments> wrongIpDataProvider() {
    return Stream.of(
            Arguments.of("smth string"),
            Arguments.of("172.28."),
            Arguments.of("172.28.0.5.7")
    );
  }

  private static Stream<Arguments> correctIpDataProvider() {
    return Stream.of(
            Arguments.of("255.255.255.252", "/30"),
            Arguments.of("255.255.0.0", "/16")
    );
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