package ru.denis.ipdistribution.business.service;

/**
 * Сервис для бизнесовой логики с ip адресами устройств
 */
public interface BusinessDeviceIpService {

  /**
   * Получить ip адрес для следущего устройства
   * @param inputIp ip адрес предыдущего устройства
   */
  String getIpForNextDevice(String inputIp);
}
