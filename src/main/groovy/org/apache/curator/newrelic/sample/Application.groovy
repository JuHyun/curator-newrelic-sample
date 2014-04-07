package org.apache.curator.newrelic.sample

import org.apache.curator.framework.CuratorFramework
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*
import org.springframework.stereotype.*
import org.springframework.web.bind.annotation.*

@Controller
@Import([ApplicationConfiguration])
@EnableAutoConfiguration
public class Application {

    @Autowired
    private CuratorFramework curatorFramework

    @RequestMapping('/')
    @ResponseBody
    String home() {
		'Sample Apache Curator NewRelic integration test application.'
    }

    @RequestMapping('/test')
    @ResponseBody
    String test() {
        new String(curatorFramework.getData().forPath('/'))
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application, args)
    }
}
