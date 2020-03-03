package ru.denis.ipdistribution.common;

import org.apache.commons.net.util.SubnetUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.denis.ipdistribution.common.impl.IpMaskServiceImpl;
import ru.denis.ipdistribution.common.impl.SubnetCalculatorServiceImpl;
import ru.denis.ipdistribution.common.service.SubnetCalculatorService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.denis.ipdistribution.TestDataProvider.DEVICE_RANGE_MASK;

class SubnetCalculatorServiceTest {

  private SubnetCalculatorService subnetCalculatorService = new SubnetCalculatorServiceImpl(new IpMaskServiceImpl());

  @ParameterizedTest
  @MethodSource("correctSubnetDataProvider")
  @DisplayName("SubnetCalculatorService.getNextSubnetForPrevious(...): тест с корректными параметрами")
  void getNextSubnetForPrevious(SubnetUtils previousSubnet, SubnetUtils expected) {
    assertEquals(subnetCalculatorService.getNextSubnetForPrevious(previousSubnet).getInfo().toString(),
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