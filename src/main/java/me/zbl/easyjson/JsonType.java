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

import me.zbl.easyjson.type.Types;
import me.zbl.easyjson.validation.StatusCheck;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author JamesZBL
 * @date 2018-02-06
 */
public class JsonType<T> {

  final Type type;
  final Class<? super T> raw;
  final int hashCode;

  @SuppressWarnings("unchecked")
  JsonType(Type t) {
    StatusCheck.checkIfNull(t);
    this.type = t;
    this.raw = (Class<? super T>) Types.getRawType(t);
    this.hashCode = t.hashCode();
  }

  public Class<? super T> getRaw() {
    return raw;
  }

  public Type getType() {
    return type;
  }

  /**
   * 获取指定类型的直接父类的第一个类型参数
   */
  static Type getSuperClassFirstTypeParameter(Class<?> c) {
    Type s = c.getGenericSuperclass();
    if (s instanceof Class) {
      throw new IllegalStateException("未指定类型参数！");
    }
    ParameterizedType p = (ParameterizedType) s;
    return Types.formalize(p.getActualTypeArguments()[0]);
  }
}
