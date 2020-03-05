package ru.denis.ipdistribution.business.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.business.configuration.BusinessConfiguration;
import ru.denis.ipdistribution.business.service.BusinessDeviceIpService;
import ru.denis.ipdistribution.executable.common.service.DeviceIpService;
import ru.denis.ipdistribution.executable.common.service.SubnetValidateService;

@Service
public class BusinessDeviceIpServiceImpl implements BusinessDeviceIpService {

  private DeviceIpService deviceIpService;
  private BusinessConfiguration configuration;
  private SubnetValidateService subnetValidateService;

  @Autowired
  public BusinessDeviceIpServiceImpl(DeviceIpService deviceIpService, BusinessConfiguration configuration, SubnetValidateService subnetValidateService) {
    this.deviceIpService = deviceIpService;
    this.configuration = configuration;
    this.subnetValidateService = subnetValidateService;
  }

  @Override
  public String getIpForNextDevice(String inputIp) {

    // Проверить входящий ip
    subnetValidateService.validateIpFormat(inputIp);
    subnetValidateService.containsIpInNetwork(inputIp, configuration.getGlobalNetworkMask());

    // Является ли входящий ip первым хостом(ip устройства) в своей подсети
    subnetValidateService.isItDeviceIpForSubnet(inputIp, inputIp + configuration.getDeviceIpRangeMask());

    // ip устройства в следующей подсети (ip первого хоста в подсети)
    String nextDeviceIp = deviceIpService.getNextDeviceIp(inputIp, configuration.getDeviceIpRangeMask());

    // Проверить, принадлжеит ли ip устройства глобальной подсети
    subnetValidateService.containsIpInNetwork(nextDeviceIp, configuration.getGlobalNetworkMask());

    return nextDeviceIp;
  }

}
