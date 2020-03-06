package ru.denis.ipdistribution;

import ru.denis.ipdistribution.business.configuration.BusinessConfiguration;
import ru.denis.ipdistribution.executable.common.service.DeviceIpService;
import ru.denis.ipdistribution.executable.seancfoley.ipaddress.impl.DeviceIpServiceImpl;

public class TestDataProvider {

  public static final String GLOBAL_NETWORK_STRING;
  public static final String DEVICE_RANGE_MASK;

  public static BusinessConfiguration businessConfiguration = new BusinessConfiguration();
  public static DeviceIpService deviceIpService;

  static {
    businessConfiguration.setGlobalSubnetIpWithCidr("172.28.0.0/16");
    businessConfiguration.setDeviceIpRangeMask("/30");
//    deviceIpService = new DeviceIpServiceImpl(new IpCalcServiceImpl());
    deviceIpService = new DeviceIpServiceImpl();

    GLOBAL_NETWORK_STRING = businessConfiguration.getGlobalSubnetIpWithCidr();
    DEVICE_RANGE_MASK = businessConfiguration.getDeviceIpRangeMask();
  }

}
