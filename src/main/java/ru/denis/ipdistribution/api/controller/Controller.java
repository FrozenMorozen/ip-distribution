package ru.denis.ipdistribution.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.denis.ipdistribution.api.exception.InvalidIpException;
import ru.denis.ipdistribution.api.exception.NotFoundException;
import ru.denis.ipdistribution.business.service.BusinessDeviceIpService;
import ru.denis.ipdistribution.executable.common.exception.OutOfIpRangeException;

@RestController
@RequestMapping("getnextip")
@Slf4j
public class Controller {

  private BusinessDeviceIpService businessDeviceIpService;

  @Autowired
  public Controller(BusinessDeviceIpService businessDeviceIpService) {
    this.businessDeviceIpService = businessDeviceIpService;
  }

  @GetMapping("/{ip}")
  public String getNextIpForPrevious(@PathVariable("ip") String ip) {
    try {
      String ipForNextDevice = businessDeviceIpService.getIpForNextDevice(ip);
      log.info(String.format("IP для следущего утройства = %s", ipForNextDevice));
      return ipForNextDevice + "\n";

    } catch (IllegalArgumentException ex) {
      log.error(ex.getMessage());
      throw new InvalidIpException(ex.getMessage());

    } catch (OutOfIpRangeException ex) {
      log.error(ex.getMessage());
      throw new NotFoundException(ex.getMessage());
    }
  }

}
