package ru.denis.ipdistribution.executable.seancfoley.ipaddress.impl;

import inet.ipaddr.IPAddressString;
import inet.ipaddr.ipv4.IPv4Address;
import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.executable.common.exception.OutOfIpRangeException;
import ru.denis.ipdistribution.executable.common.service.SubnetValidateService;

import java.util.stream.Collectors;

@Service
public class SubnetValidateServiceImpl implements SubnetValidateService {

  @Override
  public void containsIpInNetwork(String ip, String networkString) {
    IPv4Address network = new IPAddressString(networkString).getAddress().toIPv4().toPrefixBlock();
    IPv4Address ipAddress = new IPAddressString(ip).getAddress().toIPv4();
    if (!network.contains(ipAddress)) {
      throw new OutOfIpRangeException(String.format("IP: '%s' не входит в сеть '%s'", ip, networkString));
    }
  }

  @Override
  public void isItDeviceIpForSubnet(String ipForCheck, String subnetMask) {
    IPv4Address inputAddress = new IPAddressString(ipForCheck).getAddress().toIPv4();
    IPv4Address subnet = new IPAddressString(ipForCheck + subnetMask).getAddress().toIPv4().toPrefixBlock();
    String subnetDeviceIp = subnet.toSequentialRange().stream().collect(Collectors.toList()).get(1).toString();

    // Является ли входящий ip первым хостом(ip устройства) в своей подсети
    if (!subnetDeviceIp.equals(inputAddress.toString())) {
      throw new IllegalArgumentException(
              String.format("IP: '%1$s' не является ip устройства(первым хостом в своей подсети). " +
                              "Для подсети(%2$s), в которую входит '%1$s', возможен следующий IP устройства = %3$s",
                      ipForCheck, subnet.toString(), subnetDeviceIp));
    }
  }

  @Override
  public void validateIpFormat(String ipAddress) {
    // Пока не разбирался с валидацией чистого IP в либе IPAddress
    return;
  }
}
