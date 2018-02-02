/*
 * Copyright 2018 JamesZBL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package me.zbl.easyjson;

import me.zbl.easyjson.internal.StatusCheck;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * 对类中的域（属性、成员变量）的封装类
 *
 * @author JamesZBL
 * @date 2018-02-01
 */
public final class FieldItem {

  private final Field content;

  public FieldItem(Field content) {
    StatusCheck.checkIfNull(content);
    this.content = content;
  }

  /**
   * 获取域的名称
   */
  public String getFielName() {
    return content.getName();
  }

  /**
   * 获取域的类型（不包含类型变量）
   * 如：
   * <pre>
   *   class A{
   *     List<String> names;
   *   }
   * 将域 names 进行封装后，本方法将返回 java.util.List
   * </pre>
   */
  public Class<?> getFieldTypeClass() {
    return content.getType();
  }

  /**
   * 获取域的类型（包含类型变量）
   * 如：
   * <pre>
   *   class A{
   *     List<String> names;
   *   }
   * 将域 names 进行封装后，本方法将返回 java.util.List<java.lang.String>
   * </pre>
   */
  public Type getFieldType() {
    return content.getGenericType();
  }

  /**
   * 获取域所属类的实例中对应该域的引用
   *
   * @param obj 该类的一个实例
   *
   * @throws IllegalArgumentException
   */
  public Object getFieldObject(Object obj) throws IllegalAccessException {
    return content.get(obj);
  }
}
