package ru.denis.ipdistribution;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.denis.ipdistribution.business.service.BusinessDeviceIpService;
import ru.denis.ipdistribution.business.service.Impl.BusinessDeviceIpServiceImpl;
import ru.denis.ipdistribution.executable.impl.apache.comons.net.SubnetValidateServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.denis.ipdistribution.TestDataProvider.businessConfiguration;
import static ru.denis.ipdistribution.TestDataProvider.deviceIpService;

class BusinessDeviceIpServiceTest {

  private static BusinessDeviceIpService businessDeviceIpService =
          new BusinessDeviceIpServiceImpl(deviceIpService, businessConfiguration, new SubnetValidateServiceImpl());

  @ParameterizedTest
  @MethodSource("correctIpsDataProvider")
  @DisplayName("IpForDeviceService.getIpForNextDevice(...) : тест с валидными параметрами")
  void getIpForNextDevice(String inputIp, String expectedResult) {
    assertEquals(businessDeviceIpService.getIpForNextDevice(inputIp), expectedResult);
  }

  @ParameterizedTest
  @MethodSource("wrongIpsDataProvider")
  @DisplayName("IpForDeviceService.getIpForNextDevice(...) : тест с НЕвалидными параметрами")
  void getIpForNextDevice(String inputIp) {
    assertThrows(Exception.class, () -> businessDeviceIpService.getIpForNextDevice(inputIp));
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
            Arguments.of("172.28."),
            Arguments.of("172.28.0.5.7"),
            Arguments.of("0.0.0.0"),
            Arguments.of("172.28.255.253"),  // последнее значение устройства из диапазона
            Arguments.of("172.28.255.254")
    );
  }
}