package ru.denis.ipdistribution.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.denis.ipdistribution.common.exception.OutOfIpRangeException;
import ru.denis.ipdistribution.controller.exception.InvalidIpException;
import ru.denis.ipdistribution.controller.exception.NotFoundException;
import ru.denis.ipdistribution.service.DeviceIpService;

@RestController
@RequestMapping("getnextip")
@Slf4j
public class Controller {

  private DeviceIpService deviceIpService;

  @Autowired
  public Controller(DeviceIpService deviceIpService) {
    this.deviceIpService = deviceIpService;
  }

  @GetMapping("/{ip}")
  public String getNextIpForPrevious(@PathVariable("ip") String ip) {
    try {
      String ipForNextDevice = deviceIpService.getIpForNextDevice(ip);
      log.info(String.format("IP для следующего утройства = %s", ipForNextDevice));
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
