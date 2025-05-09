/*
 * Copyright 2025, AutoMQ HK Limited.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.kafka.controller.stream;

import org.apache.kafka.common.utils.MockTime;
import org.apache.kafka.common.utils.Time;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(60)
@Tag("S3Unit")
public class OverloadCircuitBreakerTest {

    @Test
    public void test() {
        Time time = new MockTime();
        OverloadCircuitBreaker breaker = new OverloadCircuitBreaker(time);
        assertFalse(breaker.isOverload());
        breaker.overload();
        assertTrue(breaker.isOverload());
        breaker.success();
        assertTrue(breaker.isOverload());
        time.sleep(OverloadCircuitBreaker.HALF_OPEN_WINDOW_MS);
        breaker.success();
        assertFalse(breaker.isOverload());
    }

}
