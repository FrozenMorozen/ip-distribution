package ru.denis.ipdistribution.business.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:business.properties")
@Getter
@Setter
public class BusinessConfiguration {

  @Value("${global.subnet.IP.with.CIDR}")
  private String globalSubnetIpWithCidr;

  @Value("${device.IP.range.mask}")
  private String deviceIpRangeMask;
}
