package ru.denis.ipdistribution.service.impl;

import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.configuration.BusinessConfiguration;
import ru.denis.ipdistribution.exception.service.OutOfIpRangeException;
import ru.denis.ipdistribution.service.IpForDeviceService;
import ru.denis.ipdistribution.service.SubnetService;

@Service
public class IpForDeviceServiceImpl implements IpForDeviceService {

  private SubnetService subnetService;
  //  private SubnetValidateService subnetValidateService;
  private BusinessConfiguration businessConfiguration;

  @Autowired
  public IpForDeviceServiceImpl(SubnetService subnetService, BusinessConfiguration businessConfiguration/*, SubnetValidateService subnetValidateService*/) {
    this.subnetService = subnetService;
    this.businessConfiguration = businessConfiguration;
//    this.subnetValidateService = subnetValidateService;
  }

  @Override
  public String getIpForNextDevice(String previousDeviceIp) throws OutOfIpRangeException, IllegalArgumentException {
//    String rangeMask = businessConfiguration.getIpPickRangeValue();
//    String globalRange = businessConfiguration.getIpGlobalRange();
//    SubnetUtils.SubnetInfo globalSubnetInfo = new SubnetUtils(globalRange).getInfo();

    // Проверить входящий ip устройства
//    subnetService.checkDeviceIpFormat(previousDeviceIp);
//    subnetService.containsIpInGlobalNetwork();
    subnetService.checkDeviceIp(previousDeviceIp, businessConfiguration.getGlobalNetworkMask());

//    try {
//      subnetValidateService.validateAndCheckIpForGlobalSubnet(previousDeviceIp);
//    } catch (IllegalArgumentException ex) {
//      throw new IllegalArgumentException(String.format("Передан некорректный IP: '%s'", previousDeviceIp));
//    } catch (OutOfIpRangeException ex) {
//      throw new OutOfIpRangeException(String.format("IP: '%s' не входит в диапазон %s", previousDeviceIp, globalRange), ex);
//    }

    // Подсеть для входящего ip устройства
    SubnetUtils previousSubnet = subnetService.createRangeSubnetForIp(previousDeviceIp, businessConfiguration.getDeviceIpRangeMask());

    // Следущая для previousSubnet подсеть
//    SubnetUtils nextSubnet = subnetService.getNextSubnet(previousSubnet);

    // Первый доступный для хоста адрес в новой подсети
//    String newDeviceIp = nextSubnet.getInfo().getLowAddress();
//    try {
//      ;
//    } catch (OutOfIpRangeException ex) {
//      throw new OutOfIpRangeException(
//              String.format("IP для следующего устройства: '%s' не входит в диапазон %s", newDeviceIp, globalRange), ex);
//    }
    return subnetService.getNextDeviceIp(previousSubnet, businessConfiguration.getGlobalNetworkMask());
  }

}
