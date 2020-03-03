package ru.denis.ipdistribution.service;

/**
 * Сервис для предоставления ip адресов устройствам
 */
public interface DeviceIpService {

  /**
   * Получить ip адрес для следущего устройства
   * @param inputIp ip адрес предыдущего устройства
   */
  String getIpForNextDevice(String inputIp);
}
