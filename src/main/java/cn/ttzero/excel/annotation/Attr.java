/*
 * Copyright (c) 2019, guanquan.wang@yandex.com All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.ttzero.excel.annotation;

import java.lang.annotation.*;

/**
 * Xml Attribute
 * Created by guanquan.wang on 2017/9/21.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Attr {
    /**
     * attribute name
     */
    String[] name();

    /**
     * attribute value
     *
     * @return
     */
    String[] value() default {};

    /**
     * namespace
     *
     * @return
     */
    NS namespace() default @NS("-");
}