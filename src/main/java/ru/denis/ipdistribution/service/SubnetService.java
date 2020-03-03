package ru.denis.ipdistribution.service;

import org.apache.commons.net.util.SubnetUtils;

/**
 * Сервис для управления подсетями
 */
public interface SubnetService {

  /**
   * Получить ip для следующего устройства в глобальной сети
   *
   * @param previousSubnet    предыдущая подсеть
   * @param globalNetworkMask маска глобальной сети (в формате XXX.XXX.XXX.XXX/XX)
   */
  String getNextDeviceIp(SubnetUtils previousSubnet, String globalNetworkMask);

  /**
   * Создать подсеть для ip устройства и маски подсети
   *
   * @param deviceIp  ip устройства
   * @param rangeMask маска подсети
   */
  SubnetUtils createRangeSubnetForIp(String deviceIp, String rangeMask);

  /**
   * Проверить ip устройства в глобальной сети
   *
   * @param deviceIp          ip устройства
   * @param globalNetworkMask маска глобальной сети (в формате XXX.XXX.XXX.XXX/XX)
   */
  void checkDeviceIp(String deviceIp, String globalNetworkMask);

}
