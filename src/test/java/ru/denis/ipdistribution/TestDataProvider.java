package ru.denis.ipdistribution;

import ru.denis.ipdistribution.configuration.BusinessConfiguration;
import ru.denis.ipdistribution.service.SubnetService;
import ru.denis.ipdistribution.service.impl.SubnetServiceImpl;
import ru.denis.ipdistribution.service.impl.SubnetValidateServiceImpl;

public class TestDataProvider {

  public static BusinessConfiguration businessConfiguration = new BusinessConfiguration();
  public static SubnetService subnetService;

  static {
    businessConfiguration.setIpGlobalRange("172.28.0.0/16");
    businessConfiguration.setIpPickRangeValue("/30");
    subnetService = new SubnetServiceImpl(new SubnetValidateServiceImpl(), businessConfiguration);
  }

}
