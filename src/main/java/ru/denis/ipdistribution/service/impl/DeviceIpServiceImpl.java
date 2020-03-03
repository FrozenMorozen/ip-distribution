package ru.denis.ipdistribution.service.impl;

import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.configuration.BusinessConfiguration;
import ru.denis.ipdistribution.exception.service.OutOfIpRangeException;
import ru.denis.ipdistribution.service.DeviceIpService;
import ru.denis.ipdistribution.service.SubnetCalculatorService;
import ru.denis.ipdistribution.service.SubnetService;

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
  public String getIpForNextDevice(String previousDeviceIp) throws OutOfIpRangeException, IllegalArgumentException {

    // Проверить входящий ip устройства
    subnetService.checkDeviceIp(previousDeviceIp, businessConfiguration.getGlobalNetworkMask());

    // Подсеть для входящего ip устройства
    SubnetUtils previousSubnet = subnetService.getRangeSubnetForIp(previousDeviceIp, businessConfiguration.getDeviceIpRangeMask());

    // Следущая подсеть
    SubnetUtils nextSubnet = subnetCalculatorService.getNextSubnetForPrevious(previousSubnet);

    return subnetService.getDeviceIp(nextSubnet, businessConfiguration.getGlobalNetworkMask());
  }

}
