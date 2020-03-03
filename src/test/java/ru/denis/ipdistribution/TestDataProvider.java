package ru.denis.ipdistribution;

import ru.denis.ipdistribution.common.impl.IpCalcServiceImpl;
import ru.denis.ipdistribution.common.impl.SubnetServiceImpl;
import ru.denis.ipdistribution.common.service.SubnetService;
import ru.denis.ipdistribution.configuration.BusinessConfiguration;

public class TestDataProvider {

  public static final String GLOBAL_NETWORK_MASK;
  public static final String DEVICE_RANGE_MASK;

  public static BusinessConfiguration businessConfiguration = new BusinessConfiguration();
  public static SubnetService subnetService;

  static {
    businessConfiguration.setGlobalNetworkMask("172.28.0.0/16");
    GLOBAL_NETWORK_MASK = businessConfiguration.getGlobalNetworkMask();
    businessConfiguration.setDeviceIpRangeMask("/30");
    DEVICE_RANGE_MASK = businessConfiguration.getDeviceIpRangeMask();
    subnetService = new SubnetServiceImpl(new IpCalcServiceImpl());
  }

}
