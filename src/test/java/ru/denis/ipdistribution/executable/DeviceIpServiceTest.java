package ru.denis.ipdistribution.executable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.denis.ipdistribution.TestDataProvider.DEVICE_RANGE_MASK;
import static ru.denis.ipdistribution.TestDataProvider.deviceIpService;

class DeviceIpServiceTest {

  @ParameterizedTest
  @MethodSource("correctSubnetDataProvider")
  @DisplayName("SubnetService.getNextDeviceIp(...): тест с корректными параметрами")
  void getNextDeviceIpForPrevious(String previousDeviceIp, String expectedDeviceIp) {
    assertEquals(deviceIpService.getNextDeviceIp(previousDeviceIp, DEVICE_RANGE_MASK), expectedDeviceIp);
  }

  private static Stream<Arguments> correctSubnetDataProvider() {
    return Stream.of(
            Arguments.of("172.28.0.1", "172.28.0.5"),
            Arguments.of("172.28.0.5", "172.28.0.9"),
            Arguments.of("172.28.255.249", "172.28.255.253")
    );
  }

}