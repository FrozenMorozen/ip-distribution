package ru.denis.ipdistribution.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.denis.ipdistribution.configuration.BusinessConfiguration;
import ru.denis.ipdistribution.service.IpForDeviceService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IpForDeviceServiceImplTest {

  private static IpForDeviceService ipForDeviceService;

  @BeforeAll
  static void setUp() {
    BusinessConfiguration businessConfiguration = new BusinessConfiguration();
    businessConfiguration.setIpGlobalRange("172.28.0.0/16");
    businessConfiguration.setIpPickRangeValue("/30");
    ipForDeviceService = new IpForDeviceServiceImpl(new SubnetServiceImpl(), businessConfiguration);
  }

  @ParameterizedTest
  @MethodSource("correctIpsDataProvider")
  @DisplayName("IpForDeviceService.getIpForNextDevice(...) : тест с валидными параметрами")
  void getIpForNextDevice(String inputIp, String expectedResult) {
    assertEquals(ipForDeviceService.getIpForNextDevice(inputIp), expectedResult);
  }

  @ParameterizedTest
  @MethodSource("wrongIpsDataProvider")
  @DisplayName("IpForDeviceService.getIpForNextDevice(...) : тест с НЕвалидными параметрами")
  void getIpForNextDevice(String inputIp) {
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
            Arguments.of("smth string"),
            Arguments.of("0.0.0.0"),
            Arguments.of("172.28."),
            Arguments.of("172.28.0.5.7"),
            Arguments.of("172.28.255.253")  // последнее значение устройства из диапазона
    );
  }
}