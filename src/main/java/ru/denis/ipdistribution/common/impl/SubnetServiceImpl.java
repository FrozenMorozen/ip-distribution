package ru.denis.ipdistribution.common.impl;

import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.common.exception.OutOfIpRangeException;
import ru.denis.ipdistribution.common.service.SubnetService;
import ru.denis.ipdistribution.common.service.SubnetValidateService;

@Service
public class SubnetServiceImpl implements SubnetService {

  private SubnetValidateService subnetValidateService;


  @Autowired
  public SubnetServiceImpl(SubnetValidateService subnetValidateService) {
    this.subnetValidateService = subnetValidateService;
  }

  @Override
  public String getDeviceIp(SubnetUtils subnet, String globalNetworkMask) {

    // Первый доступный для хоста адрес в подсети
    String newDeviceIp = subnet.getInfo().getLowAddress();
    try {
      subnetValidateService.containsIpInGlobalNetwork(newDeviceIp, globalNetworkMask);
    } catch (OutOfIpRangeException ex) {
      throw new OutOfIpRangeException(String.format("IP для следующего устройства: '%s' не входит в диапазон '%s'", newDeviceIp, globalNetworkMask), ex);
    }
    return newDeviceIp;
  }

  @Override
  public SubnetUtils getSubnetForDeviceIp(String deviceIp, String rangeMask) {
    SubnetUtils subnet = new SubnetUtils(deviceIp + rangeMask);
    try {
      subnetValidateService.isItDeviceIpForSubnet(deviceIp, subnet);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(
              String.format("IP: '%1$s' не является ip устройства. " +
                              "Для подсети(%2$s), в который входит '%1$s', возможен следующий IP устройства = %3$s",
                      deviceIp, rangeMask, subnet.getInfo().getLowAddress()), ex);
    }
    return subnet;
  }

  @Override
  public void checkDeviceIp(String deviceIp, String globalNetworkMask) {
    subnetValidateService.validateIpFormat(deviceIp);
    try {
      subnetValidateService.containsIpInGlobalNetwork(deviceIp, globalNetworkMask);
    } catch (OutOfIpRangeException ex) {
      throw new OutOfIpRangeException(String.format("IP: '%s' не входит в диапазон %s", deviceIp, globalNetworkMask), ex);
    }
  }
}
