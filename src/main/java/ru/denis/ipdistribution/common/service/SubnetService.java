package ru.denis.ipdistribution.common.service;

import org.apache.commons.net.util.SubnetUtils;

/**
 * Сервис для управления подсетями
 */
public interface SubnetService {

  /**
   * Получить IP устройства в глобальной сети из подсети
   *
   * @param subnet            подсеть для выделения IP для устройства
   * @param globalNetworkMask маска глобальной сети (в формате XXX.XXX.XXX.XXX/XX)
   */
  String getDeviceIp(SubnetUtils subnet, String globalNetworkMask);

  /**
   * Получить подсеть для IP устройства и маски подсети
   *
   * @param deviceIp  IP устройства
   * @param rangeMask маска подсети
   */
  SubnetUtils getSubnetForDeviceIp(String deviceIp, String rangeMask);

  /**
   * Проверить IP устройства в глобальной сети
   *
   * @param deviceIp          IP устройства
   * @param globalNetworkMask маска глобальной сети (в формате XXX.XXX.XXX.XXX/XX)
   */
  void checkDeviceIp(String deviceIp, String globalNetworkMask);

}
