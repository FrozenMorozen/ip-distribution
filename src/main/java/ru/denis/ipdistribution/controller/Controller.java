package ru.denis.ipdistribution.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.denis.ipdistribution.exception.controller.InvalidIpException;
import ru.denis.ipdistribution.exception.controller.NotFoundException;
import ru.denis.ipdistribution.exception.service.OutOfIpRangeException;
import ru.denis.ipdistribution.service.IpForDeviceService;

@RestController
@RequestMapping("getnextip")
@Slf4j
public class Controller {

  private IpForDeviceService ipForDeviceService;

  @Autowired
  public Controller(IpForDeviceService ipForDeviceService) {
    this.ipForDeviceService = ipForDeviceService;
  }

  @GetMapping("/{ip}")
  public String getNextIpForPrevious(@PathVariable("ip") String ip) {
    try {
      String ipForNextDevice = ipForDeviceService.getIpForNextDevice(ip);
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
