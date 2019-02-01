package local.test;

import org.junit.Test;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class CommonTest {

	
	@Test
	public void redisTest() {
		
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(100);
		config.setMinIdle(10);
		config.setTestOnBorrow(true);
		
		JedisPool pool = new JedisPool(config, "192.168.233.128", 6379, 5000);
		
		pool.getResource().set("key".getBytes(), "hello".getBytes());
		
		System.out.println(pool.getResource().get("key"));
		
	}
	
}
