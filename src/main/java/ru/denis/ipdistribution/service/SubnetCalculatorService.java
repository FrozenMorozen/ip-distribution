package ru.denis.ipdistribution.service;

import org.apache.commons.net.util.SubnetUtils;

/**
 * Сервис-калькулятор подсетей
 */
public interface SubnetCalculatorService {

  /**
   * Получить следующую подсеть
   * @param previousSubnet  предыдущая подсеть
   */
  SubnetUtils getNextSubnetForPrevious(SubnetUtils previousSubnet);
}
