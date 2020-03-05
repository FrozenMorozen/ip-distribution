package ru.denis.ipdistribution.executable.common.service;

/**
 * Сервис для управления IP адресами устройств
 */
public interface DeviceIpService {

  /**
   * Получить IP следующего устройства, имея IP предыдущего
   * @param previousDeviceIp  IP предыдущего устройства
   * @param rangeMask         маска подсети
   * @return IP устройства в следующей подсети с маской {@param rangeMask}
   */
  String getNextDeviceIp(String previousDeviceIp, String rangeMask);
}
