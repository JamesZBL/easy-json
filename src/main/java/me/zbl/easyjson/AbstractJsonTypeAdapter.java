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

import me.zbl.easyjson.exceptions.JsonIOException;
import me.zbl.easyjson.io.JsonStringWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 类型适配器
 *
 * @author JamesZBL
 * @date 2018-02-06
 */
public abstract class AbstractJsonTypeAdapter<T> {

  /**
   * 转换成 Json
   *
   * @param o 被转换的数值、布尔值、数组、对象
   * @param w JsonStringWriter
   */
  public abstract void convertToJson(T o, JsonStringWriter w) throws IOException;


  public final void convertToJson(T origin, Writer writer) throws IOException {
    JsonStringWriter j = new JsonStringWriter(writer);
    convertToJson(origin, j);
  }

  public final String convertToJson(T origin, StringWriter s) throws IOException {
    JsonStringWriter j = new JsonStringWriter(s);
    convertToJson(origin, j);
    return s.toString();
  }

  public final String convertToJson(T origin) {
    StringWriter s = new StringWriter();
    JsonStringWriter j = new JsonStringWriter(s);
    try {
      convertToJson(origin, j);
    } catch (IOException e) {
      throw new JsonIOException();
    }
    return s.toString();
  }
}
