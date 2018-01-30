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
package me.zbl.easyjson.data;

/**
 * 以字符串的方式保存数值的类型
 *
 * @author JamesZBL
 * @date 2018-01-30
 */
public class StringNumber extends Number {

  private String content;

  public StringNumber(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return content;
  }

  @Override
  public int intValue() {
    return Integer.parseInt(content);
  }

  @Override
  public long longValue() {
    return Long.parseLong(content);
  }

  @Override
  public float floatValue() {
    return Float.parseFloat(content);
  }

  @Override
  public double doubleValue() {
    return Double.parseDouble(content);
  }
}
