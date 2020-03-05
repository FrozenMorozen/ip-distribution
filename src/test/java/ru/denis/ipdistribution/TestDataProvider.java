package ru.denis.ipdistribution;

import ru.denis.ipdistribution.business.configuration.BusinessConfiguration;
import ru.denis.ipdistribution.executable.impl.apache.comons.net.DeviceIpServiceImpl;
import ru.denis.ipdistribution.executable.impl.apache.comons.net.IpCalcServiceImpl;
import ru.denis.ipdistribution.executable.service.DeviceIpService;

public class TestDataProvider {

  public static final String GLOBAL_NETWORK_STRING;
  public static final String DEVICE_RANGE_MASK;

  public static BusinessConfiguration businessConfiguration = new BusinessConfiguration();
  public static DeviceIpService deviceIpService;

  static {
    businessConfiguration.setGlobalNetworkMask("172.28.0.0/16");
    businessConfiguration.setDeviceIpRangeMask("/30");
    deviceIpService = new DeviceIpServiceImpl(new IpCalcServiceImpl());

    GLOBAL_NETWORK_STRING = businessConfiguration.getGlobalNetworkMask();
    DEVICE_RANGE_MASK = businessConfiguration.getDeviceIpRangeMask();
  }

}
