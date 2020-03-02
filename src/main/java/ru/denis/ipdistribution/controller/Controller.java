package ru.denis.ipdistribution.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.denis.ipdistribution.exception.OutOfIpRangeException;
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
      return ipForDeviceService.getIpForNextDevice(ip);
    } catch (IllegalArgumentException ex) {
      // Обработать, что некорректные данные
      log.error(String.format("Передан некорректный параметр %s", ip));
      return null;
    } catch (OutOfIpRangeException ex) {
      // Обработать, что данных не найдено
      return null;
    }
  }

  //POST
  //DELET
  //PUT

}
