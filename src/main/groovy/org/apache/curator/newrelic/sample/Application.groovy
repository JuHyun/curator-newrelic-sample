package org.apache.curator.newrelic.sample

import org.apache.curator.framework.CuratorFramework
import org.apache.zookeeper.data.Stat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Import
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

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

    @RequestMapping(value='/zk/exists/{path}', method=RequestMethod.GET)
    @ResponseBody
    Boolean checkExists(@PathVariable("path") String path) {
        Stat result = curatorFramework.checkExists().forPath(formatPath(path))
        result != null
    }

    @RequestMapping(value='/zk/data/{path}', method=RequestMethod.POST)
    @ResponseBody
    void create(@PathVariable("path") String path, @RequestBody data) {
        curatorFramework.create().forPath(formatPath(path), data ? data.getBytes() : ''.getBytes());
        formatPath(path)
    }

    @RequestMapping(value='/zk/data/{path}', method=RequestMethod.DELETE)
    @ResponseBody
    void delete(@PathVariable("path") String path) {
        curatorFramework.delete().forPath(formatPath(path));
    }

    @RequestMapping(value='/zk/data/{path}', method=RequestMethod.GET)
    @ResponseBody
    String getData(@PathVariable("path") String path) {
        new String(curatorFramework.getData().forPath(formatPath(path)))
    }

    @RequestMapping(value='/zk/data/{path}', method=RequestMethod.PUT)
    @ResponseBody
    void putData(@PathVariable("path") String path, @RequestBody String data) {
        curatorFramework.setData().forPath(formatPath(path), data.getBytes())
    }

    @RequestMapping(value='/zk/version/{path}', method=RequestMethod.GET)
    @ResponseBody
    Boolean getVersion(@PathVariable("path") String path) {
        Stat result = curatorFramework.checkExists().forPath(formatPath(path))
        result.version
    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application, args)
    }

    private String formatPath(String path) {
        path.startsWith('/') ?: "/${path}"
    }
}
