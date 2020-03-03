package ru.denis.ipdistribution.common;

import org.apache.commons.net.util.SubnetUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.denis.ipdistribution.common.exception.OutOfIpRangeException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.denis.ipdistribution.TestDataProvider.*;

class SubnetServiceTest {

  @ParameterizedTest
  @MethodSource("wrongDeviceIpDataProvider")
  @DisplayName("SubnetService.checkDeviceIp(...): тест с НЕвалидными параметрами")
  void checkDeviceWrongIp(String deviceIp) {
    assertThrows(Exception.class, () -> subnetService.checkDeviceIp(deviceIp, GLOBAL_NETWORK_MASK));
  }

  @ParameterizedTest
  @MethodSource("correctDeviceIpDataProvider")
  @DisplayName("SubnetService.checkDeviceIp(...): тест с корректными параметрами")
  void checkDeviceCorrectIp(String deviceIp) {
    assertDoesNotThrow(() -> subnetService.checkDeviceIp(deviceIp, GLOBAL_NETWORK_MASK));
  }

  @ParameterizedTest
  @MethodSource("wrongNextDeviceIpDataProvider")
  @DisplayName("SubnetService.getDeviceIp(...): тест с НЕвалидными параметрами")
  void getDeviceIpWrong(SubnetUtils subnet) {
    assertThrows(OutOfIpRangeException.class, () -> subnetService.getDeviceIp(subnet, GLOBAL_NETWORK_MASK));
  }

  @ParameterizedTest
  @MethodSource("correctNextDeviceIpDataProvider")
  @DisplayName("SubnetService.getDeviceIp(...): тест с НЕвалидными параметрами")
  void getDeviceIpCorrect(SubnetUtils subnet, String expectedResult) {
    Assertions.assertEquals(subnetService.getDeviceIp(subnet, GLOBAL_NETWORK_MASK), expectedResult);
  }

  @ParameterizedTest
  @MethodSource("wrongRangeSubnetForIpDataProvider")
  @DisplayName("SubnetService.createRangeSubnetForIp(...): тест с НЕвалидными параметрами")
  void createRangeSubnetForIpWrong(String deviceIp) {
    assertThrows(IllegalArgumentException.class, () -> subnetService.getSubnetForDeviceIp(deviceIp, DEVICE_RANGE_MASK));
  }

  @ParameterizedTest
  @MethodSource("correctRangeSubnetForIpDataProvider")
  @DisplayName("SubnetService.createRangeSubnetForIp(...): тест с корректными параметрами")
  void createRangeSubnetForIpCorrect(String deviceIp, SubnetUtils expectedResult) {
    Assertions.assertEquals(subnetService.getSubnetForDeviceIp(deviceIp, DEVICE_RANGE_MASK).getInfo().toString(),
            expectedResult.getInfo().toString());
  }

  private static Stream<Arguments> wrongDeviceIpDataProvider() {
    return Stream.of(
            Arguments.of("smth string"),
            Arguments.of("172.28."),
            Arguments.of("172.28.0.5.7"),
            Arguments.of("172.255.255.255"),
            Arguments.of("172.29.0.9")
    );
  }

  private static Stream<Arguments> correctDeviceIpDataProvider() {
    return Stream.of(
            Arguments.of("172.28.0.1"),
            Arguments.of("172.28.0.9"),
            Arguments.of("172.28.0.5")
    );
  }

  private static Stream<Arguments> wrongNextDeviceIpDataProvider() {
    return Stream.of(
            Arguments.of(new SubnetUtils("172.29.0.0" + DEVICE_RANGE_MASK))
    );
  }

  private static Stream<Arguments> correctNextDeviceIpDataProvider() {
    return Stream.of(
            Arguments.of(new SubnetUtils("172.28.0.0" + DEVICE_RANGE_MASK), "172.28.0.1"),
            Arguments.of(new SubnetUtils("172.28.0.4" + DEVICE_RANGE_MASK), "172.28.0.5"),
            Arguments.of(new SubnetUtils("172.28.0.8" + DEVICE_RANGE_MASK), "172.28.0.9"),
            Arguments.of(new SubnetUtils("172.28.0.48" + DEVICE_RANGE_MASK), "172.28.0.49")
    );
  }

  private static Stream<Arguments> correctRangeSubnetForIpDataProvider() {
    return Stream.of(
            Arguments.of("172.28.0.1", new SubnetUtils("172.28.0.1" + DEVICE_RANGE_MASK)),
            Arguments.of("172.28.0.9", new SubnetUtils("172.28.0.9" + DEVICE_RANGE_MASK)),
            Arguments.of("172.28.0.5", new SubnetUtils("172.28.0.5" + DEVICE_RANGE_MASK))
    );
  }

  private static Stream<Arguments> wrongRangeSubnetForIpDataProvider() {
    return Stream.of(
            Arguments.of("172.28.0.2"),
            Arguments.of("172.28.0.10"),
            Arguments.of("172.28.0.16")
    );
  }
}