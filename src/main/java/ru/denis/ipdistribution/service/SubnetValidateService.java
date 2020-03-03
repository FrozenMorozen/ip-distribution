package ru.denis.ipdistribution.service;

import org.apache.commons.net.util.SubnetUtils;

/**
 * Сервис валидации для работы с подстетью
 */
public interface SubnetValidateService {

  /**
   * Валидацмя ip адреса
   */
  void validateIpFormat(String ipAddress);

  /**
   * Принадлежит ли ip глобальной подсети
   *
   * @param ip            ip для проверки
   * @param globalNetwork глобальная сеть (в формате XXX.XXX.XXX.XXX/XX)
   */
  void containsIpInGlobalNetwork(String ip, String globalNetwork);

  /**
   * Является ли ip - ip устройства в подсети
   *
   * @param ipForCheck ip для проверки
   * @param subnet     подсеть для получения актуального ip устройства в ней
   */
  void isItDeviceIpForSubnet(String ipForCheck, SubnetUtils subnet);
}
