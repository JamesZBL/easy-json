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
public final class JsonBasic extends JsonItem {

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

  public JsonBasic(Object origin) {
    setContent(origin);
  }

  public JsonBasic(Number origin) {
    setContent(origin);
  }

  public JsonBasic(Character origin) {
    setContent(origin);
  }

  public JsonBasic(String origin) {
    setContent(origin);
  }

  public JsonBasic(Boolean origin) {
    setContent(origin);
  }

  @Override
  public JsonItem itemClone() {
    return this;
  }

  /**
   * 赋值只接受基本数据类型、字符串（字符）以及基本数据类型的包装类型
   * Json 中单个字符同样作为字符串处理，所有为了方便转换，将单个字符预先转换为字符串类型
   */
  public void setContent(Object origin) {
    if (origin instanceof Character) {
      content = String.valueOf(((Character) origin).charValue());
    } else if (isBasicDataTypeOrWrapperOrString(origin)) {
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

  /**
   * 以 boolean 表示
   */
  @Override
  public boolean getBooleanValue() {
    if (typeofBoolean()) {
      return (Boolean) content;
    } else {
      return Boolean.parseBoolean(content.toString());
    }
  }

  /**
   * 以 int 表示
   */
  @Override
  public int getIntValue() {
    return typeOfNumber() ? getNumberValue().intValue() : Integer.parseInt(getStringValue());
  }

  /**
   * 以 short 表示
   */
  @Override
  public short getShortValue() {
    return typeOfNumber() ? getNumberValue().shortValue() : Short.parseShort(getStringValue());
  }

  /**
   * 以 long 表示
   */
  @Override
  public long getLongValue() {
    return typeOfNumber() ? getNumberValue().longValue() : Long.parseLong(getStringValue());
  }

  /**
   * 以 byte 表示
   */
  @Override
  public byte getByteValue() {
    return typeOfNumber() ? getNumberValue().byteValue() : Byte.parseByte(getStringValue());
  }

  /**
   * 以 float 表示
   */
  @Override
  public float getFloatValue() {
    return typeOfNumber() ? getNumberValue().floatValue() : Float.parseFloat(getStringValue());
  }

  /**
   * 以 double 表示
   */
  @Override
  public double getDoubleValue() {
    return typeOfNumber() ? getNumberValue().doubleValue() : Double.parseDouble(getStringValue());
  }

  /**
   * 以 char 表示
   */
  @Override
  public char getCharValue() {
    return getStringValue().charAt(0);
  }

  /**
   * 以 Number 表示
   */
  @Override
  public Number getNumberValue() {
    return content instanceof String ? new StringNumber((String) content) : (Number) content;
  }

  /**
   * 以字符串表示
   */
  @Override
  public String getStringValue() {
    if (typeofBoolean()) {
      return String.valueOf(content);
    } else if (typeOfNumber()) {
      return getNumberValue().toString();
    }
    return (String) content;
  }

  /**
   * 判断传递进来的对象或值是否为基本数据类型（包装类型）或字符串类型
   */
  private static boolean isBasicDataTypeOrWrapperOrString(Object origin) {
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


  /**
   * 判断数值类型能否不丢失精度地转换成整数
   */
  private static boolean canParseToInt(JsonBasic origin) {
    if (origin.content instanceof Number) {
      Number n = (Number) origin.content;
      return n instanceof Integer ||
              n instanceof Short ||
              n instanceof Long ||
              n instanceof Byte;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return content.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (null != obj) {
      if (getClass() != obj.getClass()) {
        // 类型不同
        return false;
      } else {
        // 类型相同
        JsonBasic origin = (JsonBasic) obj;
        if (typeOfNumber()) {
          // 数值型
          if (canParseToInt(this) && canParseToInt(origin)) {
            // 如果可以不丢失精度地转换为整数，则比较转换成长整型后的值（保证最大精度）
            long thisLv = getNumberValue().longValue();
            long oriLv = origin.getNumberValue().longValue();
            return thisLv == oriLv;
          } else if (origin.content instanceof Number) {
            // 目标为数值型不能无损转换成整数，则比较转换成双精度后的值
            double oriDv = origin.getDoubleValue();
            double thisDv = getDoubleValue();
            // 注意判断是否为 NaN (0.0d/0 所得的值)，在 Json 中表示为 "NaN"
            return thisDv == oriDv || Double.isNaN(oriDv) && Double.isNaN(oriDv);
          }
        } else {
          return content.equals(origin.content);
        }
      }
    } else if (content == null) {
      return true;
    }
    return false;
  }
}
