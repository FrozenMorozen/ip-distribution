package ru.denis.ipdistribution.service;

/**
 * Сервис для предоставления ip адресов устройствам
 */
public interface DeviceIpService {

  /**
   * Получить ip адрес для следующего устройства
   * @param previousDeviceIp ip адрес предыдущего устройства
   */
  String getIpForNextDevice(String previousDeviceIp);
}
