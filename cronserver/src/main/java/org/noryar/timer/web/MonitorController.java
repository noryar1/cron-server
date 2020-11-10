package org.noryar.timer.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 监控类
 *
 * @author noryar
 */
@RestController
@RequestMapping("monitor")
public class MonitorController {

    @RequestMapping("ok")
    public ResponseEntity ok() {
        return new ResponseEntity("cron server is started", HttpStatus.OK);
    }
}
