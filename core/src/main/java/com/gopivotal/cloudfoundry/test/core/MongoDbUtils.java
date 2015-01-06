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

import com.mongodb.DB;
import com.mongodb.ServerAddress;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Component;

@Component
public class MongoDbUtils extends AbstractServiceUtils<MongoDbFactory> {

    public String checkAccess(MongoDbFactory mongoDbFactory) {
        try {
            DB db = mongoDbFactory.getDb();
            db.getMongo();
            return "ok";
        } catch (Exception e) {
            return "failed with " + e.getMessage();
        }
    }

    public String getUrl(MongoDbFactory mongoDbFactory) {
        DB mongoDb = mongoDbFactory.getDb();

        if (mongoDb != null) {
            ServerAddress serverAddress = mongoDb.getMongo().getAddress();
            String host = serverAddress.getHost();
            int port = serverAddress.getPort();
            String db = mongoDb.getName();

            return String.format("mongodb://%s:%d/%s", host, port, db);
        }

        return "mongodb://fake.connection";
    }

}
