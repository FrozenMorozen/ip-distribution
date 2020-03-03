package ru.denis.ipdistribution.common.service;

import org.apache.commons.net.util.SubnetUtils;

/**
 * Сервис для управления подсетями
 */
public interface SubnetService {

  /**
   * Получить следущую подсеть
   * @param previousSubnet  предыдущая подсеть
   */
  SubnetUtils getNextSubnet(SubnetUtils previousSubnet, String rangeMask);
}
