package ru.denis.ipdistribution.service;

/**
 * Сервис для работы с масками IP
 */
public interface IpMaskService {

  /**
   * Получить маску сети из ip адреса
   */
  String getNetworkMaskFromIp(String ip);

  /**
   * Прибавить кол-в битов к IP
   * @param bitForAdd кол-во битов для добавления
   */
  String addBitsToIp(String ip, int bitForAdd);
}
