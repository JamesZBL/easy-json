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

import me.zbl.easyjson.data.StringNumber;

/**
 * Json 中的基本数据类型
 * <p>
 * 由于 Java 中基本数据类型的包装类型经过转换后的，其展现形式和基本数据类型是一样的，为方便
 * 处理，将基本数据类型及其包装类型统一视为 Json 中的基本数据类型
 *
 * @author JamesZBL
 * @date 2018-1-30
 */
public class JsonBasic extends JsonItem {

  private Object content;

  private static final Class<?>[] BASIC_DATA_TYPES = {
          boolean.class,
          byte.class,
          short.class,
          int.class,
          long.class,
          float.class,
          double.class,
          char.class
  };

  private static final Class<?>[] BASIC_DATA_WRAPPER_TYPES = {
          Boolean.class,
          Byte.class,
          Short.class,
          Integer.class,
          Long.class,
          Float.class,
          Double.class,
          Character.class
  };

  @Override
  public JsonItem itemClone() {
    return this;
  }

  public void setContent(Object origin) {
    if (origin instanceof Character) {
      content = String.valueOf(((Character) origin).charValue());
    } else if (isBasicOrString(origin)) {
      this.content = origin;
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * 判断是否为数值类型
   */
  public boolean typeOfNumber() {
    return content instanceof Number;
  }

  /**
   * 判断是否为布尔类型
   */
  public boolean typeofBoolean() {
    return content instanceof Boolean;
  }

  /**
   * 判断是否为字符串类型
   */
  public boolean typeofString() {
    return content instanceof String;
  }

  public boolean getBooleanValue() {
    if (typeofBoolean()) {
      return (Boolean) content;
    } else {
      return Boolean.parseBoolean(content.toString());
    }
  }

  public Integer getIntegerValue() {
    return typeOfNumber() ? (Integer) content : Integer.valueOf(content.toString());
  }

  public Short getShortValue() {
    return typeOfNumber() ? (Short) content : Short.parseShort(content.toString());
  }

  public Long getLongValue() {
    return typeOfNumber() ? (Long) content : Long.parseLong(content.toString());
  }

  public Byte getByteValue() {
    return typeOfNumber() ? (Byte) content : Byte.parseByte(content.toString());
  }

  public Float getFloatValue() {
    return typeOfNumber() ? (Float) content : Float.parseFloat(content.toString());
  }

  public Number getNumberValue() {
    return content instanceof String ? new StringNumber((String) content) : (Number) content;
  }

  public String getStringValue() {
    if (typeofBoolean()) {
      return ((Boolean) content).toString();
    } else if (typeOfNumber()) {
      return getNumberValue().toString();
    }
    return (String) content;
  }

  /**
   * 判断传递进来的对象或值是否为基本类型或字符串类型
   */
  private static boolean isBasicOrString(Object origin) {
    Class<?> originClazz = origin.getClass();
    for (Class<?> c : BASIC_DATA_TYPES) {
      if (c.isAssignableFrom(originClazz)) {
        return true;
      }
    }
    for (Class<?> c : BASIC_DATA_WRAPPER_TYPES) {
      if (c.isAssignableFrom(originClazz)) {
        return true;
      }
    }
    return origin instanceof String;
  }

  @Override
  public int hashCode() {
    return content.hashCode();
  }
}
