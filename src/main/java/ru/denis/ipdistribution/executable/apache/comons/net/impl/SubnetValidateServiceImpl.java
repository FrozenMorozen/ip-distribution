package ru.denis.ipdistribution.executable.apache.comons.net.impl;

import org.apache.commons.net.util.SubnetUtils;
import ru.denis.ipdistribution.executable.common.exception.OutOfIpRangeException;
import ru.denis.ipdistribution.executable.common.service.SubnetValidateService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubnetValidateServiceImpl implements SubnetValidateService {

  private static final String IP_ADDRESS = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
  private static final Pattern addressPattern = Pattern.compile(IP_ADDRESS);

  @Override
  public void containsIpInNetwork(String ip, String networkString) {
    SubnetUtils network = new SubnetUtils(networkString);
    network.setInclusiveHostCount(true);
    if (!network.getInfo().isInRange(ip)) {
      throw new OutOfIpRangeException(String.format("IP: '%s' не входит в сеть '%s'", ip, networkString));
    }
  }

  @Override
  public void isItDeviceIpForSubnet(String ipForCheck, String subnetMask) {
    SubnetUtils subnet = new SubnetUtils(ipForCheck + subnetMask);
    String deviceIpForSubnet = subnet.getInfo().getLowAddress();
    if (!deviceIpForSubnet.equals(ipForCheck)) {
      throw new IllegalArgumentException(
              String.format("IP: '%1$s' не является ip устройства(первым хостом в своей подсети). " +
                              "Для подсети(%2$s), в которую входит '%1$s', возможен следующий IP устройства = %3$s",
                      ipForCheck, subnet.getInfo().getCidrSignature(), subnet.getInfo().getLowAddress()));
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
