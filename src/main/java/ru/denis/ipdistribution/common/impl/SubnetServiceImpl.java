package ru.denis.ipdistribution.common.impl;

import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.common.service.IpCalcService;
import ru.denis.ipdistribution.common.service.SubnetService;

@Service
public class SubnetServiceImpl implements SubnetService {

  private IpCalcService ipCalcService;

  @Autowired
  public SubnetServiceImpl(IpCalcService ipCalcService) {
    this.ipCalcService = ipCalcService;
  }

  @Override
  public SubnetUtils getNextSubnet(SubnetUtils previousSubnet, String rangeMask) {
    String nextNetworkIp = ipCalcService.addBitsToIp(previousSubnet.getInfo().getBroadcastAddress(),1);
    return new SubnetUtils(nextNetworkIp + rangeMask);
  }
}
