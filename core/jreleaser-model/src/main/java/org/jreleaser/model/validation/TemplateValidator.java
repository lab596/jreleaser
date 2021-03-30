/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2020-2021 Andres Almiray.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jreleaser.model.validation;

import org.jreleaser.model.Distribution;
import org.jreleaser.model.JReleaserContext;
import org.jreleaser.model.Tool;

import java.util.List;

import static org.jreleaser.util.StringUtils.isBlank;
import static org.jreleaser.util.StringUtils.isNotBlank;

/**
 * @author Andres Almiray
 * @since 0.1.0
 */
public abstract class TemplateValidator extends Validator {
    public static void validateTemplate(JReleaserContext context, Distribution distribution,
                                        Tool tool, Tool parentTool, List<String> errors) {
        if (isBlank(tool.getTemplateDirectory())) {
            tool.setTemplateDirectory(parentTool.getTemplateDirectory());
            if (isNotBlank(tool.getTemplateDirectory()) &&
                !(context.getBasedir().resolve(tool.getTemplateDirectory().trim()).toFile().exists())) {
                errors.add("distribution." + distribution.getName() + "." + tool.getName() + ".template does not exist. " + tool.getTemplateDirectory());
            } else {
                tool.setTemplateDirectory("src/jreleaser/distributions/" + distribution.getName() + "/" + tool.getName());
            }
            return;
        }

        if (isNotBlank(tool.getTemplateDirectory()) &&
            !(context.getBasedir().resolve(tool.getTemplateDirectory().trim()).toFile().exists())) {
            errors.add("distribution." + distribution.getName() + "." + tool.getName() + ".template does not exist. " + tool.getTemplateDirectory());
        } else {
            tool.setTemplateDirectory("src/jreleaser/distributions/" + distribution.getName() + "/" + tool.getName());
        }
    }
}
