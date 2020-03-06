package ru.denis.ipdistribution.executable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.denis.ipdistribution.executable.common.service.SubnetValidateService;
import ru.denis.ipdistribution.executable.seancfoley.ipaddress.impl.SubnetValidateServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.denis.ipdistribution.TestDataProvider.DEVICE_RANGE_MASK;
import static ru.denis.ipdistribution.TestDataProvider.GLOBAL_NETWORK_STRING;

class SubnetValidateServiceTest {

  private static SubnetValidateService subnetValidateService = new SubnetValidateServiceImpl();

  // Почему-то с @ParameterizedTest не работает @Ignore, поэтому пока так
//  @ParameterizedTest
//  @MethodSource("wrongIpDataProvider")
//  @DisplayName("SubnetValidateService.validateIpFormat(...): тест с НЕвалидными параметрами")
//  void validateWrongIpFormat(String ipAddress) {
//    assertThrows(Exception.class, () -> subnetValidateService.validateIpFormat(ipAddress));
//  }
//
//  @ParameterizedTest
//  @MethodSource("correctIpDataProvider")
//  @DisplayName("SubnetValidateService.validateIpFormat(...): тест с корректными параметрами")
//  void validateCorrectIpFormat(String ipAddress) {
//    assertDoesNotThrow(() -> subnetValidateService.validateIpFormat(ipAddress));
//  }

  @ParameterizedTest
  @MethodSource("wrongIpInGlobalRangeDataProvider")
  @DisplayName("SubnetValidateService.containsIpInNetwork(...): тест с НЕвалидными параметрами")
  void containsIpInGlobalRangeWrong(String ip) {
    assertThrows(Exception.class, () -> subnetValidateService.containsIpInNetwork(ip, GLOBAL_NETWORK_STRING));
  }

  @ParameterizedTest
  @MethodSource("correctIpInGlobalRangeDataProvider")
  @DisplayName("SubnetValidateService.containsIpInNetwork(...): тест с корректными параметрами")
  void containsIpInGlobalRangeCorrect(String ip) {
    assertDoesNotThrow(() -> subnetValidateService.containsIpInNetwork(ip, GLOBAL_NETWORK_STRING));
  }

  @ParameterizedTest
  @MethodSource("wrongDeviceIpForSubnetDataProvider")
  @DisplayName("SubnetValidateService.itIsDeviceIpForSubnet(...): тест с НЕвалидными параметрами")
  void itIsDeviceIpForSubnetWrong(String ipForCheck) {
    assertThrows(Exception.class, () -> subnetValidateService.isItDeviceIpForSubnet(ipForCheck, DEVICE_RANGE_MASK));
  }

  @ParameterizedTest
  @MethodSource("correctDeviceIpForSubnetDataProvider")
  @DisplayName("SubnetValidateService.itIsDeviceIpForSubnet(...): тест с корректными параметрами")
  void itIsDeviceIpForSubnetCorrect(String ipForCheck) {
    assertDoesNotThrow(() -> subnetValidateService.isItDeviceIpForSubnet(ipForCheck, DEVICE_RANGE_MASK));
  }

  private static Stream<Arguments> correctIpDataProvider() {
    return Stream.of(
            Arguments.of("172.28.0.1"),
            Arguments.of("172.28.0.9"),
            Arguments.of("172.255.255.255"),
            Arguments.of("172.28.0.5")
    );
  }

  private static Stream<Arguments> wrongIpDataProvider() {
    return Stream.of(
            Arguments.of("smth string"),
            Arguments.of("172.28."),
            Arguments.of("172.28.0.5.7")
    );
  }

  private static Stream<Arguments> wrongIpInGlobalRangeDataProvider() {
    return Stream.of(
            Arguments.of("172.255.255.255"),
            Arguments.of("172.29.0.9"),
            Arguments.of("172.255.255.255")
    );
  }

  private static Stream<Arguments> correctIpInGlobalRangeDataProvider() {
    return Stream.of(
            Arguments.of("172.28.0.9"),
            Arguments.of("172.28.0.5"),
            Arguments.of("172.28.255.253")

    );
  }

  private static Stream<Arguments> wrongDeviceIpForSubnetDataProvider() {
    return Stream.of(
            Arguments.of("172.28.255.252"),
            Arguments.of("172.28.0.2"),
            Arguments.of("0.0.0.0")
    );
  }

  private static Stream<Arguments> correctDeviceIpForSubnetDataProvider() {
    return Stream.of(
            Arguments.of("172.28.0.1"),
            Arguments.of("172.28.0.5")
    );
  }

}