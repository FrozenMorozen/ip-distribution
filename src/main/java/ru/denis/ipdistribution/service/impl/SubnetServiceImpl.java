package ru.denis.ipdistribution.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.util.SubnetUtils;
import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.service.SubnetService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class SubnetServiceImpl implements SubnetService {

  private static final String IP_ADDRESS = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
  //  private static final String SLASH_FORMAT = IP_ADDRESS + "/(\\d{1,3})";
  private static final Pattern addressPattern = Pattern.compile(IP_ADDRESS);

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

  @Override
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

  @Override
  public int convertIntFromIp(String address) {
    validateIpAddress(address);

    Matcher matcher = addressPattern.matcher(address);
    int addr = 0;
    matcher.matches();
//    matcher.groupCount();
    for (int i = 1; i <= 4; ++i) {
      int n = (rangeCheck(Integer.parseInt(matcher.group(i)), 0, 255));
      addr |= ((n & 0xff) << 8 * (4 - i));
    }
    return addr;
  }

  @Override
  public String getNextIpAddress(String previousIpAddress) {
    return convertIpFromInt(convertIntFromIp(previousIpAddress) + 1);
  }

  @Override
  public SubnetUtils getNextSubnet(SubnetUtils previousSubnet) {
    String nextNetworkIp = convertIpFromInt(convertIntFromIp(previousSubnet.getInfo().getBroadcastAddress()) + 1);
    int subnetRange = pop(convertIntFromIp(previousSubnet.getInfo().getNetmask()));
    return new SubnetUtils(nextNetworkIp + "/" + subnetRange);
  }

  @Override
  public void validateIp(String ip) throws IllegalArgumentException {
    validateIpAddress(ip);
  }

  private void validateIpAddress(String ipAddress) {
    Matcher matcher = addressPattern.matcher(ipAddress);
    if (!matcher.matches()) {
      String errorMessage = String.format("Передан некорректный IP: '%s'", ipAddress);
      throw new IllegalArgumentException(errorMessage);
    }
  }
}
