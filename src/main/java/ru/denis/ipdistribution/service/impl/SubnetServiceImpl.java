package ru.denis.ipdistribution.service.impl;

import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.service.SubnetService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
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

//  private int matchAddress(Matcher matcher) {
//    int addr = 0;
//    for (int i = 1; i <= 4; ++i) {
//      int n = (rangeCheck(Integer.parseInt(matcher.group(i)), 0, 255));
//      addr |= ((n & 0xff) << 8*(4-i));
//    }
//    return addr;
//  }

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
    validateIp(address);

    Matcher matcher = addressPattern.matcher(address);
    int addr = 0;
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
  public void validateIp(String ip) throws IllegalArgumentException {
    Matcher matcher = addressPattern.matcher(ip);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Could not parse [" + ip + "]");
    }
  }
}
