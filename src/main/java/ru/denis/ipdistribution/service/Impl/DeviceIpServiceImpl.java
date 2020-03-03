package ru.denis.ipdistribution.service.Impl;

import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.common.service.SubnetService;
import ru.denis.ipdistribution.common.service.SubnetValidateService;
import ru.denis.ipdistribution.configuration.BusinessConfiguration;
import ru.denis.ipdistribution.service.DeviceIpService;

@Service
public class DeviceIpServiceImpl implements DeviceIpService {

  private SubnetService subnetService;
  private BusinessConfiguration businessConfiguration;
  private SubnetValidateService subnetValidateService;
  private SubnetUtils globalNetwork;

  @Autowired
  public DeviceIpServiceImpl(SubnetService subnetService, BusinessConfiguration businessConfiguration, SubnetValidateService subnetValidateService) {
    this.subnetService = subnetService;
    this.businessConfiguration = businessConfiguration;
    this.subnetValidateService = subnetValidateService;

    globalNetwork = new SubnetUtils(businessConfiguration.getGlobalNetworkMask());
    globalNetwork.setInclusiveHostCount(true);
  }

  @Override
  public String getIpForNextDevice(String inputIp) {

    // Проверить входящий ip
    subnetValidateService.validateIpFormat(inputIp);
    subnetValidateService.containsIpInNetwork(inputIp, globalNetwork);

    // Подсеть для входящего ip
    SubnetUtils previousSubnet = new SubnetUtils(inputIp + businessConfiguration.getDeviceIpRangeMask());;

    // Является ли входящий ip первым хостом(ip устройства) в своей подсети
    subnetValidateService.isItDeviceIpForSubnet(inputIp, previousSubnet);

    // ip устройства в следующей подсети (ip первого хоста в подсети)
    String nextDeviceIp = subnetService.getNextSubnet(previousSubnet, businessConfiguration.getDeviceIpRangeMask())
            .getInfo().getLowAddress();

    // Проверить, принадлжеит ли ip устройства глобальной подсети
    subnetValidateService.containsIpInNetwork(nextDeviceIp, globalNetwork);

    return nextDeviceIp;
  }

}
