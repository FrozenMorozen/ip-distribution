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

  private String rangeMask;
  private String globalRange;
  private SubnetUtils.SubnetInfo globalSubnetInfo;
  private SubnetService subnetService;

  @Autowired
  public IpForDeviceServiceImpl(SubnetService subnetService, BusinessConfiguration businessConfiguration) {
    this.subnetService = subnetService;
    globalRange = businessConfiguration.getIpGlobalRange();
    globalSubnetInfo = new SubnetUtils(globalRange).getInfo();
    rangeMask = businessConfiguration.getIpPickRangeValue();
  }

  @Override
  public String getIpForNextDevice(String previousDeviceIp) throws OutOfIpRangeException, IllegalArgumentException {
    subnetService.validateIp(previousDeviceIp);
    if (!globalSubnetInfo.isInRange(previousDeviceIp)) {
      throw new OutOfIpRangeException(String.format("IP: '%s' не входит в диапазон %s", previousDeviceIp, globalRange));
    }

    // Подсеть для previousDeviceIp
    SubnetUtils previousSubnet = new SubnetUtils(previousDeviceIp + rangeMask);
    if (!previousSubnet.getInfo().getLowAddress().equals(previousDeviceIp)) {
      throw new IllegalArgumentException(
              String.format("Полученный ip: '%1$s' не является ip устройства. " +
                              "Для подсети(%2$s), в который входит '%1$s', возможен следующий IP устройства = %3$s",
                      previousDeviceIp, rangeMask, previousSubnet.getInfo().getLowAddress()));
    }

    // Следущая для previousSubnet подсеть
    SubnetUtils nextSubnet = subnetService.getNextSubnet(previousSubnet);

    // Первый доступный для хоста адрес в новой подсети
    String newDeviceIp = nextSubnet.getInfo().getLowAddress();

    if (!globalSubnetInfo.isInRange(newDeviceIp)) {
      throw new OutOfIpRangeException(
              String.format("IP для следующего устройства: '%s' не входит в диапазон %s", newDeviceIp, globalRange));
    }
    return newDeviceIp;
  }

}
