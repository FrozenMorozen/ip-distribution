package ru.denis.ipdistribution.service;

import org.apache.commons.net.util.SubnetUtils;

/**
 *
 */
public interface SubnetService {

  String convertIpFromInt(int netValue);

  int convertIntFromIp(String address);

  String getNextIpAddress(String previousIpAddress);


  SubnetUtils getNextSubnet(SubnetUtils previousSubnet);

  void validateIp(String ip);
}
