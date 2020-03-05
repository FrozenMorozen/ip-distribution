package ru.denis.ipdistribution;

import ru.denis.ipdistribution.business.configuration.BusinessConfiguration;
import ru.denis.ipdistribution.executable.apache.comons.net.impl.DeviceIpServiceImpl;
import ru.denis.ipdistribution.executable.apache.comons.net.impl.IpCalcServiceImpl;
import ru.denis.ipdistribution.executable.common.service.DeviceIpService;

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
