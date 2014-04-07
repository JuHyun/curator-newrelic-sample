package org.apache.curator.newrelic.sample

import org.apache.curator.framework.CuratorFramework
import org.apache.curator.newrelic.framework.NewRelicClientFrameworkFactory
import org.apache.curator.retry.RetryOneTime
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration {

	@Bean
	public CuratorFramework curatorFramework() {
		CuratorFramework client = NewRelicClientFrameworkFactory.newClient('localhost:2181', new RetryOneTime(10))
		client.start()
		client
	}
}