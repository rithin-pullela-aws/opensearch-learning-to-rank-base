/*
 *   Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License").
 *   You may not use this file except in compliance with the License.
 *   A copy of the License is located at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   or in the "license" file accompanying this file. This file is distributed
 *   on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *   express or implied. See the License for the specific language governing
 *   permissions and limitations under the License.
 */

package org.opensearch.ltr.breaker;

import org.opensearch.monitor.jvm.JvmService;

/**
 * A circuit breaker for memory usage.
 */
public class MemoryCircuitBreaker extends ThresholdCircuitBreaker<Short> {

    private static final short defaultThreshold = 85;
    private final JvmService jvmService;

    public MemoryCircuitBreaker(JvmService jvmService) {
        super(defaultThreshold);
        this.jvmService = jvmService;
    }

    public MemoryCircuitBreaker(short threshold, JvmService jvmService) {
        super(threshold);
        this.jvmService = jvmService;
    }

    @Override
    public boolean isOpen() {
        return jvmService.stats().getMem().getHeapUsedPercent() > this.getThreshold();
    }
}
