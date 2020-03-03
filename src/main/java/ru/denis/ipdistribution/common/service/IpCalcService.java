package ru.denis.ipdistribution.common.service;

/**
 * Сервис для вычислительных операций с IP
 */
public interface IpCalcService {

  /**
   * Прибавить кол-в битов к IP
   * @param bitForAdd кол-во битов для добавления
   */
  String addBitsToIp(String ip, int bitForAdd);
}
