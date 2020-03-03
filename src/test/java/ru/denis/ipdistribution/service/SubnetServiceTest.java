package ru.denis.ipdistribution.service;

import org.apache.commons.net.util.SubnetUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.denis.ipdistribution.exception.service.OutOfIpRangeException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.denis.ipdistribution.TestDataProvider.businessConfiguration;
import static ru.denis.ipdistribution.TestDataProvider.subnetService;

class SubnetServiceTest {

  private static final String GLOBAL_NETWORK_MASK = businessConfiguration.getGlobalNetworkMask();
  private static final String DEVICE_RANGE_MASK = businessConfiguration.getDeviceIpRangeMask();

  @ParameterizedTest
  @MethodSource("wrongDeviceIpDataProvider")
  @DisplayName("SubnetService.checkDeviceIp(...): тест с НЕвалидными параметрами")
  void checkDeviceWrongIp(String deviceIp) {
    assertThrows(Exception.class, () -> subnetService.checkDeviceIp(deviceIp, GLOBAL_NETWORK_MASK));
  }

  @ParameterizedTest
  @MethodSource("correctDeviceIpDataProvider")
  @DisplayName("SubnetService.checkDeviceIp(...): тест с корректными параметрами")
  void checkDeviceCorrectIp(String deviceIp, String globalNetworkMask) {
    assertDoesNotThrow(() -> subnetService.checkDeviceIp(deviceIp, globalNetworkMask));
  }

  @ParameterizedTest
  @MethodSource("wrongNextDeviceIpDataProvider")
  @DisplayName("SubnetService.getNextDeviceIp(...): тест с НЕвалидными параметрами")
  void getNextDeviceIpWrong(SubnetUtils subnet) {
    assertThrows(OutOfIpRangeException.class, () -> subnetService.getNextDeviceIp(subnet, GLOBAL_NETWORK_MASK));
  }

  @ParameterizedTest
  @MethodSource("correctNextDeviceIpDataProvider")
  @DisplayName("SubnetService.getNextDeviceIp(...): тест с НЕвалидными параметрами")
  void getNextDeviceIpCorrect(SubnetUtils subnet, String expectedResult) {
    assertEquals(subnetService.getNextDeviceIp(subnet, GLOBAL_NETWORK_MASK), expectedResult);
  }

  @ParameterizedTest
  @MethodSource("wrongRangeSubnetForIpDataProvider")
  @DisplayName("SubnetService.createRangeSubnetForIp(...): тест с НЕвалидными параметрами")
  void createRangeSubnetForIpWrong(String deviceIp) {
    assertThrows(IllegalArgumentException.class, () -> subnetService.createRangeSubnetForIp(deviceIp, DEVICE_RANGE_MASK));
  }

  @ParameterizedTest
  @MethodSource("correctRangeSubnetForIpDataProvider")
  @DisplayName("SubnetService.createRangeSubnetForIp(...): тест с корректными параметрами")
  void createRangeSubnetForIpCorrect(String deviceIp, SubnetUtils expectedResult) {
    assertEquals(subnetService.createRangeSubnetForIp(deviceIp, DEVICE_RANGE_MASK).getInfo().toString(),
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
            Arguments.of(new SubnetUtils("172.28.255.253" + DEVICE_RANGE_MASK))
    );
  }

  private static Stream<Arguments> correctNextDeviceIpDataProvider() {
    return Stream.of(
            Arguments.of(new SubnetUtils("172.28.0.1" + DEVICE_RANGE_MASK), "172.28.0.5"),
            Arguments.of(new SubnetUtils("172.28.0.5" + DEVICE_RANGE_MASK), "172.28.0.9"),
            Arguments.of(new SubnetUtils("172.28.0.45" + DEVICE_RANGE_MASK), "172.28.0.49")
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