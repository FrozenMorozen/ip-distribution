package ru.denis.ipdistribution.business.service.Impl;

import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;
import inet.ipaddr.ipv4.IPv4Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.business.configuration.BusinessConfiguration;
import ru.denis.ipdistribution.business.service.BusinessDeviceIpService;

import java.util.stream.Collectors;

@Service
public class DeviceServiceWithIpAddressLibImpl implements BusinessDeviceIpService {

  private BusinessConfiguration configuration;

  @Autowired
  public DeviceServiceWithIpAddressLibImpl(BusinessConfiguration businessConfiguration) {
    this.configuration = businessConfiguration;
  }

  @Override
  public String getIpForNextDevice(String inputIp) {
    int prefixLength = Integer.parseInt(configuration.getDeviceIpRangeMask().substring(1));
    IPAddress globalSubnet = new IPAddressString(configuration.getGlobalNetworkMask()).getAddress().toPrefixBlock();
    IPv4Address inputAddress = new IPAddressString(inputIp).getAddress().toIPv4();
    if (!globalSubnet.contains(inputAddress)) {
      return null;
    }

    // Подсеть для входящего ip
    IPv4Address inputAddressRange = new IPAddressString(inputIp + configuration.getDeviceIpRangeMask()).getAddress().toIPv4().toPrefixBlock();

    // Является ли входящий ip первым хостом(ip устройства) в своей подсети
    if (!inputAddressRange.toSequentialRange().stream().collect(Collectors.toList()).get(1).toString().equals(inputAddress.toString())) {
      return null;
    }

    // ip устройства в следующей подсети (ip первого хоста в подсети)
    IPv4Address newDeviceAddress = inputAddressRange.toSequentialRange().getUpper().increment(1).setPrefixLength(30).stream().collect(Collectors.toList()).get(1);

    // Проверить, принадлжеит ли ip устройства глобальной подсети
    if (!globalSubnet.contains(newDeviceAddress)) {
      return null;
    }

    return newDeviceAddress.toNormalizedWildcardString();
  }
}
