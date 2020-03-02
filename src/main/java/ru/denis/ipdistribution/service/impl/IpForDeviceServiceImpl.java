package ru.denis.ipdistribution.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.exception.OutOfIpRangeException;
import ru.denis.ipdistribution.service.IpForDeviceService;
import ru.denis.ipdistribution.service.SubnetService;

@Service
@Slf4j
public class IpForDeviceServiceImpl implements IpForDeviceService {

  private static final String RANGE_MASK = "/30";
  private static final String AVAILABLE_IP_RANGE = "172.28.0.0/16";
  private static final SubnetUtils AVAILABLE_IPS_SUBNET = new SubnetUtils(AVAILABLE_IP_RANGE);

  private SubnetService subnetService;

  @Autowired
  public IpForDeviceServiceImpl(SubnetService subnetService) {
    this.subnetService = subnetService;
  }

  @Override
  public String getIpForNextDevice(String previousDeviceIp) throws OutOfIpRangeException, IllegalArgumentException {
    subnetService.validateIp(previousDeviceIp);
    if (!AVAILABLE_IPS_SUBNET.getInfo().isInRange(previousDeviceIp)) {
      log.error(String.format("Полученный ip: \"%s\" не входит в диапазон %s", previousDeviceIp, AVAILABLE_IP_RANGE));
      throw new OutOfIpRangeException();
    }

    // Подсеть для previousDeviceIp
    SubnetUtils previousSubnet = new SubnetUtils(previousDeviceIp + RANGE_MASK);

    // ip адрес для следующей(новой) подсети
    String newSubnetIp = subnetService.getNextIpAddress(previousSubnet.getInfo().getBroadcastAddress());

    // Первый доступный для хоста адрес в новой подсети
    String ipForNewDevice = new SubnetUtils(newSubnetIp + RANGE_MASK).getInfo().getLowAddress();

    if (!AVAILABLE_IPS_SUBNET.getInfo().isInRange(ipForNewDevice)) {
      log.error(String.format("Полученный ip: \"%s\" не входит в диапазон %s", ipForNewDevice, AVAILABLE_IP_RANGE));
      throw new OutOfIpRangeException();
    }
    return ipForNewDevice;
  }

}
