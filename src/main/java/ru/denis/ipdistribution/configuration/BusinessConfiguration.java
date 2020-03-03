package ru.denis.ipdistribution.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@Getter
@Setter
public class BusinessConfiguration {

  @Value("${ip.global.range}")
  private String ipGlobalRange;

  @Value("${ip.pick.range.value}")
  private String ipPickRangeValue;
}
