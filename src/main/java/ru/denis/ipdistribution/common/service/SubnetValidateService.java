package ru.denis.ipdistribution.common.service;

import org.apache.commons.net.util.SubnetUtils;

/**
 * Сервис валидации для работы с ip адресами в подсети
 */
public interface SubnetValidateService {

  /**
   * Валидация формата ip адреса
   */
  void validateIpFormat(String ipAddress);

  /**
   * Принадлежит ли ip сети
   *
   * @param ip      ip для проверки
   * @param network сеть
   */
  void containsIpInNetwork(String ip, SubnetUtils network);

  /**
   * Является ли ip - ip устройства в подсети
   *
   * @param ipForCheck ip для проверки
   * @param subnet     подсеть для получения актуального ip устройства в ней
   */
  void isItDeviceIpForSubnet(String ipForCheck, SubnetUtils subnet);

}
