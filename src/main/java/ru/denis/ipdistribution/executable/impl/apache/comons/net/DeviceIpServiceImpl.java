package ru.denis.ipdistribution.executable.impl.apache.comons.net;

import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.executable.service.DeviceIpService;
import ru.denis.ipdistribution.executable.service.apache.commons.net.IpCalcService;

@Service
public class DeviceIpServiceImpl implements DeviceIpService {

  private IpCalcService ipCalcService;

  @Autowired
  public DeviceIpServiceImpl(IpCalcService ipCalcService) {
    this.ipCalcService = ipCalcService;
  }

  @Override
  public SubnetUtils getNextSubnet(SubnetUtils previousSubnet, String rangeMask) {
    String nextNetworkIp = ipCalcService.addBitsToIp(previousSubnet.getInfo().getBroadcastAddress(),1);
    return new SubnetUtils(nextNetworkIp + rangeMask);
  }

  @Override
  public SubnetUtils getNextSubnet(String previousIp, String rangeMask) {
    SubnetUtils previousSubnet = new SubnetUtils(previousIp + rangeMask);
    String nextNetworkIp = ipCalcService.addBitsToIp(previousSubnet.getInfo().getBroadcastAddress(),1);
    return new SubnetUtils(nextNetworkIp + rangeMask);
  }

  @Override
  public String getNextDeviceIp(String previousDeviceIp, String rangeMask) {
    SubnetUtils previousSubnet = new SubnetUtils(previousDeviceIp + rangeMask);
    String nextNetworkIp = ipCalcService.addBitsToIp(previousSubnet.getInfo().getBroadcastAddress(),1);
    return new SubnetUtils(nextNetworkIp + rangeMask).getInfo().getLowAddress();
  }
}
