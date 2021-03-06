package ru.denis.ipdistribution.executable.apache.comons.net.impl;

import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ru.denis.ipdistribution.executable.apache.comons.net.service.IpCalcService;
import ru.denis.ipdistribution.executable.common.service.DeviceIpService;

public class DeviceIpServiceImpl implements DeviceIpService {

  private IpCalcService ipCalcService;

  @Autowired
  public DeviceIpServiceImpl(IpCalcService ipCalcService) {
    this.ipCalcService = ipCalcService;
  }

  @Override
  public String getNextDeviceIp(String previousDeviceIp, String rangeMask) {
    SubnetUtils previousSubnet = new SubnetUtils(previousDeviceIp + rangeMask);
    String nextNetworkIp = ipCalcService.addBitsToIp(previousSubnet.getInfo().getBroadcastAddress(),1);
    return new SubnetUtils(nextNetworkIp + rangeMask).getInfo().getLowAddress();
  }
}
