package ru.denis.ipdistribution.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.denis.ipdistribution.TestDataProvider.subnetService;

class SubnetServiceTest {

  @Test
  void checkDeviceWrongIp(String deviceIp) {
    assertThrows(Exception.class, () -> subnetService.checkDeviceIp(deviceIp));
  }

  @Test
  void checkDeviceCorrectIp(String deviceIp) {
    assertDoesNotThrow(() -> subnetService.checkDeviceIp(deviceIp));
  }

  @Test
  void getNextDeviceIp() {
//    subnetService.getNextDeviceIp()
  }

  @Test
  void createRangeSubnetForIp() {
  }
}