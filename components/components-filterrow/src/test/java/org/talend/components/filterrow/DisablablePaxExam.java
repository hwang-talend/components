/**
 * Copyright (C) 2006-2019 Talend Inc. - www.talend.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.talend.components.filterrow;

import org.apache.commons.lang3.SystemUtils;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.ops4j.pax.exam.junit.PaxExam;

// not a perfect impl but enough for now if we want to keep that test
public class DisablablePaxExam extends PaxExam {

    private final Class<?> clazz;

    public DisablablePaxExam(final Class<?> klass) throws InitializationError {
        super(klass);
        clazz = klass;
    }

    @Override
    public void run(final RunNotifier notifier) {
        if (!SystemUtils.JAVA_VERSION.startsWith("1.8.")) {
            notifier.fireTestAssumptionFailed(new Failure(
                    Description.createSuiteDescription(clazz),
                    new IllegalStateException("Java " + SystemUtils.JAVA_VERSION + " not yet supported")));
        } else {
            super.run(notifier);
        }
    }
}
