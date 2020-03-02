package ru.denis.ipdistribution.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.denis.ipdistribution.service.IpForDeviceService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IpForDeviceServiceImplTest {

  IpForDeviceService ipForDeviceService = new IpForDeviceServiceImpl(new SubnetServiceImpl());

  @ParameterizedTest
  @MethodSource("correctIpsDataProvider")
  @DisplayName("")
  void getIpForNextDevice(String inputIp, String expectedResult) {
    assertEquals(ipForDeviceService.getIpForNextDevice(inputIp), expectedResult);
  }

  @ParameterizedTest
  @MethodSource("wrongIpsDataProvider")
  @DisplayName("")
  void getIpIPForNextDevice(String inputIp) {
    assertThrows(Exception.class, () -> ipForDeviceService.getIpForNextDevice(inputIp));
  }

  private static Stream<Arguments> correctIpsDataProvider() {
    return Stream.of(
            Arguments.of("172.28.0.1", "172.28.0.5"),
            Arguments.of("172.28.0.5", "172.28.0.9")
    );
  }

  private static Stream<Arguments> wrongIpsDataProvider() {
    return Stream.of(
            Arguments.of((Object) null),
            Arguments.of("smth string")
    );
  }
}