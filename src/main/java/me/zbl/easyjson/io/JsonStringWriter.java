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
package me.zbl.easyjson.io;

import me.zbl.easyjson.internal.StatusCheck;
import me.zbl.easyjson.io.lexical.JsonLexical;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

/**
 * 实现 Json 字符流的输入/输出
 *
 * @author JamesZBL
 * @date 2018-02-04
 */
public class JsonStringWriter implements Closeable, Flushable {

  private Writer mWriter;
  /**
   * 数组元素或对象属性之间的分隔符，默认为逗号
   */
  private String delimiterArrayOrAtrribute = ",";
  /**
   * 键值对之间的分隔符，默认为冒号
   */
  private String delimiterNameValuePair = ":";
  private String signArrayStart = "[";
  private String signArrayEnd = "]";
  private String signObjectStart = "{";
  private String signObjectEnd = "}";
  /**
   * 键值对中的“键”
   */
  private String itemName;
  /**
   * 以栈的形式存储词法上下文
   */
  private JsonLexical[] contextStack = new JsonLexical[64];
  /**
   * 初始栈顶位置
   */
  private int contextStackSize = 0;

  public JsonStringWriter(Writer w) {
    StatusCheck.checkIfNull(w);
    this.mWriter = w;
    // 置当前状态为初始化状态
    stackPush(JsonLexical.DOCUMENT_WITHOUT_ANY_ELEMENTS);
  }


  /**
   * 对词法上下文栈进行压栈操作
   * 用于开辟新的作用域（一个新的数组或新的对象）
   *
   * @param lexical 词法（入栈元素）
   */
  private void stackPush(JsonLexical lexical) {
    if (contextStackSize == contextStack.length) {
      // 如果发现栈满，则创建一个2倍容量的栈，将原有元素拷贝到新栈中，并将原栈的引用指向新栈
      JsonLexical[] n = new JsonLexical[contextStack.length * 2];
      System.arraycopy(contextStack, 0, n, 0, contextStackSize);
      contextStack = n;
    }
    contextStack[contextStackSize++] = lexical;
  }

  /**
   * 获取栈顶元素
   */
  private JsonLexical stackLast() {
    if (contextStackSize == 0) {
      throw new IllegalStateException("读写已经完成，无法再获取词法元素");
    }
    return contextStack[contextStackSize - 1];
  }

  /**
   * 踢出栈顶元素，并将新元素压栈
   *
   * @param lexical 词法（新的栈顶元素）
   */
  private void stackKick(JsonLexical lexical) {
    contextStack[contextStackSize - 1] = lexical;
  }

  /**
   * 开辟新的作用域（新的对象或新的数组）
   *
   * @param lexical 词法（表示一个作用域的开始）
   * @param sign    <pre>作用域开始位置的符号：数组为 " [ "，对象为 " { "</pre>
   *
   * @throws IOException
   */
  private JsonStringWriter newScope(JsonLexical lexical, String sign) throws IOException {
    beforeWritingValue();
    stackPush(lexical);
    mWriter.write(sign);
    return this;
  }

  /**
   * 结束作用域
   *
   * @param lexical 词法（表示一个作用域的开始）
   * @param sign    <pre>作用域开始位置的符号：数组为 " [ "，对象为 " { "</pre>
   *
   * @throws IOException
   */
  private JsonStringWriter finishScope(String sign, JsonLexical... lexical) throws IOException {
    JsonLexical last = stackLast();
    int confirm = 0;
    for (JsonLexical expected : lexical) {
      if (last.equals(expected)) {
        confirm++;
      }
    }
    if (confirm == 0) {
      throw new IllegalStateException("写顺序不正确！");
    }
    contextStackSize--;
    mWriter.write(sign);
    return this;
  }

  /**
   * 写键值对的“键”名之前的处理
   */
  private void beforeWritingName() throws IOException {
    JsonLexical last = stackLast();
    if (last == JsonLexical.OBJECT_WITH_ATTRIBUTES) {
      // 对象不为空，表明在此之前写过至少一个键值对
      mWriter.write(delimiterArrayOrAtrribute);
    }
    stackKick(JsonLexical.NAME_OF_PAIR);
  }

