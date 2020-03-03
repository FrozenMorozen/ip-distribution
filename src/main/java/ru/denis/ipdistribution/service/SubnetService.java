package ru.denis.ipdistribution.service;

import org.apache.commons.net.util.SubnetUtils;

/**
 * Сервис для управления подсетями
 */
public interface SubnetService {

//  String convertIpFromInt(int netValue);

//  int convertIntFromIp(String address);

//  String getNextIpAddress(String previousIpAddress);


//  SubnetUtils getNextSubnet(SubnetUtils previousSubnet);

//  String getNextIp(SubnetUtils previousSubnet, SubnetUtils.SubnetInfo globalSubnetInfo);

  void checkDeviceIp(String deviceIp);

  String getNextDeviceIp(SubnetUtils previousSubnet);

  SubnetUtils createRangeSubnetForIp(String ip);

//  void validateAndCheckIpForSubnet(String ip, SubnetUtils.SubnetInfo subnetInfo);
//
//  void containsIpInSubnet(String ip, SubnetUtils.SubnetInfo subnet);
//
//  void checkDeviceIpForSubnet(String deviceIp, SubnetUtils subnet);
}
