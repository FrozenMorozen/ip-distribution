package ru.denis.ipdistribution.service.impl;

import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.service.IpMaskService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IpMaskServiceImpl implements IpMaskService {

  private static final String IP_ADDRESS = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
  private static final Pattern addressPattern = Pattern.compile(IP_ADDRESS);

  private int[] toArray(int val) {
    int[] ret = new int[4];
    for (int j = 3; j >= 0; --j) {
      ret[j] |= ((val >>> 8 * (3 - j)) & (0xff));
    }
    return ret;
  }

  private int pop(int x) {
    x = x - ((x >>> 1) & 0x55555555);
    x = (x & 0x33333333) + ((x >>> 2) & 0x33333333);
    x = (x + (x >>> 4)) & 0x0F0F0F0F;
    x = x + (x >>> 8);
    x = x + (x >>> 16);
    return x & 0x0000003F;
  }

  private int rangeCheck(int value) {
    if (value >= 0 && value <= 255) { // (begin,end]
      return value;
    }
    throw new IllegalArgumentException("Value [" + value + "] not in range [" + 0 + "," + 255 + "]");
  }

  private String convertIpFromInt(int netValue) {
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

  private int convertIntFromIp(String address) {
    Matcher matcher = addressPattern.matcher(address);
    int addr = 0;
    matcher.matches();
    for (int i = 1; i <= 4; ++i) {
      int n = (rangeCheck(Integer.parseInt(matcher.group(i))));
      addr |= ((n & 0xff) << 8 * (4 - i));
    }
    return addr;
  }

  @Override
  public String getNetworkMaskFromIp(String ip) {
    return "/" + pop(convertIntFromIp(ip));
  }

  @Override
  public String addBitsToIp(String ip, int bitForAdd) {
    return convertIpFromInt(convertIntFromIp(ip) + bitForAdd);
  }

}
