package ru.denis.ipdistribution.executable.service;

import org.apache.commons.net.util.SubnetUtils;

/**
 * Сервис для управления IP адресами устройств
 */
public interface DeviceIpService {

  /**
   * Получить следущую подсеть
   * @param previousSubnet  предыдущая подсеть
   */
  SubnetUtils getNextSubnet(SubnetUtils previousSubnet, String rangeMask);
  SubnetUtils getNextSubnet(String previousIp, String rangeMask);

  /**
   * Получить IP следующего устройства, имея IP предыдущего
   * @param previousDeviceIp  IP предыдущего устройства
   * @param rangeMask         маска подсети
   * @return IP устройства в следующей подсети с маской {@param rangeMask}
   */
  String getNextDeviceIp(String previousDeviceIp, String rangeMask);
}