  /**
   * 写键值对的“值”之前的处理
   */
  private void beforeWritingValue() throws IOException {
    JsonLexical last = stackLast();
    switch (last) {
      case DOCUMENT_WITHOUT_ANY_ELEMENTS:
        // 顶级元素
        stackKick(JsonLexical.DOCUMENT_WITH_ELEMENTS);
        break;
      case DOCUMENT_WITH_ELEMENTS:
        // 只允许存在一个顶级元素，表示多个元素应使用数组
        throw new IllegalArgumentException("存在多个顶级元素！");
      case ARRAY_WITHOUT_ELEMENT:
        // 数组中的第一个元素
        stackKick(JsonLexical.ARRAY_WITH_ELEMENTS);
        break;
      case ARRAY_WITH_ELEMENTS:
        // 数组中已经有至少一个元素
        mWriter.write(delimiterArrayOrAtrribute);
        break;
      case NAME_OF_PAIR:
        // 刚写完“键”，现在写“值”
        mWriter.write(delimiterNameValuePair);
        // 将状态置为“非空对象”
        stackKick(JsonLexical.OBJECT_WITH_ATTRIBUTES);
        break;
      default:
    }
  }

  private void writeName() throws IOException {
    if (null != itemName) {
      beforeWritingName();
      writeString(itemName);
      this.itemName = null;
    }
  }

  /**
   * 写键
   *
   * @param name 键
   */
  public JsonStringWriter name(String name) {
    this.itemName = name;
    return this;
  }

  /**
   * 写值
   *
   * @param value 字符串值
   *
   * @throws IOException
   */
  public JsonStringWriter value(String value) throws IOException {
    writeName();
    beforeWritingValue();
    if (null == value) {
      writeNull();
    } else {
      writeString(value);
    }
    return this;
  }

  /**
   * 写值
   *
   * @param value 数值型值
   *
   * @throws IOException
   */
  public JsonStringWriter value(Number value) throws IOException {
    writeName();
    beforeWritingValue();
    if (null == value) {
      writeNull();
    } else {
      mWriter.append(value.toString());
    }
    return this;
  }

  /**
   * 写值
   *
   * @param value 布尔型值
   *
   * @throws IOException
   */
  public JsonStringWriter value(Boolean value) throws IOException {
    writeName();
    beforeWritingValue();
    if (null == value) {
      writeNull();
    } else {
      mWriter.write(value ? "true" : "false");
    }
    return this;
  }

  /**
   * 开始写数组
   *
   * @throws IOException
   */
  public JsonStringWriter newJsonArray() throws IOException {
    writeName();
    newScope(JsonLexical.ARRAY_WITHOUT_ELEMENT, signArrayStart);
    return this;
  }

  /**
   * 写数组完成
   *
   * @throws IOException
   */
  public JsonStringWriter finishJsonArray() throws IOException {
    finishScope(signArrayEnd, JsonLexical.ARRAY_WITHOUT_ELEMENT, JsonLexical.ARRAY_WITH_ELEMENTS);
    return this;
  }

  /**
   * 开始写对象
   *
   * @throws IOException
   */
  public JsonStringWriter newJsonObject() throws IOException {
    writeName();
    newScope(JsonLexical.OBJECT_WITHOUT_ATTRIBUTE, signObjectStart);
    return this;
  }

  /**
   * 写对象完成
   *
   * @throws IOException
   */
  public JsonStringWriter finishJsonObject() throws IOException {
    finishScope(signObjectEnd, JsonLexical.OBJECT_WITHOUT_ATTRIBUTE, JsonLexical.OBJECT_WITH_ATTRIBUTES);
    return this;
  }

  /**
   * 写字符串
   * 形式为："name"（带有双引号）
   *
   * @param s 字符串
   *
   * @throws IOException
   */
  private void writeString(String s) throws IOException {
    mWriter.write("\"");
    mWriter.write(s);
    mWriter.write("\"");
  }

  private void writeNull() throws IOException {
    mWriter.write("null");
  }

  @Override
  public void close() throws IOException {

  }

  @Override
  public void flush() throws IOException {

  }
}
