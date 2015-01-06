/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gopivotal.cloudfoundry.test.core;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils extends AbstractServiceUtils<RedisConnectionFactory> {

    public String checkAccess(RedisConnectionFactory redisConnectionFactory) {
        try {
            RedisConnection connection = redisConnectionFactory.getConnection();
            connection.echo("hello".getBytes());
            return "ok";
        } catch (Exception e) {
            return "failed with " + e.getMessage();
        }
    }

    public String getUrl(RedisConnectionFactory redisConnectionFactory) {
        if (isClass(redisConnectionFactory, "com.gopivotal.cloudfoundry.test.core.FakeRedisConnectionFactory")) {
            return "redis://fake";
        } else if (isClass(redisConnectionFactory, "org.springframework.data.redis.connection.jedis" +
                ".JedisConnectionFactory")) {
            org.springframework.data.redis.connection.jedis.JedisConnectionFactory jedisConnectionFactory =
                    (org.springframework.data.redis.connection.jedis.JedisConnectionFactory) redisConnectionFactory;
            String host = jedisConnectionFactory.getHostName();
            int port = jedisConnectionFactory.getPort();

            return String.format("redis://%s:%d", host, port);
        }

        return String.format("Unable to determine URL for RedisConnectionFactory of type %s",
                redisConnectionFactory.getClass().getName());
    }

}
