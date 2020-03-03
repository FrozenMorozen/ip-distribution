package ru.denis.ipdistribution.service;

import org.apache.commons.net.util.SubnetUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.denis.ipdistribution.service.impl.SubnetValidateServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.denis.ipdistribution.TestDataProvider.businessConfiguration;

class SubnetValidateServiceTest {

  private static SubnetValidateService subnetValidateService = new SubnetValidateServiceImpl();

  @ParameterizedTest
  @MethodSource("wrongIpDataProvider")
  @DisplayName("SubnetValidateService.validateIpFormat(...): тест с НЕвалидными параметрами")
  void validateWrongIpFormat(String ipAddress) {
    assertThrows(Exception.class, () -> subnetValidateService.validateIpFormat(ipAddress));
  }

  ;

  @ParameterizedTest
  @MethodSource("correctIpDataProvider")
  @DisplayName("SubnetValidateService.validateIpFormat(...): тест с корректными параметрами")
  void validateCorrectIpFormat(String ipAddress) {
    assertDoesNotThrow(() -> subnetValidateService.validateIpFormat(ipAddress));
  }

  ;

  @ParameterizedTest
  @MethodSource("wrongIpInGlobalRangeDataProvider")
  @DisplayName("SubnetValidateService.containsIpInGlobalRange(...): тест с НЕвалидными параметрами")
  void containsIpInGlobalRangeWrong(String ip, String globalSubnet) {
    assertThrows(Exception.class, () -> subnetValidateService.containsIpInGlobalRange(ip, globalSubnet));
  }

  ;

  @ParameterizedTest
  @MethodSource("correctIpInGlobalRangeDataProvider")
  @DisplayName("SubnetValidateService.containsIpInGlobalRange(...): тест с корректными параметрами")
  void containsIpInGlobalRangeCorrect(String ip, String globalSubnet) {
    assertDoesNotThrow(() -> subnetValidateService.containsIpInGlobalRange(ip, globalSubnet));
  }

  ;

  @ParameterizedTest
  @MethodSource("wrongDeviceIpForSubnetDataProvider")
  @DisplayName("SubnetValidateService.itIsDeviceIpForSubnet(...): тест с НЕвалидными параметрами")
  void itIsDeviceIpForSubnetWrong(String ipForCheck, SubnetUtils subnet) {
    assertThrows(Exception.class, () -> subnetValidateService.itIsDeviceIpForSubnet(ipForCheck, subnet));
  }

  @ParameterizedTest
  @MethodSource("correctDeviceIpForSubnetDataProvider")
  @DisplayName("SubnetValidateService.itIsDeviceIpForSubnet(...): тест с корректными параметрами")
  void itIsDeviceIpForSubnetCorrect(String ipForCheck, SubnetUtils subnet) {
    assertDoesNotThrow(() -> subnetValidateService.itIsDeviceIpForSubnet(ipForCheck, subnet));
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
            Arguments.of("172.255.255.255", businessConfiguration.getIpGlobalRange()),
            Arguments.of("172.29.0.9", businessConfiguration.getIpGlobalRange()),
            Arguments.of("172.255.255.255", businessConfiguration.getIpGlobalRange())
    );
  }

  private static Stream<Arguments> correctIpInGlobalRangeDataProvider() {
    return Stream.of(
            Arguments.of("172.28.0.9", businessConfiguration.getIpGlobalRange()),
            Arguments.of("172.28.0.5", businessConfiguration.getIpGlobalRange())
    );
  }

  private static Stream<Arguments> wrongDeviceIpForSubnetDataProvider() {
    SubnetUtils subnetForBusinessConfig = new SubnetUtils(businessConfiguration.getIpGlobalRange());
    return Stream.of(
            Arguments.of("172.28.255.253", subnetForBusinessConfig),
            Arguments.of("0.0.0.0", subnetForBusinessConfig)
    );
  }

  private static Stream<Arguments> correctDeviceIpForSubnetDataProvider() {
//    SubnetUtils subnetForBusinessConfig = new SubnetUtils(businessConfiguration.getIpGlobalRange());
    return Stream.of(
            Arguments.of("172.28.0.1", new SubnetUtils("172.28.0.1" + businessConfiguration.getIpPickRangeValue())),
            Arguments.of("172.28.0.5", new SubnetUtils("172.28.0.5" + businessConfiguration.getIpPickRangeValue()))
    );
  }

}