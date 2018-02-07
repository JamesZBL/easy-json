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
package me.zbl.easyjson.type;

import me.zbl.easyjson.validation.StatusCheck;

import java.lang.reflect.*;

/**
 * @author JamesZBL
 * @date 2018-02-06
 */
public class Types {

  private Types() {
  }

  /**
   * 对类型进行分类规整
   */
  public static Type formalize(Type t) {
    return t;
  }

  /**
   * 获取类型的非泛型类型
   */
  public static Class<?> getRawType(Type type) {
    if (type instanceof Class<?>) {
      return (Class<?>) type;
    } else if (type instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) type;
      Type r = parameterizedType.getRawType();
      StatusCheck.checkIfLegal(r instanceof Class);
      return (Class<?>) r;
    } else if (type instanceof GenericArrayType) {
      Type c = ((GenericArrayType) type).getGenericComponentType();
      return Array.newInstance(getRawType(c), 0).getClass();
    } else if (type instanceof TypeVariable) {
      return Object.class;
    } else if (type instanceof WildcardType) {
      return getRawType(((WildcardType) type).getUpperBounds()[0]);
    } else {
      throw new IllegalArgumentException("无法识别的类型！");
    }
  }
}
