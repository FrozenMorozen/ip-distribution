package ru.denis.ipdistribution.executable.seancfoley.ipaddress.impl;

import inet.ipaddr.IPAddressString;
import inet.ipaddr.ipv4.IPv4Address;
import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.executable.common.service.DeviceIpService;

import java.util.stream.Collectors;

@Service
public class DeviceIpServiceImpl implements DeviceIpService {

  @Override
  public String getNextDeviceIp(String previousDeviceIp, String rangeMask) {
    IPv4Address inputAddressRange = new IPAddressString(previousDeviceIp + rangeMask).getAddress().toIPv4().toPrefixBlock();
    return inputAddressRange.toSequentialRange().getUpper()
            .increment(1)
            .setPrefixLength(Integer.parseInt(rangeMask.substring(1)))
            .stream()
            .collect(Collectors.toList()).get(1).toCanonicalWildcardString();
  }
}
