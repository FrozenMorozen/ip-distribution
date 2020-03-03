package ru.denis.ipdistribution.common;

import org.apache.commons.net.util.SubnetUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.denis.ipdistribution.common.impl.SubnetValidateServiceImpl;
import ru.denis.ipdistribution.common.service.SubnetValidateService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.denis.ipdistribution.TestDataProvider.GLOBAL_NETWORK_MASK;
import static ru.denis.ipdistribution.TestDataProvider.businessConfiguration;

class SubnetValidateServiceTest {

  private static SubnetValidateService subnetValidateService = new SubnetValidateServiceImpl();
  private static SubnetUtils globalSubnet = new SubnetUtils(GLOBAL_NETWORK_MASK);

  @ParameterizedTest
  @MethodSource("wrongIpDataProvider")
  @DisplayName("SubnetValidateService.validateIpFormat(...): тест с НЕвалидными параметрами")
  void validateWrongIpFormat(String ipAddress) {
    assertThrows(Exception.class, () -> subnetValidateService.validateIpFormat(ipAddress));
  }

  @ParameterizedTest
  @MethodSource("correctIpDataProvider")
  @DisplayName("SubnetValidateService.validateIpFormat(...): тест с корректными параметрами")
  void validateCorrectIpFormat(String ipAddress) {
    assertDoesNotThrow(() -> subnetValidateService.validateIpFormat(ipAddress));
  }

  @ParameterizedTest
  @MethodSource("wrongIpInGlobalRangeDataProvider")
  @DisplayName("SubnetValidateService.containsIpInNetwork(...): тест с НЕвалидными параметрами")
  void containsIpInGlobalRangeWrong(String ip, SubnetUtils network) {
    assertThrows(Exception.class, () -> subnetValidateService.containsIpInNetwork(ip, globalSubnet));
  }

  @ParameterizedTest
  @MethodSource("correctIpInGlobalRangeDataProvider")
  @DisplayName("SubnetValidateService.containsIpInNetwork(...): тест с корректными параметрами")
  void containsIpInGlobalRangeCorrect(String ip, SubnetUtils network) {
    assertDoesNotThrow(() -> subnetValidateService.containsIpInNetwork(ip, network));
  }

  @ParameterizedTest
  @MethodSource("wrongDeviceIpForSubnetDataProvider")
  @DisplayName("SubnetValidateService.itIsDeviceIpForSubnet(...): тест с НЕвалидными параметрами")
  void itIsDeviceIpForSubnetWrong(String ipForCheck, SubnetUtils subnet) {
    assertThrows(Exception.class, () -> subnetValidateService.isItDeviceIpForSubnet(ipForCheck, subnet));
  }

  @ParameterizedTest
  @MethodSource("correctDeviceIpForSubnetDataProvider")
  @DisplayName("SubnetValidateService.itIsDeviceIpForSubnet(...): тест с корректными параметрами")
  void itIsDeviceIpForSubnetCorrect(String ipForCheck, SubnetUtils subnet) {
    assertDoesNotThrow(() -> subnetValidateService.isItDeviceIpForSubnet(ipForCheck, subnet));
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
            Arguments.of("172.255.255.255", globalSubnet),
            Arguments.of("172.29.0.9", globalSubnet),
            Arguments.of("172.255.255.255", globalSubnet)
    );
  }

  private static Stream<Arguments> correctIpInGlobalRangeDataProvider() {
    return Stream.of(
            Arguments.of("172.28.0.9", globalSubnet),
            Arguments.of("172.28.0.5", globalSubnet)
    );
  }

  private static Stream<Arguments> wrongDeviceIpForSubnetDataProvider() {
    SubnetUtils subnetForBusinessConfig = new SubnetUtils(businessConfiguration.getGlobalNetworkMask());
    return Stream.of(
            Arguments.of("172.28.255.253", subnetForBusinessConfig),
            Arguments.of("0.0.0.0", subnetForBusinessConfig)
    );
  }

  private static Stream<Arguments> correctDeviceIpForSubnetDataProvider() {
    return Stream.of(
            Arguments.of("172.28.0.1", new SubnetUtils("172.28.0.1" + businessConfiguration.getDeviceIpRangeMask())),
            Arguments.of("172.28.0.5", new SubnetUtils("172.28.0.5" + businessConfiguration.getDeviceIpRangeMask()))
    );
  }

}