package ru.denis.ipdistribution.service;

/**
 *
 */
public interface SubnetService {

  String convertIpFromInt(int netValue);

  int convertIntFromIp(String address);

  String getNextIpAddress(String previousIpAddress);

  void validateIp(String ip);
}
