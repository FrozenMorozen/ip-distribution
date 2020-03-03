package ru.denis.ipdistribution.service.impl;

import org.apache.commons.net.util.SubnetUtils;
import org.springframework.stereotype.Service;
import ru.denis.ipdistribution.exception.service.OutOfIpRangeException;
import ru.denis.ipdistribution.service.SubnetValidateService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SubnetValidateServiceImpl implements SubnetValidateService {

  private static final String IP_ADDRESS = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
  private static final Pattern addressPattern = Pattern.compile(IP_ADDRESS);

  @Override
  public void containsIpInGlobalRange(String ip, String globalSubnet) {
    SubnetUtils.SubnetInfo subnet = new SubnetUtils(globalSubnet).getInfo();
    if (!subnet.isInRange(ip)) {
      throw new OutOfIpRangeException();
    }
  }

  @Override
  public void itIsDeviceIpForSubnet(String ipForCheck, SubnetUtils subnet) {
    String deviceIpForSubnet = subnet.getInfo().getLowAddress();
    if (!deviceIpForSubnet.equals(ipForCheck)) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public void validateIpFormat(String ipAddress) {
    Matcher matcher = addressPattern.matcher(ipAddress);
    if (!matcher.matches()) {
      throw new IllegalArgumentException(String.format("Некорректный IP: '%s'", ipAddress));
    }
  }
}
