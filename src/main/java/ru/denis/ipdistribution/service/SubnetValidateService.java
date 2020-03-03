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
   * @param ip           ip для проверки
   * @param globalSubnet адрес глобальной подстети в формате XXX.XXX.XXX.XXX/XX
   */
  void containsIpInGlobalRange(String ip, String globalSubnet);

  /**
   * Является ли ip - ip устройства в подсети
   *
   * @param ipForCheck ip для проверки
   * @param subnet     подсеть для получения актуального ip устройства в ней
   */
  void itIsDeviceIpForSubnet(String ipForCheck, SubnetUtils subnet);
}
