package ru.denis.ipdistribution.service.impl;

import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.configuration.BusinessConfiguration;
import ru.denis.ipdistribution.exception.service.OutOfIpRangeException;
import ru.denis.ipdistribution.service.SubnetService;
import ru.denis.ipdistribution.service.SubnetValidateService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SubnetServiceImpl implements SubnetService {

  private static final String IP_ADDRESS = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
  private static final Pattern addressPattern = Pattern.compile(IP_ADDRESS);

  private SubnetValidateService subnetValidateService;
  private BusinessConfiguration businessConfiguration;

  @Autowired
  public SubnetServiceImpl(SubnetValidateService subnetValidateService, BusinessConfiguration businessConfiguration) {
    this.subnetValidateService = subnetValidateService;
    this.businessConfiguration = businessConfiguration;
  }

  private int[] toArray(int val) {
    int[] ret = new int[4];
    for (int j = 3; j >= 0; --j) {
      ret[j] |= ((val >>> 8 * (3 - j)) & (0xff));
    }
    return ret;
  }

  /*
   * Count the number of 1-bits in a 32-bit integer using a divide-and-conquer strategy
   * see Hacker's Delight section 5.1
   */
  int pop(int x) {
    x = x - ((x >>> 1) & 0x55555555);
    x = (x & 0x33333333) + ((x >>> 2) & 0x33333333);
    x = (x + (x >>> 4)) & 0x0F0F0F0F;
    x = x + (x >>> 8);
    x = x + (x >>> 16);
    return x & 0x0000003F;
  }

  private int rangeCheck(int value, int begin, int end) {
    if (value >= begin && value <= end) { // (begin,end]
      return value;
    }
    throw new IllegalArgumentException("Value [" + value + "] not in range [" + begin + "," + end + "]");
  }

  private String getRangeFromIp(String ip) {
    return "/" + pop(convertIntFromIp(ip));
  }

  //  @Override
  public String convertIpFromInt(int netValue) {
    int[] octets = toArray(netValue);
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < octets.length; ++i) {
      str.append(octets[i]);
      if (i != octets.length - 1) {
        str.append(".");
      }
    }
    return str.toString();
  }

  //  @Override
  public int convertIntFromIp(String address) {
    Matcher matcher = addressPattern.matcher(address);
    int addr = 0;
    matcher.matches();
    for (int i = 1; i <= 4; ++i) {
      int n = (rangeCheck(Integer.parseInt(matcher.group(i)), 0, 255));
      addr |= ((n & 0xff) << 8 * (4 - i));
    }
    return addr;
  }

  //  @Override
  public String getNextIpAddress(String previousIpAddress) {
    return convertIpFromInt(convertIntFromIp(previousIpAddress) + 1);
  }

  //  @Override
  public SubnetUtils getNextSubnet(SubnetUtils previousSubnet) {
    String nextNetworkIp = convertIpFromInt(convertIntFromIp(previousSubnet.getInfo().getBroadcastAddress()) + 1);
    String subnetRange = getRangeFromIp(previousSubnet.getInfo().getNetmask());
    return new SubnetUtils(nextNetworkIp + subnetRange);
  }

//  @Override
//  public String getNextIp(SubnetUtils previousSubnet, SubnetUtils.SubnetInfo globalSubnetInfo) throws OutOfIpRangeException {
//    String nextNetworkIp = convertIpFromInt(convertIntFromIp(previousSubnet.getInfo().getBroadcastAddress()) + 1);
//    String subnetRange = getRangeFromIp(previousSubnet.getInfo().getNetmask());
//    // Следущая для previousSubnet подсеть
//    SubnetUtils nextSubnet = new SubnetUtils(nextNetworkIp + subnetRange);
//
//    // Первый доступный для хоста адрес в новой подсети
//    String newDeviceIp = nextSubnet.getInfo().getLowAddress();
//    try {
//      subnetValidateService.containsIpInGlobalSubnet(newDeviceIp, globalSubnetInfo);
//    } catch (OutOfIpRangeException ex) {
//      throw new OutOfIpRangeException(String.format("IP для следующего устройства: '%s' не входит в диапазон '%s'", newDeviceIp, "globalRange"), ex);
//    }
//
//    return newDeviceIp;
//  }

  @Override
  public String getNextDeviceIp(SubnetUtils previousSubnet) {
    String nextNetworkIp = convertIpFromInt(convertIntFromIp(previousSubnet.getInfo().getBroadcastAddress()) + 1);
    String subnetRange = getRangeFromIp(previousSubnet.getInfo().getNetmask());
    // Следущая для previousSubnet подсеть
    SubnetUtils nextSubnet = new SubnetUtils(nextNetworkIp + subnetRange);

    String globalRange = businessConfiguration.getIpGlobalRange();

    // Первый доступный для хоста адрес в новой подсети
    String newDeviceIp = nextSubnet.getInfo().getLowAddress();
    try {
      subnetValidateService.containsIpInGlobalRange(newDeviceIp, globalRange);
    } catch (OutOfIpRangeException ex) {
      throw new OutOfIpRangeException(String.format("IP для следующего устройства: '%s' не входит в диапазон '%s'", newDeviceIp, globalRange), ex);
    }
    return newDeviceIp;
  }

  @Override
  public SubnetUtils createRangeSubnetForIp(String ip) {
    String rangeMask = businessConfiguration.getIpPickRangeValue();
    SubnetUtils subnet = new SubnetUtils(ip + rangeMask);
    try {
      subnetValidateService.itIsDeviceIpForSubnet(ip, subnet);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(
              String.format("IP: '%1$s' не является ip устройства. " +
                              "Для подсети(%2$s), в который входит '%1$s', возможен следующий IP устройства = %3$s",
                      ip, rangeMask, subnet.getInfo().getLowAddress()), ex);
    }
    return subnet;
  }

  @Override
  public void checkDeviceIp(String deviceIp) {
    subnetValidateService.validateIpFormat(deviceIp);
    try {
      subnetValidateService.containsIpInGlobalRange(deviceIp, businessConfiguration.getIpGlobalRange());
    } catch (OutOfIpRangeException ex) {
      throw new OutOfIpRangeException(String.format("IP: '%s' не входит в диапазон %s", deviceIp, businessConfiguration.getIpGlobalRange()), ex);
    }

  }
}
