package ru.denis.ipdistribution;

import ru.denis.ipdistribution.configuration.BusinessConfiguration;
import ru.denis.ipdistribution.service.SubnetService;
import ru.denis.ipdistribution.service.impl.SubnetServiceImpl;
import ru.denis.ipdistribution.service.impl.SubnetValidateServiceImpl;

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
    subnetService = new SubnetServiceImpl(new SubnetValidateServiceImpl());
  }

}
