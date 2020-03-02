package ru.denis.ipdistribution.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.configuration.BusinessConfiguration;
import ru.denis.ipdistribution.exception.OutOfIpRangeException;
import ru.denis.ipdistribution.service.IpForDeviceService;
import ru.denis.ipdistribution.service.SubnetService;

@Service
@Slf4j
public class IpForDeviceServiceImpl implements IpForDeviceService {

  private String rangeMask;
  private static final String AVAILABLE_IP_RANGE = "172.28.0.0/16";
  private static final SubnetUtils.SubnetInfo AVAILABLE_IP_SUBNET = new SubnetUtils(AVAILABLE_IP_RANGE).getInfo();

  private SubnetUtils.SubnetInfo globalSubnetInfo;
  private SubnetService subnetService;

  @Autowired
  public IpForDeviceServiceImpl(SubnetService subnetService, BusinessConfiguration businessConfiguration) {
    this.subnetService = subnetService;
    globalSubnetInfo = new SubnetUtils(businessConfiguration.getIpRange()).getInfo();
    rangeMask = businessConfiguration.getIpPickRangeValue();

  }

  @Override
  public String getIpForNextDevice(String previousDeviceIp) throws OutOfIpRangeException, IllegalArgumentException {
    subnetService.validateIp(previousDeviceIp);
    try {
      subnetContainsIp(globalSubnetInfo, previousDeviceIp);
    } catch (OutOfIpRangeException ex) {
      log.error(String.format("Полученный ip: \"%s\" не входит в диапазон %s", previousDeviceIp, AVAILABLE_IP_RANGE));
    }

    // Подсеть для previousDeviceIp
    SubnetUtils previousSubnet = new SubnetUtils(previousDeviceIp + rangeMask);

    // ip адрес для следующей(новой) подсети
    String newSubnetIp = subnetService.getNextIpAddress(previousSubnet.getInfo().getBroadcastAddress());
//    String newSubnetIp = previousSubnet.format(previousSubnet.toArray(previousSubnet.toInteger(previousSubnet.getInfo().getBroadcastAddress()) + 1));

    // Первый доступный для хоста адрес в новой подсети
    String newDeviceIp = new SubnetUtils(newSubnetIp + rangeMask).getInfo().getLowAddress();
    try {
      subnetContainsIp(globalSubnetInfo, newDeviceIp);
    } catch (OutOfIpRangeException ex) {
      log.error(String.format("IP для следующего устройства: \"%s\" не входит в диапазон %s", newDeviceIp, AVAILABLE_IP_RANGE));
    }
    return newDeviceIp;
  }

  private void subnetContainsIp(SubnetUtils.SubnetInfo subnetInfo, String ipForCheck) throws OutOfIpRangeException {
    if (!subnetInfo.isInRange(ipForCheck)) {
      throw new OutOfIpRangeException();
    }
  }

}
