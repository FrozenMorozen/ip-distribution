package ru.denis.ipdistribution.common;

import org.apache.commons.net.util.SubnetUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.denis.ipdistribution.TestDataProvider.DEVICE_RANGE_MASK;
import static ru.denis.ipdistribution.TestDataProvider.subnetService;

class SubnetServiceTest {

  @ParameterizedTest
  @MethodSource("correctSubnetDataProvider")
  @DisplayName("SubnetService.getNextSubnet(...): тест с корректными параметрами")
  void getNextSubnetForPrevious(SubnetUtils previousSubnet, SubnetUtils expected) {
    assertEquals(subnetService.getNextSubnet(previousSubnet, DEVICE_RANGE_MASK).getInfo().toString(),
            expected.getInfo().toString());
  }

  private static Stream<Arguments> correctSubnetDataProvider() {
    return Stream.of(
            Arguments.of(new SubnetUtils("172.28.0.0" + DEVICE_RANGE_MASK),
                    new SubnetUtils("172.28.0.4" + DEVICE_RANGE_MASK)),
            Arguments.of(new SubnetUtils("172.28.0.4" + DEVICE_RANGE_MASK),
                    new SubnetUtils("172.28.0.8" + DEVICE_RANGE_MASK)),
            Arguments.of(new SubnetUtils("172.28.0.8" + DEVICE_RANGE_MASK),
                    new SubnetUtils("172.28.0.12" + DEVICE_RANGE_MASK)),
            Arguments.of(new SubnetUtils("172.28.0.48" + DEVICE_RANGE_MASK),
                    new SubnetUtils("172.28.0.52" + DEVICE_RANGE_MASK))
    );
  }

}