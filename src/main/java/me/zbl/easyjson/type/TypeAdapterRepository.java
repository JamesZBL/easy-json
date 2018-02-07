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

import me.zbl.easyjson.AbstractJsonTypeAdapter;
import me.zbl.easyjson.EasyJson;
import me.zbl.easyjson.JsonType;
import me.zbl.easyjson.JsonTypeAdapterFactory;
import me.zbl.easyjson.io.JsonStringWriter;

import java.io.IOException;

/**
 * 常见类型的适配器仓库
 *
 * @author JamesZBL
 * @date 2018-02-06
 */
public final class TypeAdapterRepository {

  /**
   * Boolean
   */
  public static final AbstractJsonTypeAdapter<Boolean> BOOLEAN = new AbstractJsonTypeAdapter<Boolean>() {
    @Override
    public void convertToJson(Boolean o, JsonStringWriter w) throws IOException {
      w.value(o);
    }
  };

  /**
   * Character
   */
  public static final AbstractJsonTypeAdapter<Character> CHARACTER = new AbstractJsonTypeAdapter<Character>() {
    @Override
    public void convertToJson(Character o, JsonStringWriter w) throws IOException {
      w.value(o);
    }
  };

  /**
   * Number
   */
  public static final AbstractJsonTypeAdapter<Number> NUMBER = new AbstractJsonTypeAdapter<Number>() {
    @Override
    public void convertToJson(Number o, JsonStringWriter w) throws IOException {
      w.value(o);
    }
  };

  /**
   * String
   */
  public static final AbstractJsonTypeAdapter<String> STRING = new AbstractJsonTypeAdapter<String>() {
    @Override
    public void convertToJson(String o, JsonStringWriter w) throws IOException {
      w.value(o);
    }
  };

  /**
   * 构建一个生产类型适配器的工厂
   *
   * @param type    类型
   * @param adapter 已实现的类型适配器
   * @param <T1>    适配器适配的类型
   */
  @SuppressWarnings("unchecked")
  private <T1> JsonTypeAdapterFactory buildFactory(final Class<T1> type, final AbstractJsonTypeAdapter<T1> adapter) {
    return new JsonTypeAdapterFactory() {
      @Override
      public <T> AbstractJsonTypeAdapter<T> createAdapter(EasyJson context, JsonType<?> got) {
        // 将给定的类型和既定类型进行比较，如果相符就返回一个现有的对应类型的类型适配器
        return type == got.getRaw() ? (AbstractJsonTypeAdapter<T>) adapter : null;
      }
    };
  }
}
