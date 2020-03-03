package ru.denis.ipdistribution.common.impl;

import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.common.service.IpMaskService;
import ru.denis.ipdistribution.common.service.SubnetCalculatorService;

@Service
public class SubnetCalculatorServiceImpl implements SubnetCalculatorService {

  private IpMaskService ipMaskService;

  @Autowired
  public SubnetCalculatorServiceImpl(IpMaskService ipMaskService) {
    this.ipMaskService = ipMaskService;
  }

  @Override
  public SubnetUtils getNextSubnetForPrevious(SubnetUtils previousSubnet) {
    String nextNetworkIp = ipMaskService.addBitsToIp(previousSubnet.getInfo().getBroadcastAddress(),1);
    String subnetRange = ipMaskService.getNetworkMaskFromIp(previousSubnet.getInfo().getNetmask());
    return new SubnetUtils(nextNetworkIp + subnetRange);
  }
}
