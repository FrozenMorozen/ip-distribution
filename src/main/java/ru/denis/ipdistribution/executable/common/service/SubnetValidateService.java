package ru.denis.ipdistribution.executable.common.service;

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
   * @param ip            ip для проверки
   * @param networkString строка вида: ip сети/маска подсети
   */
  void containsIpInNetwork(String ip, String networkString);

  /**
   * Является ли ip - ip устройства в подсети
   *
   * @param ipForCheck    ip для проверки
   * @param subnetString  строка вида: ip подсетисети/маска подсети
   */
  void isItDeviceIpForSubnet(String ipForCheck, String subnetString);

}
