package ru.denis.ipdistribution.service.Impl;

import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.common.service.SubnetCalculatorService;
import ru.denis.ipdistribution.common.service.SubnetService;
import ru.denis.ipdistribution.configuration.BusinessConfiguration;
import ru.denis.ipdistribution.service.DeviceIpService;

@Service
public class DeviceIpServiceImpl implements DeviceIpService {

  private SubnetService subnetService;
  private BusinessConfiguration businessConfiguration;
  private SubnetCalculatorService subnetCalculatorService;

  @Autowired
  public DeviceIpServiceImpl(SubnetService subnetService, BusinessConfiguration businessConfiguration, SubnetCalculatorService subnetCalculatorService) {
    this.subnetService = subnetService;
    this.businessConfiguration = businessConfiguration;
    this.subnetCalculatorService = subnetCalculatorService;
  }

  @Override
  public String getIpForNextDevice(String previousDeviceIp) {

    // Проверить ip предыдущего устройства
    subnetService.checkDeviceIp(previousDeviceIp, businessConfiguration.getGlobalNetworkMask());

    // Подсеть для входящего ip устройства
    SubnetUtils previousSubnet = subnetService.getSubnetForDeviceIp(previousDeviceIp, businessConfiguration.getDeviceIpRangeMask());

    // Следущая подсеть
    SubnetUtils nextSubnet = subnetCalculatorService.getNextSubnetForPrevious(previousSubnet);

    // ip устройства
    return subnetService.getDeviceIp(nextSubnet, businessConfiguration.getGlobalNetworkMask());
  }

}
