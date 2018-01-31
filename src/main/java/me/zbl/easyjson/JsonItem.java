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

/**
 * Json 中所有构成部分的抽象父类
 * <p>
 * Json 的组成部分映在 Java 中的映射包括：null（不包括在引用数据类型中）、基本数据类型、引用数据类型、数组类型
 * 不同的转换策略包含对 null 值不同处理的处理方法，因此 null值不包括在引用数据类型中，单独分类
 *
 * @author JamesZBL
 * @date 2018-1-30
 */
public abstract class JsonItem {

  /**
   * 对构成部分（包括所有子元素）进行克隆
   */
  public abstract JsonItem itemClone();

  /**
   * 判断是否为 Json 基本数据类型
   */
  public boolean typeOfJsonBasic() {
    return this instanceof JsonBasic;
  }

  public boolean typeOfJsonNull() {
    return this instanceof JsonNull;
  }

  public boolean typeOfJsonArray() {
    return this instanceof JsonArray;
  }

  public boolean getBooleanValue() {
    throw new UnsupportedOperationException();
  }

  public byte getByteValue() {
    throw new UnsupportedOperationException();
  }

  public int getIntValue() {
    throw new UnsupportedOperationException();
  }

  public short getShortValue() {
    throw new UnsupportedOperationException();
  }

  public long getLongValue() {
    throw new UnsupportedOperationException();
  }

  public float getFloatValue() {
    throw new UnsupportedOperationException();
  }

  public double getDoubleValue() {
    throw new UnsupportedOperationException();
  }

  public char getCharValue() {
    throw new UnsupportedOperationException();
  }

  public String getStringValue() {
    throw new UnsupportedOperationException();
  }

  public Number getNumberValue() {
    throw new UnsupportedOperationException();
  }
}
